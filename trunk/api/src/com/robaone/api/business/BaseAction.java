package com.robaone.api.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

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

import org.json.JSONObject;
import org.w3c.dom.Document;
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
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class BaseAction<T> {
	private OutputStream out;
	private SessionData session;
	private HttpServletRequest request;
	private DSResponse<T> dsr;
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xfactory;
	XPath xpath;
	protected DatabaseImpl db = new DatabaseImpl();
	public BaseAction(OutputStream o, SessionData d, HttpServletRequest request) throws ParserConfigurationException{
		this.out = o;
		this.session = d;
		this.request = request;
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xfactory = XPathFactory.newInstance();
		xpath = xfactory.newXPath();
	}
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
	public void setDSResponse(DSResponse<T> r){
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
		AppDatabase.writeLog("Response = "+jo.toString());
		pw.flush();
		pw.close();
	}
	public Properties getProperties(){
		return this.dsr.getResponse().getProperties();
	}
	public void validate() throws Exception {
		AppDatabase.writeLog("BaseAction.validate()");
		boolean debug = false;
		try{
			debug = AppDatabase.getProperty("debug").equals("true");
		}catch(Exception e){}
		if(debug) return;
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);

		final OAuthAccessor accessor = ROAPIOAuthProvider.getAccessor(requestMessage);
		ROAPIOAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
		AppDatabase.writeLog("Request validated");
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
					App_credentials_jdo cred = App_credentials_jdoManager.bindApp_credentials(getResultSet());
					User_jdoManager uman = new User_jdoManager(this.getConnection());
					User_jdo user = uman.getUser(cred.getIduser());
					getSessionData().setUser(user);
					getSessionData().setCredentials(cred);
					AppDatabase.writeLog("Credentials Saved");
				}
			}
			
		};
		ConfigManager.runConnectionBlock(block, this.db.getConnectionManager());
	}
	public void deAuthorize() throws Exception {
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
		AppDatabase.writeLog("BaseAction.deAuthorize()");
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
					App_credentials_jdo cred = App_credentials_jdoManager.bindApp_credentials(getResultSet());
					cred.setActive(0);
					man.save(cred);
					AppDatabase.writeLog("credentials deauthorized");
				}
			}
			
		};
		ConfigManager.runConnectionBlock(block, db.getConnectionManager());
	}
	public void sendError(Exception e){
		this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
		this.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
	}
}
