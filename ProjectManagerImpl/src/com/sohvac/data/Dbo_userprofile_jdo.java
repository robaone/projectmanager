/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_userprofile_jdo extends RO_JDO{
  public final static String PROFILEID = "PROFILEID";
  public final static String USERID = "USERID";
  public final static String PROPERTYDEFINITIONID = "PROPERTYDEFINITIONID";
  public final static String PROPERTYVALUE = "PROPERTYVALUE";
  public final static String PROPERTYTEXT = "PROPERTYTEXT";
  public final static String VISIBILITY = "VISIBILITY";
  public final static String LASTUPDATEDDATE = "LASTUPDATEDDATE";
  protected Dbo_userprofile_jdo(){
    
  }
  protected void setProfileid(Integer profileid){
    this.setField(PROFILEID,profileid);
  }
  public final Integer getProfileid(){
    Object[] val = this.getField(PROFILEID);
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
  public void setUserid(Integer userid){
    this.setField(USERID,userid);
  }
  public Integer getUserid(){
    Object[] val = this.getField(USERID);
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
  public void setPropertydefinitionid(Integer propertydefinitionid){
    this.setField(PROPERTYDEFINITIONID,propertydefinitionid);
  }
  public Integer getPropertydefinitionid(){
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
  public void setPropertyvalue(String propertyvalue){
    this.setField(PROPERTYVALUE,propertyvalue);
  }
  public String getPropertyvalue(){
    Object[] val = this.getField(PROPERTYVALUE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setPropertytext(String propertytext){
    this.setField(PROPERTYTEXT,propertytext);
  }
  public String getPropertytext(){
    Object[] val = this.getField(PROPERTYTEXT);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setVisibility(Integer visibility){
    this.setField(VISIBILITY,visibility);
  }
  public Integer getVisibility(){
    Object[] val = this.getField(VISIBILITY);
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
  public void setLastupdateddate(java.sql.Timestamp lastupdateddate){
    this.setField(LASTUPDATEDDATE,lastupdateddate);
  }
  public java.sql.Timestamp getLastupdateddate(){
    Object[] val = this.getField(LASTUPDATEDDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "PROFILEID";
  }
}