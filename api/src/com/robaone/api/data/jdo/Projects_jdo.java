/*
* Created on Jan 10, 2012
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Projects_jdo extends RO_JDO{
  public final static String IDPROJECTS = "IDPROJECTS";
  public final static String CONSUMERID = "CONSUMERID";
  public final static String NAME = "NAME";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String MODIFIED_DATE = "MODIFIED_DATE";
  public final static String MODIFIER_HOST = "MODIFIER_HOST";
  protected Projects_jdo(){
    
  }
  protected void setIdprojects(Integer idprojects){
    this.setField(IDPROJECTS,idprojects);
  }
  public final Integer getIdprojects(){
    Object[] val = this.getField(IDPROJECTS);
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
  public void setConsumerid(Integer consumerid){
    this.setField(CONSUMERID,consumerid);
  }
  public Integer getConsumerid(){
    Object[] val = this.getField(CONSUMERID);
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
  public void setModifier_host(String modifier_host){
    this.setField(MODIFIER_HOST,modifier_host);
  }
  public String getModifier_host(){
    Object[] val = this.getField(MODIFIER_HOST);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "IDPROJECTS";
  }
}