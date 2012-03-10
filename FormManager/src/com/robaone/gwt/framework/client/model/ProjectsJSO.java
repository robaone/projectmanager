package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class ProjectsJSO extends JavaScriptObject {

	protected ProjectsJSO(){}
	public static final ProjectsJSO newInstance(){
		return ProjectsJSO.eval("{}");
	}
	public static final native ProjectsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdprojects(Integer b) /*-{
		this.m_idprojects = b;
	}-*/;
	public final native Integer getIdprojects()/*-{
		return this.m_idprojects;
	}-*/;
	public final native void setConsumerid(Integer b) /*-{
		this.m_consumerid = b;
	}-*/;
	public final native Integer getConsumerid()/*-{
		return this.m_consumerid;
	}-*/;
	public final native void setName(String b) /*-{
		this.m_name = b;
	}-*/;
	public final native String getName()/*-{
		return this.m_name;
	}-*/;
	public final native void setCreated_by(Integer b) /*-{
		this.m_created_by = b;
	}-*/;
	public final native Integer getCreated_by()/*-{
		return this.m_created_by;
	}-*/;
	public final native void setCreation_date(java.util.Date b) /*-{
		this.m_creation_date = b;
	}-*/;
	public final native java.util.Date getCreation_date()/*-{
		return this.m_creation_date;
	}-*/;
	public final native void setCreation_host(String b) /*-{
		this.m_creation_host = b;
	}-*/;
	public final native String getCreation_host()/*-{
		return this.m_creation_host;
	}-*/;
	public final native void setModified_by(Integer b) /*-{
		this.m_modified_by = b;
	}-*/;
	public final native Integer getModified_by()/*-{
		return this.m_modified_by;
	}-*/;
	public final native void setModified_date(java.util.Date b) /*-{
		this.m_modified_date = b;
	}-*/;
	public final native java.util.Date getModified_date()/*-{
		return this.m_modified_date;
	}-*/;
	public final native void setModifier_host(String b) /*-{
		this.m_modifier_host = b;
	}-*/;
	public final native String getModifier_host()/*-{
		return this.m_modifier_host;
	}-*/;
}