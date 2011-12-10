/*
* Created on Dec 09, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class App_credentials_jdo extends RO_JDO{
  public final static String IDAPP_CREDENTIALS = "IDAPP_CREDENTIALS";
  public final static String REQUEST_TOKEN = "REQUEST_TOKEN";
  public final static String ACCESS_TOKEN = "ACCESS_TOKEN";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String EXPIRATION_DATE = "EXPIRATION_DATE";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String ACTIVE = "ACTIVE";
  public final static String IDAPPS = "IDAPPS";
  public final static String IDUSER = "IDUSER";
  public final static String TOKEN_SECRET = "TOKEN_SECRET";
  protected App_credentials_jdo(){
    
  }
  protected void setIdapp_credentials(Integer idapp_credentials){
    this.setField(IDAPP_CREDENTIALS,idapp_credentials);
  }
  public final Integer getIdapp_credentials(){
    Object[] val = this.getField(IDAPP_CREDENTIALS);
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
  public void setRequest_token(String request_token){
    this.setField(REQUEST_TOKEN,request_token);
  }
  public String getRequest_token(){
    Object[] val = this.getField(REQUEST_TOKEN);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setAccess_token(String access_token){
    this.setField(ACCESS_TOKEN,access_token);
  }
  public String getAccess_token(){
    Object[] val = this.getField(ACCESS_TOKEN);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCreated_by(String created_by){
    this.setField(CREATED_BY,created_by);
  }
  public String getCreated_by(){
    Object[] val = this.getField(CREATED_BY);
    if(val != null && val[0] != null){
      return (String)val[0];
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
  public void setExpiration_date(java.sql.Timestamp expiration_date){
    this.setField(EXPIRATION_DATE,expiration_date);
  }
  public java.sql.Timestamp getExpiration_date(){
    Object[] val = this.getField(EXPIRATION_DATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
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
  public void setActive(Integer active){
    this.setField(ACTIVE,active);
  }
  public Integer getActive(){
    Object[] val = this.getField(ACTIVE);
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
  public void setIdapps(Integer idapps){
    this.setField(IDAPPS,idapps);
  }
  public Integer getIdapps(){
    Object[] val = this.getField(IDAPPS);
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
  public void setIduser(Integer iduser){
    this.setField(IDUSER,iduser);
  }
  public Integer getIduser(){
    Object[] val = this.getField(IDUSER);
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
  public void setToken_secret(String token_secret){
    this.setField(TOKEN_SECRET,token_secret);
  }
  public String getToken_secret(){
    Object[] val = this.getField(TOKEN_SECRET);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "IDAPP_CREDENTIALS";
  }
}