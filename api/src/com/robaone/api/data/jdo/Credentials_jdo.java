/*
* Created on Oct 04, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Credentials_jdo extends RO_JDO{
  public final static String IDCREDENTIALS = "IDCREDENTIALS";
  public final static String IDUSER = "IDUSER";
  public final static String AUTHENTICATOR = "AUTHENTICATOR";
  public final static String AUTHDATA = "AUTHDATA";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String MODIFICATION_DATE = "MODIFICATION_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFICATION_HOST = "MODIFICATION_HOST";
  protected Credentials_jdo(){
    
  }
  protected void setIdcredentials(Integer idcredentials){
    this.setField(IDCREDENTIALS,idcredentials);
  }
  public final Integer getIdcredentials(){
    Object[] val = this.getField(IDCREDENTIALS);
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
  public void setAuthenticator(String authenticator){
    this.setField(AUTHENTICATOR,authenticator);
  }
  public String getAuthenticator(){
    Object[] val = this.getField(AUTHENTICATOR);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setAuthdata(String authdata){
    this.setField(AUTHDATA,authdata);
  }
  public String getAuthdata(){
    Object[] val = this.getField(AUTHDATA);
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
  public void setModified_by(String modified_by){
    this.setField(MODIFIED_BY,modified_by);
  }
  public String getModified_by(){
    Object[] val = this.getField(MODIFIED_BY);
    if(val != null && val[0] != null){
      return (String)val[0];
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
  public void setModification_date(java.sql.Timestamp modification_date){
    this.setField(MODIFICATION_DATE,modification_date);
  }
  public java.sql.Timestamp getModification_date(){
    Object[] val = this.getField(MODIFICATION_DATE);
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
  public String getIdentityName() {
    return "IDCREDENTIALS";
  }
}