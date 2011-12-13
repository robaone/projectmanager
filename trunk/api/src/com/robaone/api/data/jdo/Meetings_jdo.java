/*
* Created on Dec 12, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Meetings_jdo extends RO_JDO{
  public final static String IDMEETINGS = "IDMEETINGS";
  public final static String CREATED_BY = "CREATED_BY";
  public final static String CREATION_DATE = "CREATION_DATE";
  public final static String MODIFIED_BY = "MODIFIED_BY";
  public final static String MODIFICATION_DATE = "MODIFICATION_DATE";
  public final static String _VOID = "_VOID";
  public final static String STARTDATE = "STARTDATE";
  public final static String ENDDATE = "ENDDATE";
  public final static String TITLE = "TITLE";
  public final static String CALENDAR_DOC = "CALENDAR_DOC";
  protected Meetings_jdo(){
    
  }
  protected void setIdmeetings(Integer idmeetings){
    this.setField(IDMEETINGS,idmeetings);
  }
  public final Integer getIdmeetings(){
    Object[] val = this.getField(IDMEETINGS);
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
  public void setCreation_date(java.util.Date creation_date){
    this.setField(CREATION_DATE,creation_date);
  }
  public java.util.Date getCreation_date(){
    Object[] val = this.getField(CREATION_DATE);
    if(val != null && val[0] != null){
      return (java.util.Date)val[0];
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
  public void setModification_date(java.util.Date modification_date){
    this.setField(MODIFICATION_DATE,modification_date);
  }
  public java.util.Date getModification_date(){
    Object[] val = this.getField(MODIFICATION_DATE);
    if(val != null && val[0] != null){
      return (java.util.Date)val[0];
    }else{
      return null;
    }
  }
  public void set_void(Boolean _void){
    this.setField(_VOID,_void);
  }
  public Boolean get_void(){
    Object[] val = this.getField(_VOID);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setStartdate(java.util.Date startdate){
    this.setField(STARTDATE,startdate);
  }
  public java.util.Date getStartdate(){
    Object[] val = this.getField(STARTDATE);
    if(val != null && val[0] != null){
      return (java.util.Date)val[0];
    }else{
      return null;
    }
  }
  public void setEnddate(java.util.Date enddate){
    this.setField(ENDDATE,enddate);
  }
  public java.util.Date getEnddate(){
    Object[] val = this.getField(ENDDATE);
    if(val != null && val[0] != null){
      return (java.util.Date)val[0];
    }else{
      return null;
    }
  }
  public void setTitle(String title){
    this.setField(TITLE,title);
  }
  public String getTitle(){
    Object[] val = this.getField(TITLE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setCalendar_doc(String calendar_doc){
    this.setField(CALENDAR_DOC,calendar_doc);
  }
  public String getCalendar_doc(){
    Object[] val = this.getField(CALENDAR_DOC);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "IDMEETINGS";
  }
}