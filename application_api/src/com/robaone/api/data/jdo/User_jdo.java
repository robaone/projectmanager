/*
* Created on Sep 25, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class User_jdo extends RO_JDO{
  public final static String IDUSER = "IDUSER";
  public final static String USERNAME = "USERNAME";
  public final static String FIRST_NAME = "FIRST_NAME";
  public final static String LAST_NAME = "LAST_NAME";
  public final static String FAILED_ATTEMPTS = "FAILED_ATTEMPTS";
  public final static String PASSWORD = "PASSWORD";
  public final static String ACTIVE = "ACTIVE";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String MODIFIED_DATE = "MODIFIED_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFICATION_HOST = "MODIFICATION_HOST";
  public final static String META_DATA = "META_DATA";
  public final static String RESET = "RESET";
  protected User_jdo(){
    
  }
  protected void setIduser(Integer iduser){
    this.setField(IDUSER,iduser);
  }
  public final Integer getIduser(){
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
  public void setFirst_name(String first_name){
    this.setField(FIRST_NAME,first_name);
  }
  public String getFirst_name(){
    Object[] val = this.getField(FIRST_NAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setLast_name(String last_name){
    this.setField(LAST_NAME,last_name);
  }
  public String getLast_name(){
    Object[] val = this.getField(LAST_NAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setFailed_attempts(Integer failed_attempts){
    this.setField(FAILED_ATTEMPTS,failed_attempts);
  }
  public Integer getFailed_attempts(){
    Object[] val = this.getField(FAILED_ATTEMPTS);
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
  public void setMeta_data(String meta_data){
    this.setField(META_DATA,meta_data);
  }
  public String getMeta_data(){
    Object[] val = this.getField(META_DATA);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setReset(Integer reset){
    this.setField(RESET,reset);
  }
  public Integer getReset(){
    Object[] val = this.getField(RESET);
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
  public String getIdentityName() {
    return "IDUSER";
  }
}