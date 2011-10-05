/*
* Created on Oct 04, 2011
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Roles_jdo extends RO_JDO{
  public final static String IDROLES = "IDROLES";
  public final static String IDUSER = "IDUSER";
  public final static String ROLE = "ROLE";
  protected Roles_jdo(){
    
  }
  protected void setIdroles(Integer idroles){
    this.setField(IDROLES,idroles);
  }
  public final Integer getIdroles(){
    Object[] val = this.getField(IDROLES);
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
  public void setRole(Integer role){
    this.setField(ROLE,role);
  }
  public Integer getRole(){
    Object[] val = this.getField(ROLE);
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
    return "IDROLES";
  }
}