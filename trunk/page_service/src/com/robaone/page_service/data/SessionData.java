package com.robaone.page_service.data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.business.ApiCall;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.URLConnectionClient;


public class SessionData {

	private String user;
	private String remotehost;
	private String requesttoken;
	private String tokenSecret;
	private String accesstoken;
	private String authenticateurl;
	private String callbackurl;
	public SessionData(){
		String callbackUrl = AppDatabase.getProperty("callbackUrl");
		String authzUrl = AppDatabase.getProperty("authorizationUrl");
		this.authenticateurl = authzUrl;
		this.callbackurl = callbackUrl;
	}
	public void setUser(String account_record) {
		this.user = account_record;
	}

	public String getUser() {
		return this.user;
	}

	public String getRemoteHost() {
		return this.remotehost;
	}

	public void setRemoteHost(String host){
		this.remotehost = host;
	}

	private OAuthAccessor createOAuthAccessor(){
		String consumerKey = AppDatabase.getProperty("consumerKey");
		String callbackUrl = AppDatabase.getProperty("callbackUrl");
		String consumerSecret = AppDatabase.getProperty("consumerSecret");

		String reqUrl = AppDatabase.getProperty("requestUrl");
		String authzUrl = AppDatabase.getProperty("authorizationUrl");
		String accessUrl = AppDatabase.getProperty("accessUrl");

		OAuthServiceProvider provider
		= new OAuthServiceProvider(reqUrl, authzUrl, accessUrl);
		OAuthConsumer consumer
		= new OAuthConsumer(callbackUrl, consumerKey,
				consumerSecret, provider);
		return new OAuthAccessor(consumer);
	}
	public void executeRequest() throws IOException, OAuthException, URISyntaxException{
		this.execute("request",null,null);
	}
	public void executeAccess() throws IOException, OAuthException, URISyntaxException{
		this.execute("access",null,null);
	}
	public void executeAuthorize() throws IOException, OAuthException, URISyntaxException{
		this.execute("authorize",null,null);
	}
	public OAuthMessage executeAPI(String action,String jsondata) throws IOException, OAuthException, URISyntaxException{
		return this.execute(AppDatabase.getProperty("api.url"),action,jsondata);
	}
	private OAuthMessage execute(String operation,String action,String jsondata) throws IOException, OAuthException,
	URISyntaxException
	{
		if ("request".equals(operation)){
			OAuthAccessor accessor = createOAuthAccessor();
			OAuthClient client = new OAuthClient(new URLConnectionClient());
			client.getRequestToken(accessor);
			this.setRequestToken(accessor.requestToken);
			this.setTokenSecret(accessor.tokenSecret);

			AppDatabase.writeLog("00046: Last action: added requestToken");
		}
		else if ("access".equals(operation))
		{
			Properties paramProps = new Properties();
			paramProps.setProperty("oauth_token",
					this.getRequestToken());

			OAuthMessage response 
			= sendRequest(paramProps, this.getAccessUrl());

			this.setAccessToken(
					response.getParameter("oauth_token"));
			this.setTokenSecret( 
					response.getParameter("oauth_token_secret"));
			this.setUser(response.getParameter("user_id"));


			AppDatabase.writeLog("00047: Last action: added accessToken");
		}
		else if ("authorize".equals(operation))
		{
			// just print the redirect
			Properties paramProps = new Properties();
			paramProps.setProperty("oauth_token",
					this.getRequestToken());
			//paramProps.setProperty("oauth_callback",this.getCallbackUrl());

			OAuthAccessor accessor = createOAuthAccessor();

			OAuthMessage response = sendGETRequest(paramProps,
					accessor.consumer.serviceProvider.userAuthorizationURL);

			AppDatabase.writeLog("00048: Paste this in a browser:");
			AppDatabase.writeLog("00049: "+response.URL);
			this.authenticateurl = response.URL;
		} else {
			// access the resource
			Properties paramProps = new Properties();
			paramProps.setProperty("oauth_token",
					this.getAccessToken());
			paramProps.setProperty("action", action);
			paramProps.setProperty("data", jsondata);
			OAuthMessage response = sendRequest(paramProps, operation);
			return response;
		}
		return null;
	}

	public String getCallbackUrl() {
		return this.callbackurl;
	}
	public void setCallbackUrl(String url){
		this.callbackurl = url;
	}

	public String getAccessToken() {
		return this.accesstoken;
	}

	public void setAccessToken(String parameter) {
		this.accesstoken = parameter;
	}
	@SuppressWarnings("rawtypes")
	private OAuthMessage sendGETRequest(Map map, String url) throws IOException,
	URISyntaxException, OAuthException
	{
		List<Map.Entry> params = new ArrayList<Map.Entry>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry p = (Map.Entry) it.next();
			params.add(new OAuth.Parameter((String)p.getKey(),
					(String)p.getValue()));
		}
		OAuthAccessor accessor = createOAuthAccessor();
		accessor.tokenSecret = this.getTokenSecret();
		OAuthClient client = new OAuthClient(new URLConnectionClient());
		return client.invoke(accessor, "GET",  url, params);
	}
	@SuppressWarnings("rawtypes")
	private OAuthMessage sendRequest(Map map, String url) throws IOException,
	URISyntaxException, OAuthException
	{
		List<Map.Entry> params = new ArrayList<Map.Entry>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry p = (Map.Entry) it.next();
			params.add(new OAuth.Parameter((String)p.getKey(),
					(String)p.getValue()));
		}
		OAuthAccessor accessor = createOAuthAccessor();
		accessor.tokenSecret = this.getTokenSecret();
		OAuthClient client = new OAuthClient(new URLConnectionClient());
		return client.invoke(accessor, "POST",  url, params);
	}

	private String getAccessUrl() {
		return AppDatabase.getProperty("accessUrl");
	}

	public String getRequestToken() {
		return this.requesttoken;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	public String getTokenSecret(){
		return this.tokenSecret;
	}

	public void setRequestToken(String requestToken) {
		this.requesttoken = requestToken;
	}

	public boolean isAuthorized() throws Exception {
		if(this.accesstoken != null){
			try {
				OAuthMessage message = this.executeAPI("Login.getProfile", "{}");
				JSONObject jo = new JSONObject(ApiCall.StreamtoString(message.getBodyAsStream()));
				int status = jo.getJSONObject("response").getInt("status");
				String error = jo.getJSONObject("response").getString("error");
				if(status == 3 || (status == 1 && (error.endsWith("permission_denied") || error.endsWith("token_expired")))){
					return false;
				}else{
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} catch (OAuthException e) {
				e.printStackTrace();
				throw e;
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw e;
			} catch (JSONException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}else{
			return false;
		}
	}

	public String getAuthenticateUrl() {
		return this.authenticateurl;
	}

}
