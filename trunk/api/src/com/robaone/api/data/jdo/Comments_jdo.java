/*
* Created on Dec 09, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Comments_jdo extends RO_JDO{
  public final static String IDCOMMENTS = "IDCOMMENTS";
  public final static String REFERENCEID = "REFERENCEID";
  public final static String COMMENT = "COMMENT";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String MODIFIED_DATE = "MODIFIED_DATE";
  public final static String MODIFICATION_HOST = "MODIFICATION_HOST";
  public final static String _VOID = "_VOID";
  protected Comments_jdo(){
    
  }
  protected void setIdcomments(Integer idcomments){
    this.setField(IDCOMMENTS,idcomments);
  }
  public final Integer getIdcomments(){
    Object[] val = this.getField(IDCOMMENTS);
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
  public void setReferenceid(String referenceid){
    this.setField(REFERENCEID,referenceid);
  }
  public String getReferenceid(){
    Object[] val = this.getField(REFERENCEID);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setComment(String comment){
    this.setField(COMMENT,comment);
  }
  public String getComment(){
    Object[] val = this.getField(COMMENT);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCreated_by(Integer created_by){
    this.setField(CREATED_BY,created_by);
  }
  public Integer getCreated_by(){
    Object[] val = this.getField(CREATED_BY);
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
  public void setCreation_date(java.sql.Timestamp creation_date){
    this.setField(CREATION_DATE,creation_date);
  }
  public java.sql.Timestamp getCreation_date(){
    Object[] val = this.getField(CREATION_DATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setCreation_host(String creation_host){
    this.setField(CREATION_HOST,creation_host);
  }
  public String getCreation_host(){
    Object[] val = this.getField(CREATION_HOST);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setModified_by(Integer modified_by){
    this.setField(MODIFIED_BY,modified_by);
  }
  public Integer getModified_by(){
    Object[] val = this.getField(MODIFIED_BY);
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
  public void setModified_date(java.sql.Timestamp modified_date){
    this.setField(MODIFIED_DATE,modified_date);
  }
  public java.sql.Timestamp getModified_date(){
    Object[] val = this.getField(MODIFIED_DATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setModification_host(String modification_host){
    this.setField(MODIFICATION_HOST,modification_host);
  }
  public String getModification_host(){
    Object[] val = this.getField(MODIFICATION_HOST);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void set_void(Boolean _void){
    this.setField(_VOID,_void);
  }
  public Boolean get_void(){
    Object[] val = this.getField(_VOID);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "IDCOMMENTS";
  }
}