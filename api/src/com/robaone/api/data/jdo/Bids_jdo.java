/*
* Created on Jan 15, 2012
* Author Ansel Robateau
* http://www.robaone.com
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Bids_jdo extends RO_JDO{
  public final static String IDBIDS = "IDBIDS";
  public final static String CONTRACTORID = "CONTRACTORID";
  public final static String PROJECTID = "PROJECTID";
  public final static String TOTAL = "TOTAL";
  public final static String DETAILS = "DETAILS";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String CREATION_HOST = "CREATION_HOST";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String MODIFIED_DATE = "MODIFIED_DATE";
  public final static String MODIFIER_HOST = "MODIFIER_HOST";
  public final static String STATUS = "STATUS";
  protected Bids_jdo(){
    
  }
  protected void setIdbids(Integer idbids){
    this.setField(IDBIDS,idbids);
  }
  public final Integer getIdbids(){
    Object[] val = this.getField(IDBIDS);
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
  public void setContractorid(Integer contractorid){
    this.setField(CONTRACTORID,contractorid);
  }
  public Integer getContractorid(){
    Object[] val = this.getField(CONTRACTORID);
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
  public void setProjectid(Integer projectid){
    this.setField(PROJECTID,projectid);
  }
  public Integer getProjectid(){
    Object[] val = this.getField(PROJECTID);
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
  public void setTotal(BigDecimal total){
    this.setField(TOTAL,total);
  }
  public BigDecimal getTotal(){
    Object[] val = this.getField(TOTAL);
    if(val != null && val[0] != null){
      return (BigDecimal)val[0];
    }else{
      return null;
    }
  }
  public void setDetails(String details){
    this.setField(DETAILS,details);
  }
  public String getDetails(){
    Object[] val = this.getField(DETAILS);
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
  public void setStatus(Integer status){
    this.setField(STATUS,status);
  }
  public Integer getStatus(){
    Object[] val = this.getField(STATUS);
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
    return "IDBIDS";
  }
}