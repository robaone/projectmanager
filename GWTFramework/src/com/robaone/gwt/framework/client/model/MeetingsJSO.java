package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class MeetingsJSO extends JavaScriptObject {

	protected MeetingsJSO(){}
	public static final MeetingsJSO newInstance(){
		return MeetingsJSO.eval("{}");
	}
	public static final native MeetingsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdmeetings(Integer b) /*-{
		this.m_idmeetings = b;
	}-*/;
	public final native Integer getIdmeetings()/*-{
		return this.m_idmeetings;
	}-*/;
	public final native void setCreated_by(Integer b) /*-{
		this.m_created_by = b;
	}-*/;
	public final native Integer getCreated_by()/*-{
		return this.m_created_by;
	}-*/;
	public final native void setCreation_date(String b) /*-{
		this.m_creation_date = b;
	}-*/;
	public final native String getCreation_date()/*-{
		return this.m_creation_date;
	}-*/;
	public final native void setModified_by(Integer b) /*-{
		this.m_modified_by = b;
	}-*/;
	public final native Integer getModified_by()/*-{
		return this.m_modified_by;
	}-*/;
	public final native void setModification_date(String b) /*-{
		this.m_modification_date = b;
	}-*/;
	public final native String getModification_date()/*-{
		return this.m_modification_date;
	}-*/;
	public final native void set_void(Boolean b) /*-{
		this.m__void = b;
	}-*/;
	public final native Boolean get_void()/*-{
		return this.m__void;
	}-*/;
	public final native void setStartdate(String b) /*-{
		this.m_startdate = b;
	}-*/;
	public final native String getStartdate()/*-{
		return this.m_startdate;
	}-*/;
	public final native void setEnddate(String b) /*-{
		this.m_enddate = b;
	}-*/;
	public final native String getEnddate()/*-{
		return this.m_enddate;
	}-*/;
	public final native void setTitle(String b) /*-{
		this.m_title = b;
	}-*/;
	public final native String getTitle()/*-{
		return this.m_title;
	}-*/;
	public final native void setCalendar_doc(String b) /*-{
		this.m_calendar_doc = b;
	}-*/;
	public final native String getCalendar_doc()/*-{
		return this.m_calendar_doc;
	}-*/;
}