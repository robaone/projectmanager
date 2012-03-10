package com.robaone.gwt.framework.client.model;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Message_queue implements IsSerializable {

	private Integer m_idmessage_queue;
	private java.util.Date m_creationdate;
	private String m_subject;
	private String m_from;
	private String m_to;
	private String m_cc;
	private String m_bcc;
	private String m_body;
	private String m_html;
	private String m_attachments;

	public void setIdmessage_queue(Integer b) {
		this.m_idmessage_queue = b;
	}
	public Integer getIdmessage_queue(){
		return this.m_idmessage_queue;
	}
	public void setCreationdate(java.util.Date b) {
		this.m_creationdate = b;
	}
	public java.util.Date getCreationdate(){
		return this.m_creationdate;
	}
	public void setSubject(String b) {
		this.m_subject = b;
	}
	public String getSubject(){
		return this.m_subject;
	}
	public void setFrom(String b) {
		this.m_from = b;
	}
	public String getFrom(){
		return this.m_from;
	}
	public void setTo(String b) {
		this.m_to = b;
	}
	public String getTo(){
		return this.m_to;
	}
	public void setCc(String b) {
		this.m_cc = b;
	}
	public String getCc(){
		return this.m_cc;
	}
	public void setBcc(String b) {
		this.m_bcc = b;
	}
	public String getBcc(){
		return this.m_bcc;
	}
	public void setBody(String b) {
		this.m_body = b;
	}
	public String getBody(){
		return this.m_body;
	}
	public void setHtml(String b) {
		this.m_html = b;
	}
	public String getHtml(){
		return this.m_html;
	}
	public void setAttachments(String b) {
		this.m_attachments = b;
	}
	public String getAttachments(){
		return this.m_attachments;
	}
}