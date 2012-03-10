package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class AppsJSO extends JavaScriptObject {

	protected AppsJSO(){}
	public static final AppsJSO newInstance(){
		return AppsJSO.eval("{}");
	}
	public static final native AppsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdapps(Integer b) /*-{
		this.m_idapps = b;
	}-*/;
	public final native Integer getIdapps()/*-{
		return this.m_idapps;
	}-*/;
	public final native void setName(String b) /*-{
		this.m_name = b;
	}-*/;
	public final native String getName()/*-{
		return this.m_name;
	}-*/;
	public final native void setCallback_url(String b) /*-{
		this.m_callback_url = b;
	}-*/;
	public final native String getCallback_url()/*-{
		return this.m_callback_url;
	}-*/;
	public final native void setDescription(String b) /*-{
		this.m_description = b;
	}-*/;
	public final native String getDescription()/*-{
		return this.m_description;
	}-*/;
	public final native void setConsumer_key(String b) /*-{
		this.m_consumer_key = b;
	}-*/;
	public final native String getConsumer_key()/*-{
		return this.m_consumer_key;
	}-*/;
	public final native void setConsumer_secret(String b) /*-{
		this.m_consumer_secret = b;
	}-*/;
	public final native String getConsumer_secret()/*-{
		return this.m_consumer_secret;
	}-*/;
	public final native void setActive(Integer b) /*-{
		this.m_active = b;
	}-*/;
	public final native Integer getActive()/*-{
		return this.m_active;
	}-*/;
	public final native void setIduser(Integer b) /*-{
		this.m_iduser = b;
	}-*/;
	public final native Integer getIduser()/*-{
		return this.m_iduser;
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
	public final native void setCreated_date(java.util.Date b) /*-{
		this.m_created_date = b;
	}-*/;
	public final native java.util.Date getCreated_date()/*-{
		return this.m_created_date;
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
}