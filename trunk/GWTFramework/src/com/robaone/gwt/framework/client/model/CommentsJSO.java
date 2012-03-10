package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class CommentsJSO extends JavaScriptObject {

	protected CommentsJSO(){}
	public static final CommentsJSO newInstance(){
		return CommentsJSO.eval("{}");
	}
	public static final native CommentsJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdcomments(Integer b) /*-{
		this.m_idcomments = b;
	}-*/;
	public final native Integer getIdcomments()/*-{
		return this.m_idcomments;
	}-*/;
	public final native void setReferenceid(String b) /*-{
		this.m_referenceid = b;
	}-*/;
	public final native String getReferenceid()/*-{
		return this.m_referenceid;
	}-*/;
	public final native void setComment(String b) /*-{
		this.m_comment = b;
	}-*/;
	public final native String getComment()/*-{
		return this.m_comment;
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
	public final native void setModification_host(String b) /*-{
		this.m_modification_host = b;
	}-*/;
	public final native String getModification_host()/*-{
		return this.m_modification_host;
	}-*/;
	public final native void set_void(Boolean b) /*-{
		this.m__void = b;
	}-*/;
	public final native Boolean get_void()/*-{
		return this.m__void;
	}-*/;
}