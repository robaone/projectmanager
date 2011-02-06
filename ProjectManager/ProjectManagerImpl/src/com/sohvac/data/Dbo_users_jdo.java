/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_users_jdo extends RO_JDO{
  public final static String USERID = "USERID";
  public final static String USERNAME = "USERNAME";
  public final static String FIRSTNAME = "FIRSTNAME";
  public final static String LASTNAME = "LASTNAME";
  public final static String ISSUPERUSER = "ISSUPERUSER";
  public final static String AFFILIATEID = "AFFILIATEID";
  public final static String EMAIL = "EMAIL";
  public final static String DISPLAYNAME = "DISPLAYNAME";
  public final static String UPDATEPASSWORD = "UPDATEPASSWORD";
  public final static String LASTIPADDRESS = "LASTIPADDRESS";
  public final static String ISDELETED = "ISDELETED";
  public final static String CREATEDBYUSERID = "CREATEDBYUSERID";
  public final static String CREATEDONDATE = "CREATEDONDATE";
  public final static String LASTMODIFIEDBYUSERID = "LASTMODIFIEDBYUSERID";
  public final static String LASTMODIFIEDONDATE = "LASTMODIFIEDONDATE";
  public final static String PASSWORD = "PASSWORD";
  public final static String PHONENUMBER = "PHONENUMBER";
  public final static String ADDRESS1 = "ADDRESS1";
  public final static String ADDRESS2 = "ADDRESS2";
  public final static String CITY = "CITY";
  public final static String STATE = "STATE";
  public final static String ZIP = "ZIP";
  public final static String ZIP_EXT = "ZIP_EXT";
  public final static String TYPE = "TYPE";
  protected Dbo_users_jdo(){
    
  }
  protected void setUserid(Integer userid){
    this.setField(USERID,userid);
  }
  public final Integer getUserid(){
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
  public void setUsername(String username){
    this.setField(USERNAME,username);
  }
  public String getUsername(){
    Object[] val = this.getField(USERNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setFirstname(String firstname){
    this.setField(FIRSTNAME,firstname);
  }
  public String getFirstname(){
    Object[] val = this.getField(FIRSTNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setLastname(String lastname){
    this.setField(LASTNAME,lastname);
  }
  public String getLastname(){
    Object[] val = this.getField(LASTNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setIssuperuser(Boolean issuperuser){
    this.setField(ISSUPERUSER,issuperuser);
  }
  public Boolean getIssuperuser(){
    Object[] val = this.getField(ISSUPERUSER);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setAffiliateid(Integer affiliateid){
    this.setField(AFFILIATEID,affiliateid);
  }
  public Integer getAffiliateid(){
    Object[] val = this.getField(AFFILIATEID);
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
  public void setEmail(String email){
    this.setField(EMAIL,email);
  }
  public String getEmail(){
    Object[] val = this.getField(EMAIL);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setDisplayname(String displayname){
    this.setField(DISPLAYNAME,displayname);
  }
  public String getDisplayname(){
    Object[] val = this.getField(DISPLAYNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setUpdatepassword(Boolean updatepassword){
    this.setField(UPDATEPASSWORD,updatepassword);
  }
  public Boolean getUpdatepassword(){
    Object[] val = this.getField(UPDATEPASSWORD);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setLastipaddress(String lastipaddress){
    this.setField(LASTIPADDRESS,lastipaddress);
  }
  public String getLastipaddress(){
    Object[] val = this.getField(LASTIPADDRESS);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setIsdeleted(Boolean isdeleted){
    this.setField(ISDELETED,isdeleted);
  }
  public Boolean getIsdeleted(){
    Object[] val = this.getField(ISDELETED);
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
  public void setPassword(String password){
    this.setField(PASSWORD,password);
  }
  public String getPassword(){
    Object[] val = this.getField(PASSWORD);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setPhonenumber(String phonenumber){
    this.setField(PHONENUMBER,phonenumber);
  }
  public String getPhonenumber(){
    Object[] val = this.getField(PHONENUMBER);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setAddress1(String address1){
    this.setField(ADDRESS1,address1);
  }
  public String getAddress1(){
    Object[] val = this.getField(ADDRESS1);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setAddress2(String address2){
    this.setField(ADDRESS2,address2);
  }
  public String getAddress2(){
    Object[] val = this.getField(ADDRESS2);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCity(String city){
    this.setField(CITY,city);
  }
  public String getCity(){
    Object[] val = this.getField(CITY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setState(String state){
    this.setField(STATE,state);
  }
  public String getState(){
    Object[] val = this.getField(STATE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setZip(BigDecimal zip){
    this.setField(ZIP,zip);
  }
  public BigDecimal getZip(){
    Object[] val = this.getField(ZIP);
    if(val != null && val[0] != null){
      return (BigDecimal)val[0];
    }else{
      return null;
    }
  }
  public void setZip_ext(BigDecimal zip_ext){
    this.setField(ZIP_EXT,zip_ext);
  }
  public BigDecimal getZip_ext(){
    Object[] val = this.getField(ZIP_EXT);
    if(val != null && val[0] != null){
      return (BigDecimal)val[0];
    }else{
      return null;
    }
  }
  public void setType(String type){
    this.setField(TYPE,type);
  }
  public String getType(){
    Object[] val = this.getField(TYPE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "USERID";
  }
}