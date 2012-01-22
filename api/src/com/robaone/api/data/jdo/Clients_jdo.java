/*
* Created on Jan 22, 2012
* Author Ansel Robateau
* http://www.robaone.com
*
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Clients_jdo extends RO_JDO{
  public final static String IDCLIENTS = "IDCLIENTS";
  public final static String IDUSER = "IDUSER";
  public final static String IDOWNER = "IDOWNER";
  public final static String STATUS = "STATUS";
  protected Clients_jdo(){
    
  }
  protected void setIdclients(Integer idclients){
    this.setField(IDCLIENTS,idclients);
  }
  public final Integer getIdclients(){
    Object[] val = this.getField(IDCLIENTS);
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
  public void setIdowner(Integer idowner){
    this.setField(IDOWNER,idowner);
  }
  public Integer getIdowner(){
    Object[] val = this.getField(IDOWNER);
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
    return "IDCLIENTS";
  }
}