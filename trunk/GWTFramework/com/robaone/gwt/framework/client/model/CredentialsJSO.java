package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class CredentialsJSO extends JavaScriptObject {

	protected CredentialsJSO(){}
	public static final CredentialsJSO newInstance(){
		return CredentialsJSO.eval("{}");
	}
	public static final native CredentialsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdcredentials(Integer b) /*-{
		this.m_idcredentials = b;
	}-*/;
	public final native Integer getIdcredentials()/*-{
		return this.m_idcredentials;
	}-*/;
	public final native void setIduser(Integer b) /*-{
		this.m_iduser = b;
	}-*/;
	public final native Integer getIduser()/*-{
		return this.m_iduser;
	}-*/;
	public final native void setAuthenticator(String b) /*-{
		this.m_authenticator = b;
	}-*/;
	public final native String getAuthenticator()/*-{
		return this.m_authenticator;
	}-*/;
	public final native void setAuthdata(String b) /*-{
		this.m_authdata = b;
	}-*/;
	public final native String getAuthdata()/*-{
		return this.m_authdata;
	}-*/;
	public final native void setCreated_by(String b) /*-{
		this.m_created_by = b;
	}-*/;
	public final native String getCreated_by()/*-{
		return this.m_created_by;
	}-*/;
	public final native void setModified_by(String b) /*-{
		this.m_modified_by = b;
	}-*/;
	public final native String getModified_by()/*-{
		return this.m_modified_by;
	}-*/;
	public final native void setCreation_date(java.util.Date b) /*-{
		this.m_creation_date = b;
	}-*/;
	public final native java.util.Date getCreation_date()/*-{
		return this.m_creation_date;
	}-*/;
	public final native void setModification_date(java.util.Date b) /*-{
		this.m_modification_date = b;
	}-*/;
	public final native java.util.Date getModification_date()/*-{
		return this.m_modification_date;
	}-*/;
	public final native void setCreation_host(String b) /*-{
		this.m_creation_host = b;
	}-*/;
	public final native String getCreation_host()/*-{
		return this.m_creation_host;
	}-*/;
	public final native void setModification_host(String b) /*-{
		this.m_modification_host = b;
	}-*/;
	public final native String getModification_host()/*-{
		return this.m_modification_host;
	}-*/;
}