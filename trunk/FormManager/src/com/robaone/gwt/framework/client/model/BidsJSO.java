package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class BidsJSO extends JavaScriptObject {

	protected BidsJSO(){}
	public static final BidsJSO newInstance(){
		return BidsJSO.eval("{}");
	}
	public static final native BidsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdbids(Integer b) /*-{
		this.m_idbids = b;
	}-*/;
	public final native Integer getIdbids()/*-{
		return this.m_idbids;
	}-*/;
	public final native void setContractorid(Integer b) /*-{
		this.m_contractorid = b;
	}-*/;
	public final native Integer getContractorid()/*-{
		return this.m_contractorid;
	}-*/;
	public final native void setProjectid(Integer b) /*-{
		this.m_projectid = b;
	}-*/;
	public final native Integer getProjectid()/*-{
		return this.m_projectid;
	}-*/;
	public final native void setTotal(Double b) /*-{
		this.m_total = b;
	}-*/;
	public final native Double getTotal()/*-{
		return this.m_total;
	}-*/;
	public final native void setDetails(String b) /*-{
		this.m_details = b;
	}-*/;
	public final native String getDetails()/*-{
		return this.m_details;
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
	public final native void setStatus(Integer b) /*-{
		this.m_status = b;
	}-*/;
	public final native Integer getStatus()/*-{
		return this.m_status;
	}-*/;
}