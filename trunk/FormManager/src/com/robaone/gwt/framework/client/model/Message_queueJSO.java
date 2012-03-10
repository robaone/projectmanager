package com.robaone.gwt.framework.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Message_queueJSO extends JavaScriptObject {

	protected Message_queueJSO(){}
	public static final Message_queueJSO newInstance(){
		return Message_queueJSO.eval("{}");
	}
	public static final native Message_queueJSO eval(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
	public final native void setIdmessage_queue(Integer b) /*-{
		this.m_idmessage_queue = b;
	}-*/;
	public final native Integer getIdmessage_queue()/*-{
		return this.m_idmessage_queue;
	}-*/;
	public final native void setCreationdate(java.util.Date b) /*-{
		this.m_creationdate = b;
	}-*/;
	public final native java.util.Date getCreationdate()/*-{
		return this.m_creationdate;
	}-*/;
	public final native void setSubject(String b) /*-{
		this.m_subject = b;
	}-*/;
	public final native String getSubject()/*-{
		return this.m_subject;
	}-*/;
	public final native void setFrom(String b) /*-{
		this.m_from = b;
	}-*/;
	public final native String getFrom()/*-{
		return this.m_from;
	}-*/;
	public final native void setTo(String b) /*-{
		this.m_to = b;
	}-*/;
	public final native String getTo()/*-{
		return this.m_to;
	}-*/;
	public final native void setCc(String b) /*-{
		this.m_cc = b;
	}-*/;
	public final native String getCc()/*-{
		return this.m_cc;
	}-*/;
	public final native void setBcc(String b) /*-{
		this.m_bcc = b;
	}-*/;
	public final native String getBcc()/*-{
		return this.m_bcc;
	}-*/;
	public final native void setBody(String b) /*-{
		this.m_body = b;
	}-*/;
	public final native String getBody()/*-{
		return this.m_body;
	}-*/;
	public final native void setHtml(Integer b) /*-{
		this.m_html = b;
	}-*/;
	public final native Integer getHtml()/*-{
		return this.m_html;
	}-*/;
	public final native void setAttachments(String b) /*-{
		this.m_attachments = b;
	}-*/;
	public final native String getAttachments()/*-{
		return this.m_attachments;
	}-*/;
}