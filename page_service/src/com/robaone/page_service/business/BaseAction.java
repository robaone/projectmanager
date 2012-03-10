package com.robaone.page_service.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
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
import org.xml.sax.SAXException;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.jdo.App_credentials_jdo;
import com.robaone.api.data.jdo.App_credentials_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.api.oauth.ROAPIOAuthProvider;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.page_service.data.SessionData;

public class BaseAction<T> {
	private OutputStream out;
	private SessionData session;
	private HttpServletRequest request;
	private DSResponse<T> dsr;
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xfactory;
	XPath xpath;
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
	public boolean requireLogin(){
		String user = this.getSessionData().getUser();
		if(user == null){
			this.getResponse().setStatus(JSONResponse.LOGIN_REQUIRED);
			return true;
		}else{
			return false;
		}
	}
	public void validate() throws Exception {
		
	}
	public String findXPathText(JSONObject jo,String tagname,String path) throws XPathExpressionException, SAXException, IOException, JSONException {
		return findXPathText(XML.toString(jo,tagname),path);
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
	public void resetSession(){
		SessionData d = new SessionData();
		this.request.getSession().setAttribute("appsessiondata", d);
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
	public void writeResonse(){
		if(this.dsr != null){
			PrintWriter pw = new PrintWriter(this.out);
			JSONObject jo = new JSONObject(this.dsr);
			pw.print(jo.toString());
			AppDatabase.writeLog("00043: Response = "+jo.toString());
			pw.flush();
			pw.close();
		}
	}
	public Properties getProperties(){
		return this.dsr.getResponse().getProperties();
	}
	public void sendError(Exception e){
		this.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
		this.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
	}
	protected void authorize() throws Exception {
		SessionData data = this.getSessionData();
		if(!data.isAuthorized()){
			data.executeRequest();
			data.executeAuthorize();
			throw new AuthorizationException(data.getAuthenticateUrl());
		}
	}
	protected void authorize(String callbackurl) throws Exception {
		SessionData data = this.getSessionData();
		data.setCallbackUrl(callbackurl);
		this.authorize();
	}
	protected void deauthorize() throws Exception {
		AppDatabase.writeLog("00044: BaseAction.deauthorize()");
		SessionData data = this.getSessionData();
		if(data.isAuthorized()){
			String action = "Login.logout";
			String adata = "{}";
			OAuthMessage response = data.executeAPI(action,adata);
			JSONObject jo = new JSONObject(response.readBodyAsString());
			AppDatabase.writeLog("00045: response = "+jo.toString());
			String xml = XML.toString(jo, "response");
			String status = this.findXPathText(xml, "//status");
			if(!status.equals("0")){
				throw new Exception(this.findXPathText(xml, "//error"));
			}
		}
	}
}
