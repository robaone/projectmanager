package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class App_credentialsJSO extends JavaScriptObject {

	protected App_credentialsJSO(){}
	public static final App_credentialsJSO newInstance(){
		return App_credentialsJSO.eval("{}");
	}
	public static final native App_credentialsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdapp_credentials(Integer b) /*-{
		this.m_idapp_credentials = b;
	}-*/;
	public final native Integer getIdapp_credentials()/*-{
		return this.m_idapp_credentials;
	}-*/;
	public final native void setRequest_token(String b) /*-{
		this.m_request_token = b;
	}-*/;
	public final native String getRequest_token()/*-{
		return this.m_request_token;
	}-*/;
	public final native void setAccess_token(String b) /*-{
		this.m_access_token = b;
	}-*/;
	public final native String getAccess_token()/*-{
		return this.m_access_token;
	}-*/;
	public final native void setCreated_by(String b) /*-{
		this.m_created_by = b;
	}-*/;
	public final native String getCreated_by()/*-{
		return this.m_created_by;
	}-*/;
	public final native void setCreation_host(String b) /*-{
		this.m_creation_host = b;
	}-*/;
	public final native String getCreation_host()/*-{
		return this.m_creation_host;
	}-*/;
	public final native void setExpiration_date(java.util.Date b) /*-{
		this.m_expiration_date = b;
	}-*/;
	public final native java.util.Date getExpiration_date()/*-{
		return this.m_expiration_date;
	}-*/;
	public final native void setCreation_date(java.util.Date b) /*-{
		this.m_creation_date = b;
	}-*/;
	public final native java.util.Date getCreation_date()/*-{
		return this.m_creation_date;
	}-*/;
	public final native void setActive(Integer b) /*-{
		this.m_active = b;
	}-*/;
	public final native Integer getActive()/*-{
		return this.m_active;
	}-*/;
	public final native void setIdapps(Integer b) /*-{
		this.m_idapps = b;
	}-*/;
	public final native Integer getIdapps()/*-{
		return this.m_idapps;
	}-*/;
	public final native void setIduser(Integer b) /*-{
		this.m_iduser = b;
	}-*/;
	public final native Integer getIduser()/*-{
		return this.m_iduser;
	}-*/;
	public final native void setToken_secret(String b) /*-{
		this.m_token_secret = b;
	}-*/;
	public final native String getToken_secret()/*-{
		return this.m_token_secret;
	}-*/;
}