package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class UserJSO extends JavaScriptObject {

	protected UserJSO(){}
	public static final UserJSO newInstance(){
		return UserJSO.eval("{}");
	}
	public static final native UserJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIduser(Integer b) /*-{
		this.m_iduser = b;
	}-*/;
	public final native Integer getIduser()/*-{
		return this.m_iduser;
	}-*/;
	public final native void setUsername(String b) /*-{
		this.m_username = b;
	}-*/;
	public final native String getUsername()/*-{
		return this.m_username;
	}-*/;
	public final native void setFirst_name(String b) /*-{
		this.m_first_name = b;
	}-*/;
	public final native String getFirst_name()/*-{
		return this.m_first_name;
	}-*/;
	public final native void setLast_name(String b) /*-{
		this.m_last_name = b;
	}-*/;
	public final native String getLast_name()/*-{
		return this.m_last_name;
	}-*/;
	public final native void setFailed_attempts(Integer b) /*-{
		this.m_failed_attempts = b;
	}-*/;
	public final native Integer getFailed_attempts()/*-{
		return this.m_failed_attempts;
	}-*/;
	public final native void setPassword(String b) /*-{
		this.m_password = b;
	}-*/;
	public final native String getPassword()/*-{
		return this.m_password;
	}-*/;
	public final native void setActive(Integer b) /*-{
		this.m_active = b;
	}-*/;
	public final native Integer getActive()/*-{
		return this.m_active;
	}-*/;
	public final native void setModified_by(String b) /*-{
		this.m_modified_by = b;
	}-*/;
	public final native String getModified_by()/*-{
		return this.m_modified_by;
	}-*/;
	public final native void setCreated_by(String b) /*-{
		this.m_created_by = b;
	}-*/;
	public final native String getCreated_by()/*-{
		return this.m_created_by;
	}-*/;
	public final native void setCreation_date(java.util.Date b) /*-{
		this.m_creation_date = b;
	}-*/;
	public final native java.util.Date getCreation_date()/*-{
		return this.m_creation_date;
	}-*/;
	public final native void setModified_date(java.util.Date b) /*-{
		this.m_modified_date = b;
	}-*/;
	public final native java.util.Date getModified_date()/*-{
		return this.m_modified_date;
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
	public final native void setMeta_data(String b) /*-{
		this.m_meta_data = b;
	}-*/;
	public final native String getMeta_data()/*-{
		return this.m_meta_data;
	}-*/;
	public final native void setReset(Integer b) /*-{
		this.m_reset = b;
	}-*/;
	public final native Integer getReset()/*-{
		return this.m_reset;
	}-*/;
}