/*
* Created on Sep 25, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Apps_jdo extends RO_JDO{
  public final static String IDAPPS = "IDAPPS";
  public final static String NAME = "NAME";
  public final static String CALLBACK_URL = "CALLBACK_URL";
  public final static String DESCRIPTION = "DESCRIPTION";
  public final static String CONSUMER_KEY = "CONSUMER_KEY";
  public final static String CONSUMER_SECRET = "CONSUMER_SECRET";
  public final static String ACTIVE = "ACTIVE";
  public final static String IDUSER = "IDUSER";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATED_DATE = "CREATED_DATE";
  public final static String MODIFIED_DATE = "MODIFIED_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFICATION_HOST = "MODIFICATION_HOST";
  public final static String META_DATA = "META_DATA";
  protected Apps_jdo(){
    
  }
  protected void setIdapps(Integer idapps){
    this.setField(IDAPPS,idapps);
  }
  public final Integer getIdapps(){
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
  public void setName(String name){
    this.setField(NAME,name);
  }
  public String getName(){
    Object[] val = this.getField(NAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCallback_url(String callback_url){
    this.setField(CALLBACK_URL,callback_url);
  }
  public String getCallback_url(){
    Object[] val = this.getField(CALLBACK_URL);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setDescription(String description){
    this.setField(DESCRIPTION,description);
  }
  public String getDescription(){
    Object[] val = this.getField(DESCRIPTION);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setConsumer_key(String consumer_key){
    this.setField(CONSUMER_KEY,consumer_key);
  }
  public String getConsumer_key(){
    Object[] val = this.getField(CONSUMER_KEY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setConsumer_secret(String consumer_secret){
    this.setField(CONSUMER_SECRET,consumer_secret);
  }
  public String getConsumer_secret(){
    Object[] val = this.getField(CONSUMER_SECRET);
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
  public void setCreated_date(java.sql.Timestamp created_date){
    this.setField(CREATED_DATE,created_date);
  }
  public java.sql.Timestamp getCreated_date(){
    Object[] val = this.getField(CREATED_DATE);
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
  public String getIdentityName() {
    return "IDAPPS";
  }
}