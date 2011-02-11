/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_sohvac_service_jdo extends RO_JDO{
  public final static String SOHVAC_SERVICEID = "SOHVAC_SERVICEID";
  public final static String SOHVAC_NAME = "SOHVAC_NAME";
  public final static String SOHVAC_DESCRIPTION = "SOHVAC_DESCRIPTION";
  public final static String SOHVAC_ACTIVE = "SOHVAC_ACTIVE";
  public final static String SOHVAC_MAIN = "SOHVAC_MAIN";
  protected Dbo_sohvac_service_jdo(){
    
  }
  protected void setSohvac_serviceid(Integer sohvac_serviceid){
    this.setField(SOHVAC_SERVICEID,sohvac_serviceid);
  }
  public final Integer getSohvac_serviceid(){
    Object[] val = this.getField(SOHVAC_SERVICEID);
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
  public void setSohvac_name(String sohvac_name){
    this.setField(SOHVAC_NAME,sohvac_name);
  }
  public String getSohvac_name(){
    Object[] val = this.getField(SOHVAC_NAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_description(String sohvac_description){
    this.setField(SOHVAC_DESCRIPTION,sohvac_description);
  }
  public String getSohvac_description(){
    Object[] val = this.getField(SOHVAC_DESCRIPTION);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_active(Boolean sohvac_active){
    this.setField(SOHVAC_ACTIVE,sohvac_active);
  }
  public Boolean getSohvac_active(){
    Object[] val = this.getField(SOHVAC_ACTIVE);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_main(String sohvac_main){
    this.setField(SOHVAC_MAIN,sohvac_main);
  }
  public String getSohvac_main(){
    Object[] val = this.getField(SOHVAC_MAIN);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "SOHVAC_SERVICEID";
  }
}