/*
* Created on Aug 25, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Message_queue_jdo extends RO_JDO{
  public final static String IDMESSAGE_QUEUE = "IDMESSAGE_QUEUE";
  public final static String CREATIONDATE = "CREATIONDATE";
  public final static String SUBJECT = "SUBJECT";
  public final static String FROM = "FROM";
  public final static String TO = "TO";
  public final static String CC = "CC";
  public final static String BCC = "BCC";
  public final static String BODY = "BODY";
  public final static String HTML = "HTML";
  public final static String ATTACHMENTS = "ATTACHMENTS";
  protected Message_queue_jdo(){
    
  }
  protected void setIdmessage_queue(Integer idmessage_queue){
    this.setField(IDMESSAGE_QUEUE,idmessage_queue);
  }
  public final Integer getIdmessage_queue(){
    Object[] val = this.getField(IDMESSAGE_QUEUE);
    if(val != null && val[0] != null){
      if(val[0] instanceof java.lang.Short){
        return new Integer(((java.lang.Short)val[0]).toString());
      }else{
        return (Integer)val[0];
      }
    }else{
      return null;
    }
  }
  public void setCreationdate(java.sql.Timestamp creationdate){
    this.setField(CREATIONDATE,creationdate);
  }
  public java.sql.Timestamp getCreationdate(){
    Object[] val = this.getField(CREATIONDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setSubject(String subject){
    this.setField(SUBJECT,subject);
  }
  public String getSubject(){
    Object[] val = this.getField(SUBJECT);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setFrom(String from){
    this.setField(FROM,from);
  }
  public String getFrom(){
    Object[] val = this.getField(FROM);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setTo(String to){
    this.setField(TO,to);
  }
  public String getTo(){
    Object[] val = this.getField(TO);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCc(String cc){
    this.setField(CC,cc);
  }
  public String getCc(){
    Object[] val = this.getField(CC);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setBcc(String bcc){
    this.setField(BCC,bcc);
  }
  public String getBcc(){
    Object[] val = this.getField(BCC);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setBody(String body){
    this.setField(BODY,body);
  }
  public String getBody(){
    Object[] val = this.getField(BODY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setHtml(Boolean html){
    this.setField(HTML,html);
  }
  public Boolean getHtml(){
    Object[] val = this.getField(HTML);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setAttachments(String attachments){
    this.setField(ATTACHMENTS,attachments);
  }
  public String getAttachments(){
    Object[] val = this.getField(ATTACHMENTS);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "IDMESSAGE_QUEUE";
  }
}