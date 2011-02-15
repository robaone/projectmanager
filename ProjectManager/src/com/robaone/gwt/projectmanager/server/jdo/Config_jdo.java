/*
* Created on Feb 14, 2011
*
*/
package com.robaone.gwt.projectmanager.server.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Config_jdo extends RO_JDO{
  public final static String ID = "ID";
  public final static String NAME = "NAME";
  public final static String PARENT = "PARENT";
  public final static String TYPE = "TYPE";
  public final static String TITLE = "TITLE";
  public final static String DESCRIPTION = "DESCRIPTION";
  public final static String VALUE = "VALUE";
  protected Config_jdo(){
    
  }
  protected void setId(BigDecimal id){
    this.setField(ID,id);
  }
  public final BigDecimal getId(){
    Object[] val = this.getField(ID);
    if(val != null && val[0] != null){
      return (BigDecimal)val[0];
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
  public void setParent(BigDecimal parent){
    this.setField(PARENT,parent);
  }
  public BigDecimal getParent(){
    Object[] val = this.getField(PARENT);
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
  public void setValue(String value){
    this.setField(VALUE,value);
  }
  public String getValue(){
    Object[] val = this.getField(VALUE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "ID";
  }
}