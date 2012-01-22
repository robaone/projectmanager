package com.robaone.api.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.server.OAuthServlet;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.App_credentials_jdo;
import com.robaone.api.data.jdo.App_credentials_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.api.oauth.ROAPIOAuthProvider;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;

abstract public class BaseAction<T> {
	protected static final String NOT_FOUND_ERROR = "Record Not Found";
	public static String NOT_SUPPORTED = "Not Supported";
	private OutputStream out;
	private SessionData session;
	private HttpServletRequest request;
	private DSResponse<T> dsr;
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xfactory;
	XPath xpath;
	protected DatabaseImpl db = new DatabaseImpl();
	private Document query_doc;
	abstract public class FunctionCall{
		protected BaseAction action;
		private String xml;
		abstract protected void run(JSONObject jo) throws Exception;
		public String findXPathString(String xpath) throws Exception{
			return action.findXPathText(xml, xpath);
		}
		public void run(BaseAction action,JSONObject jo){
			try{
				this.action = action;
				action.validate();
				if(action.requireLogin() == false){
					String xml = XML.toString(jo, "request");
					run(jo);
				}else{
					action.getResponse().setStatus(JSONResponse.LOGIN_REQUIRED);
					action.getResponse().setError("Login Required");
				}
			}catch(Exception e){
				action.sendError(e);
			}
		}
	}
	abstract public class PagedFunctionCall extends FunctionCall {
		private Document query_doc;
		private String m_query_name;
		public PagedFunctionCall(String query_name) {
			m_query_name = query_name;
		}
		@Override
		protected void run(JSONObject jo) throws Exception {
			this.buildQueryDoc(m_query_name);
			String filter = this.findXPathString("//filter");
			String page = this.findXPathString("//page");
			String limit = this.findXPathString("//limit");
			int p = 0;
			int lim = 5;
			if(FieldValidator.exists(page) && (!FieldValidator.isNumber(page) || Integer.parseInt(page) < 0)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("page", "You must enter a number greater than or equal to zero");
			}else if(FieldValidator.exists(page)){
				p = Integer.parseInt(page);
			}
			if(FieldValidator.exists(limit) && (!FieldValidator.isNumber(limit) || Integer.parseInt(limit) < 1)){
				getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				getResponse().addError("limit", "You must enter a limit that is greater than zero");
			}else if(FieldValidator.exists(limit)){
				lim = Integer.parseInt(limit);
				lim = lim > 100 ? 100 : lim;
			}
			if(FieldValidator.exists(filter)){
				/**
				 * Search for failed jobs based on the filter
				 */
				if(getResponse().getStatus() == JSONResponse.OK){
					filteredSearch(jo,p,lim,filter);
				}
			}else{
				/**
				 * Search for all failed jobs
				 */
				if(getResponse().getStatus() == JSONResponse.OK){
					unfilteredSearch(jo,p,lim);
				}
			}
		}

		abstract protected void unfilteredSearch(JSONObject jo, int p, int lim) throws Exception;

		abstract protected void filteredSearch(JSONObject jo, int p, int lim, String filter) throws Exception;
		protected void buildQueryDoc(String name) throws Exception {
			InputStream in = BaseAction.class.getResourceAsStream("/com/robaone/api/data/queries/"+name+".xml");
			this.query_doc = builder.parse(in);
		}

		protected String getQueryStatement(String name) throws Exception {
			XPathExpression expr = xpath.compile("//ResultSet[@name=\""+name+"\"]//PreparedStatement");
			return (String)expr.evaluate(this.query_doc, XPathConstants.STRING);
		}

		protected Integer getParameterCount(String name) throws Exception {
			String path = "count(//ResultSet[@name=\""+name+"\"]//Parameter)";
			XPathExpression expr = xpath.compile(path);
			return (Integer)expr.evaluate(this.query_doc, XPathConstants.NUMBER);
		}

		protected NodeList getParameters(String name) throws Exception {
			String path = "//ResultSet[@name=\""+name+"\"]//Parameter";
			XPathExpression expr = xpath.compile(path);
			return (NodeList)expr.evaluate(this.query_doc, XPathConstants.NODESET);
		}
		protected int executeUpdate(final JSONObject jo,final int p, final int lim,final String query) throws Exception {
			final Vector<Integer> retval = new Vector<Integer>();
			new ConnectionBlock(){

				@Override
				protected void run() throws Exception {
					jo.put("result_page", p);
					jo.put("result_limit", lim);
					String query_str = getQueryStatement(query);
					this.prepareStatement(query_str);
					applyParameters(jo,query,getPS());
					int updated = this.executeUpdate();
					retval.add(updated);
				}
				
			}.run(db.getConnectionManager());
			return retval.size() > 0 ? retval.get(0) : 0;
		}
		protected void applyParameters(final JSONObject jo,
				final String list_query,PreparedStatement ps) throws Exception, SQLException {
			NodeList parameters = getParameters(list_query);
			for(int i = 0 ; i < parameters.getLength();i++){
				Node attrib = parameters.item(i).getAttributes().getNamedItem("name");
				String name = attrib.getTextContent();
				try{
					Object o = jo.get(name);
					if(name.equals("filter")){
						ps.setString(i+1, "%"+o.toString()+"%");
					}else{
						ps.setObject(i+1, o);
					}
				}catch(JSONException e){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError(name, ""+e.getMessage());
				}
			}
		}
		protected void getList(final JSONObject jo,final int p ,final int lim,final String list_query,final String count_query) throws Exception {
			new ConnectionBlock(){

				@Override
				protected void run() throws Exception {
					int startindex = (lim*p) + 1;
					int endindex = startindex + lim -1;
					jo.put("result_page", p);
					jo.put("result_limit", lim);
					String query_str = getQueryStatement(list_query);
					this.prepareStatement(query_str);
					applyParameters(jo, list_query,getPS());
					this.executeQuery();
					if(getResponse().getStatus() == JSONResponse.OK){
						convert(getResultSet());
						new ConnectionBlock(){

							@Override
							protected void run() throws Exception {
								String count_str = getQueryStatement(count_query);
								this.prepareStatement(count_str);
								applyParameters(jo, count_query,getPS());
								if(getResponse().getStatus() == JSONResponse.OK){
									this.executeQuery();
									if(next()){
										int count = this.getResultSet().getInt(1);
										getResponse().setTotalRows(count);
									}
								}
							}

						}.run(getConnectionManager());
					}
					endindex = (endindex-1) < getResponse().getTotalRows() ? endindex-1 : getResponse().getTotalRows()-1;
					getResponse().setEndRow(endindex);
				}

				

			}.run(getConnectionManager());

		}
		protected HDBConnectionManager getConnectionManager() throws NamingException {
			return db.getConnectionManager();
		}

	}
	public BaseAction(OutputStream o, SessionData d, HttpServletRequest request) throws ParserConfigurationException{
		this.out = o;
		this.session = d;
		this.request = request;
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xfactory = XPathFactory.newInstance();
		xpath = xfactory.newXPath();
		this.setDSResponse(newDSResponse());
	}
	abstract public DSResponse<T> newDSResponse();
	public String findXPathText(String xmldoc,String path) throws SAXException, IOException, XPathExpressionException{
		ByteArrayInputStream bin = new ByteArrayInputStream(xmldoc.getBytes());
		Document doc = builder.parse(bin);
		XPathExpression expr = xpath.compile(path);
		return (String)expr.evaluate(doc, XPathConstants.STRING);
	}
	public OutputStream getOutputStream(){
		return out;
	}
	public HttpServletRequest getRequest(){
		return this.request;
	}
	public String[] getRequestParameterValues(String name){
		return this.request.getParameterValues(name);
	}
	public String getParameter(String name){
		String[] values = this.getRequestParameterValues(name);
		if(values == null){
			return "";
		}else{
			return values[0];
		}
	}
	public void resetSession() throws Exception{
		SessionData d = new SessionData();
		this.request.getSession().setAttribute("sessiondata", d);
		this.session = d;
	}
	public SessionData getSessionData(){
		return session;
	}
	private void setDSResponse(DSResponse<T> r){
		this.dsr = r;
	}
	public JSONResponse<T> getResponse(){
		return this.dsr.getResponse();
	}
	public boolean requireLogin(){
		User_jdo user = this.getSessionData().getUser();
		if(user == null){
			this.getResponse().setStatus(JSONResponse.LOGIN_REQUIRED);
			return true;
		}else{
			return false;
		}
	}
	public void writeResonse(){
		PrintWriter pw = new PrintWriter(this.out);
		JSONObject jo = new JSONObject(this.dsr);
		pw.print(jo.toString());
		AppDatabase.writeLog("00011: Response = "+jo.toString());
		pw.flush();
		pw.close();
	}
	public Properties getProperties(){
		return this.dsr.getResponse().getProperties();
	}
	public void validate() throws Exception {
		AppDatabase.writeLog("00012: BaseAction.validate()");
		boolean debug = false;
		try{
			debug = AppDatabase.getProperty("debug").equals("true");
		}catch(Exception e){}
		if(debug) return;
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);

		final OAuthAccessor accessor = ROAPIOAuthProvider.getAccessor(requestMessage);
		ROAPIOAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
		AppDatabase.writeLog("00013: Request validated");
		// make sure token is authorized
		if (!Boolean.TRUE.equals(accessor.getProperty("authorized"))) {
			OAuthProblemException problem = new OAuthProblemException("permission_denied");
			throw problem;
		}
		/**
		 * Setup the session
		 */
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(App_credentials_jdo.ACCESS_TOKEN + " = ? or "+App_credentials_jdo.REQUEST_TOKEN + " = ?"));
				this.getPreparedStatement().setString(1, accessor.accessToken);
				this.getPreparedStatement().setString(2, accessor.requestToken);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					App_credentials_jdo cred = man.bindApp_credentials(getResultSet());
					User_jdoManager uman = new User_jdoManager(this.getConnection());
					User_jdo user = uman.getUser(cred.getIduser());
					getSessionData().setUser(user);
					getSessionData().setCredentials(cred);
					AppDatabase.writeLog("00014: Credentials Saved");
				}
			}

		};
		ConfigManager.runConnectionBlock(block, this.db.getConnectionManager());
	}
	public void deAuthorize() throws Exception {
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
		AppDatabase.writeLog("00015: BaseAction.deAuthorize()");
		final OAuthAccessor accessor = ROAPIOAuthProvider.getAccessor(requestMessage);
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				App_credentials_jdoManager man = new App_credentials_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(App_credentials_jdo.ACCESS_TOKEN + " = ? or "+App_credentials_jdo.REQUEST_TOKEN+" = ?"));
				this.getPreparedStatement().setString(1, accessor.accessToken);
				this.getPreparedStatement().setString(2, accessor.requestToken);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					App_credentials_jdo cred = man.bindApp_credentials(getResultSet());
					cred.setActive(0);
					man.save(cred);
					AppDatabase.writeLog("00016: credentials deauthorized");
				}
			}

		};
		ConfigManager.runConnectionBlock(block, db.getConnectionManager());
	}
	public void sendError(Exception e){
		this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
		this.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
	}
	protected void convert(ResultSet resultSet) throws SQLException, JSONException {
		ResultSetMetaData rsmdata = resultSet.getMetaData();
		while(resultSet.next()){
			JSONObject jo = new JSONObject();
			for(int i = 0; i < rsmdata.getColumnCount();i++){
				String column = rsmdata.getColumnName(i);
				int type = rsmdata.getColumnType(i);
				if(type == Types.DATE){
					jo.put(column, resultSet.getTimestamp(i));
				}else{
					jo.put(column, resultSet.getObject(i));
				}
			}
			getResponse().addData((T) jo);
		}
	}
	public java.util.Date jsonDate(String str) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		return df.parse(str);
	}
	public void fieldError(String field,String error){
		getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
		getResponse().addError(field, error);
	}
	public void generalError(String error){
		getResponse().setStatus(JSONResponse.GENERAL_ERROR);
		getResponse().setError(error);
	}
	public boolean isOK(){
		return getResponse().getStatus() == JSONResponse.OK;
	}
	public void removeReservedFields(JSONObject jo){
		jo.remove("created_by");
		jo.remove("creation_date");
		jo.remove("creation_host");
		jo.remove("modified_by");
		jo.remove("modified_date");
		jo.remove("modification_host");
	}
}
