/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_profilepropertydefinition_jdo extends RO_JDO{
  public final static String PROPERTYDEFINITIONID = "PROPERTYDEFINITIONID";
  public final static String PORTALID = "PORTALID";
  public final static String MODULEDEFID = "MODULEDEFID";
  public final static String DELETED = "DELETED";
  public final static String DATATYPE = "DATATYPE";
  public final static String DEFAULTVALUE = "DEFAULTVALUE";
  public final static String PROPERTYCATEGORY = "PROPERTYCATEGORY";
  public final static String PROPERTYNAME = "PROPERTYNAME";
  public final static String LENGTH = "LENGTH";
  public final static String REQUIRED = "REQUIRED";
  public final static String VALIDATIONEXPRESSION = "VALIDATIONEXPRESSION";
  public final static String VIEWORDER = "VIEWORDER";
  public final static String VISIBLE = "VISIBLE";
  public final static String CREATEDBYUSERID = "CREATEDBYUSERID";
  public final static String CREATEDONDATE = "CREATEDONDATE";
  public final static String LASTMODIFIEDBYUSERID = "LASTMODIFIEDBYUSERID";
  public final static String LASTMODIFIEDONDATE = "LASTMODIFIEDONDATE";
  protected Dbo_profilepropertydefinition_jdo(){
    
  }
  protected void setPropertydefinitionid(Integer propertydefinitionid){
    this.setField(PROPERTYDEFINITIONID,propertydefinitionid);
  }
  public final Integer getPropertydefinitionid(){
    Object[] val = this.getField(PROPERTYDEFINITIONID);
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
  public void setPortalid(Integer portalid){
    this.setField(PORTALID,portalid);
  }
  public Integer getPortalid(){
    Object[] val = this.getField(PORTALID);
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
  public void setModuledefid(Integer moduledefid){
    this.setField(MODULEDEFID,moduledefid);
  }
  public Integer getModuledefid(){
    Object[] val = this.getField(MODULEDEFID);
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
  public void setDeleted(Boolean deleted){
    this.setField(DELETED,deleted);
  }
  public Boolean getDeleted(){
    Object[] val = this.getField(DELETED);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setDatatype(Integer datatype){
    this.setField(DATATYPE,datatype);
  }
  public Integer getDatatype(){
    Object[] val = this.getField(DATATYPE);
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
  public void setDefaultvalue(String defaultvalue){
    this.setField(DEFAULTVALUE,defaultvalue);
  }
  public String getDefaultvalue(){
    Object[] val = this.getField(DEFAULTVALUE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setPropertycategory(String propertycategory){
    this.setField(PROPERTYCATEGORY,propertycategory);
  }
  public String getPropertycategory(){
    Object[] val = this.getField(PROPERTYCATEGORY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setPropertyname(String propertyname){
    this.setField(PROPERTYNAME,propertyname);
  }
  public String getPropertyname(){
    Object[] val = this.getField(PROPERTYNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setLength(Integer length){
    this.setField(LENGTH,length);
  }
  public Integer getLength(){
    Object[] val = this.getField(LENGTH);
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
  public void setRequired(Boolean required){
    this.setField(REQUIRED,required);
  }
  public Boolean getRequired(){
    Object[] val = this.getField(REQUIRED);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setValidationexpression(String validationexpression){
    this.setField(VALIDATIONEXPRESSION,validationexpression);
  }
  public String getValidationexpression(){
    Object[] val = this.getField(VALIDATIONEXPRESSION);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setVieworder(Integer vieworder){
    this.setField(VIEWORDER,vieworder);
  }
  public Integer getVieworder(){
    Object[] val = this.getField(VIEWORDER);
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
  public void setVisible(Boolean visible){
    this.setField(VISIBLE,visible);
  }
  public Boolean getVisible(){
    Object[] val = this.getField(VISIBLE);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setCreatedbyuserid(Integer createdbyuserid){
    this.setField(CREATEDBYUSERID,createdbyuserid);
  }
  public Integer getCreatedbyuserid(){
    Object[] val = this.getField(CREATEDBYUSERID);
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
  public void setCreatedondate(java.sql.Timestamp createdondate){
    this.setField(CREATEDONDATE,createdondate);
  }
  public java.sql.Timestamp getCreatedondate(){
    Object[] val = this.getField(CREATEDONDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setLastmodifiedbyuserid(Integer lastmodifiedbyuserid){
    this.setField(LASTMODIFIEDBYUSERID,lastmodifiedbyuserid);
  }
  public Integer getLastmodifiedbyuserid(){
    Object[] val = this.getField(LASTMODIFIEDBYUSERID);
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
  public void setLastmodifiedondate(java.sql.Timestamp lastmodifiedondate){
    this.setField(LASTMODIFIEDONDATE,lastmodifiedondate);
  }
  public java.sql.Timestamp getLastmodifiedondate(){
    Object[] val = this.getField(LASTMODIFIEDONDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "PROPERTYDEFINITIONID";
  }
}