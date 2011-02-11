/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_userroles_jdo extends RO_JDO{
  public final static String USERROLEID = "USERROLEID";
  public final static String USERID = "USERID";
  public final static String ROLEID = "ROLEID";
  public final static String EXPIRYDATE = "EXPIRYDATE";
  public final static String ISTRIALUSED = "ISTRIALUSED";
  public final static String EFFECTIVEDATE = "EFFECTIVEDATE";
  public final static String CREATEDBYUSERID = "CREATEDBYUSERID";
  public final static String CREATEDONDATE = "CREATEDONDATE";
  public final static String LASTMODIFIEDBYUSERID = "LASTMODIFIEDBYUSERID";
  public final static String LASTMODIFIEDONDATE = "LASTMODIFIEDONDATE";
  protected Dbo_userroles_jdo(){
    
  }
  protected void setUserroleid(Integer userroleid){
    this.setField(USERROLEID,userroleid);
  }
  public final Integer getUserroleid(){
    Object[] val = this.getField(USERROLEID);
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
  public void setRoleid(Integer roleid){
    this.setField(ROLEID,roleid);
  }
  public Integer getRoleid(){
    Object[] val = this.getField(ROLEID);
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
  public void setExpirydate(java.sql.Timestamp expirydate){
    this.setField(EXPIRYDATE,expirydate);
  }
  public java.sql.Timestamp getExpirydate(){
    Object[] val = this.getField(EXPIRYDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setIstrialused(Boolean istrialused){
    this.setField(ISTRIALUSED,istrialused);
  }
  public Boolean getIstrialused(){
    Object[] val = this.getField(ISTRIALUSED);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setEffectivedate(java.sql.Timestamp effectivedate){
    this.setField(EFFECTIVEDATE,effectivedate);
  }
  public java.sql.Timestamp getEffectivedate(){
    Object[] val = this.getField(EFFECTIVEDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
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
    return "USERROLEID";
  }
}