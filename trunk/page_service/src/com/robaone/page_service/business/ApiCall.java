package com.robaone.page_service.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import net.oauth.OAuthMessage;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.data.SessionData;

public class ApiCall {
	public static String toXML(org.w3c.dom.Node doc) throws Exception {
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = 
		    (DOMImplementationLS)registry.getDOMImplementation("LS");
		LSSerializer writer = impl.createLSSerializer();
		String xml = writer.writeToString(doc);
		return xml;
	}
	public static JSONObject toJSONObject(org.w3c.dom.Node doc) throws Exception{
		return XML.toJSONObject(toXML(doc));
	}
	public static String call_function(String instanceid,String function,String data,String style_sheet) {
		return call_function(instanceid,function,data,null,style_sheet);
	}
	public static String transform(org.w3c.dom.Node inherited_doc,String style_sheet){
		AppDatabase.writeLog("00084: ApiCall.transform('"+inherited_doc+"','"+style_sheet+"')");
		String retval = "";
		try{
			retval = transform(XML.toString(toJSONObject(inherited_doc)),style_sheet);
		}catch(Exception e){
			e.printStackTrace();
			retval = e.getMessage();
		}
		return retval;
	}
	public static String call_function(String instanceid,String function,String data,org.w3c.dom.Node inherited_doc,String style_sheet){
		String retval = "";
		JSONObject session_jo = null;
		AppDatabase.writeLog("00076: ApiCall.call_function('"+instanceid+"','"+function+"','"+data+"','"+inherited_doc+"','"+style_sheet+"')");
		try{
			SessionData session = getSessionData(instanceid);
			session_jo = AppDatabase.getSession(instanceid);
			ApiCall.authorize(instanceid);
			String access_token = null;
			try{ 
				access_token = session_jo.getString("accessToken");
			}catch(Exception e){
				e.printStackTrace();
			}
			if(access_token != null){
				OAuthMessage message = session.executeAPI(function, data);
				JSONObject retval_json = new JSONObject(StreamtoString(message.getBodyAsStream()));
				JSONObject data_json = new JSONObject(data);
				JSONObject combined_data = new JSONObject();
				combined_data.put("retval", retval_json);
				combined_data.put("input", data_json);
				combined_data.put("instanceid", instanceid);
				if(inherited_doc != null){
					JSONObject parent = toJSONObject(inherited_doc);
					parent.getJSONObject(JSONObject.getNames(parent)[0]).getJSONObject("retval").getJSONObject("response").put(function, combined_data);
					combined_data = parent;
				}
				String xml = null;
				if(inherited_doc != null){
					xml = XML.toString(combined_data);
				}else{
					xml = XML.toString(combined_data,function);
				}
				String status = findXPathString(XML.toString(retval_json),"//response/status");

				if("3".equals(status)){
					/* Request a login */
					session_jo.put("request_login", true);
					AppDatabase.putSession(instanceid, session_jo);
				}else if("1".equals(status)){
					String error = findXPathString(XML.toString(retval_json),"/response/error");
					if(error != null && (error.endsWith("permission_denied") || error.endsWith("token_expired"))){
						session_jo.put("request_login", true);
						AppDatabase.putSession(instanceid, session_jo);
					}
				}else{
					retval = transform(xml,style_sheet);
				}
			}else{
				session_jo.put("request_login", true);
				AppDatabase.putSession(instanceid, session_jo);
			}
		}catch(AuthorizationException ae){
			try{
				session_jo.put("request_login", true);
				AppDatabase.putSession(instanceid, session_jo);
			}catch(Exception e){
				e.printStackTrace();
				retval = e.getMessage();
			}
		}catch(Exception e){
			e.printStackTrace();
			retval = e.getMessage();
		}
		return retval;
	}
	protected static void authorize(String instanceid) throws Exception {
		SessionData data = getSessionData(instanceid);
		authorize(data);
	}
	private static SessionData getSessionData(String instanceid) throws Exception {
		SessionData data = new SessionData();
		JSONObject session_jo = AppDatabase.getSession(instanceid);
		String accesstoken = session_jo.getString("accessToken");
		if(accesstoken != null && !accesstoken.equals("null"))
			data.setAccessToken(session_jo.getString("accessToken"));
		data.setCallbackUrl(session_jo.getString("callbackUrl"));
		String remote_host = session_jo.getString("remoteHost");
		if(remote_host != null && !remote_host.equals("null"))
			data.setRemoteHost(session_jo.getString("remoteHost"));
		data.setRequestToken(session_jo.getString("requestToken"));
		data.setTokenSecret(session_jo.getString("tokenSecret"));
		String user = session_jo.getString("user");
		if(user != null && !user.equals("null"))
			data.setUser(session_jo.getString("user"));
		return data;
	}
	public static void authorize(SessionData data) throws Exception {
		if(!data.isAuthorized()){
			data.executeRequest();
			data.executeAuthorize();
			throw new AuthorizationException(data.getAuthenticateUrl());
		}
	}
	public static String findXPathString(String xml,String path) throws Exception {
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		XPathFactory xfactory;
		XPath xpath;
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xfactory = XPathFactory.newInstance();
		xpath = xfactory.newXPath();
		ByteArrayInputStream bin = new ByteArrayInputStream(xml.getBytes());
		Document doc = builder.parse(bin);
		XPathExpression expr = xpath.compile(path);
		return (String)expr.evaluate(doc, XPathConstants.STRING);
	}
	public static String StreamtoString(InputStream in) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		for(int i = in.read(buffer);i > -1;i = in.read(buffer)){
			bout.write(buffer, 0, i);
		}
		return bout.toString();
	}
	public static String transform(String xml,String xsl) throws TransformerException{
		String xsl_folder = AppDatabase.getProperty("xsl.folder");
		StreamSource source = new StreamSource(new StringReader(xml));
		StreamSource stylesource = new StreamSource(xsl_folder+System.getProperty("file.separator")+xsl+".xsl");
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(stylesource);
		StringWriter out = new StringWriter();
		StreamResult result = new StreamResult(out);
		transformer.transform(source, result);
		String retval = out.toString();
		return retval;
	}
}
