/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.util.Date;
import com.robaone.jdo.RO_JDO;


public class Dbo_sohvac_listing_jdo extends RO_JDO{
  public final static String SOHVAC_LISTINGID = "SOHVAC_LISTINGID";
  public final static String SOHVAC_BUSINESSNAME = "SOHVAC_BUSINESSNAME";
  public final static String SOHVAC_CONTACTPERSON = "SOHVAC_CONTACTPERSON";
  public final static String SOHVAC_ADDRESS1 = "SOHVAC_ADDRESS1";
  public final static String SOHVAC_ADDRESS2 = "SOHVAC_ADDRESS2";
  public final static String SOHVAC_CITY = "SOHVAC_CITY";
  public final static String SOHVAC_REGIONCODE = "SOHVAC_REGIONCODE";
  public final static String SOHVAC_COUNTRYCODE = "SOHVAC_COUNTRYCODE";
  public final static String SOHVAC_POSTALCODE = "SOHVAC_POSTALCODE";
  public final static String SOHVAC_PHONE1 = "SOHVAC_PHONE1";
  public final static String SOHVAC_PHONE2 = "SOHVAC_PHONE2";
  public final static String SOHVAC_BUSINESSWEBSITE = "SOHVAC_BUSINESSWEBSITE";
  public final static String SOHVAC_LOGOIMAGE = "SOHVAC_LOGOIMAGE";
  public final static String SOHVAC_BBB = "SOHVAC_BBB";
  public final static String SOHVAC_ABOUTBUSINESS = "SOHVAC_ABOUTBUSINESS";
  public final static String SOHVAC_INBUSINESSSINCE = "SOHVAC_INBUSINESSSINCE";
  public final static String SOHVAC_PORTALID = "SOHVAC_PORTALID";
  public final static String SOHVAC_USERID = "SOHVAC_USERID";
  public final static String SOHVAC_SUBSCRIPTIONID = "SOHVAC_SUBSCRIPTIONID";
  public final static String SOHVAC_ACTIVE = "SOHVAC_ACTIVE";
  public final static String SOHVAC_RESIDENTIAL = "SOHVAC_RESIDENTIAL";
  public final static String SOHVAC_COMMERCIAL = "SOHVAC_COMMERCIAL";
  public final static String SOHVAC_GOVERNMENT = "SOHVAC_GOVERNMENT";
  public final static String SOHVAC_INDUSTRIAL = "SOHVAC_INDUSTRIAL";
  public final static String SOHVAC_24HOUR = "SOHVAC_24HOUR";
  public final static String SOHVAC_FINANCING = "SOHVAC_FINANCING";
  public final static String SOHVAC_CREDITCARD = "SOHVAC_CREDITCARD";
  public final static String SOHVAC_BONDED = "SOHVAC_BONDED";
  public final static String SOHVAC_INSURED = "SOHVAC_INSURED";
  public final static String SOHVAC_INSURANCE = "SOHVAC_INSURANCE";
  public final static String SOHVAC_LICENSEINFO = "SOHVAC_LICENSEINFO";
  public final static String SOHVAC_CREATEDDATE = "SOHVAC_CREATEDDATE";
  public final static String SOHVAC_CREATORID = "SOHVAC_CREATORID";
  public final static String SOHVAC_EMAILADDRESS = "SOHVAC_EMAILADDRESS";
  public final static String SOHVAC_EMAIL = "SOHVAC_EMAIL";
  public final static String SOHVAC_CONTRACTORTYPE = "SOHVAC_CONTRACTORTYPE";
  public final static String SOHVAC_HOURSOFOPERATION = "SOHVAC_HOURSOFOPERATION";
  public final static String SOHVAC_LOGOSTATEMENTBRAND = "SOHVAC_LOGOSTATEMENTBRAND";
  public final static String SOHVAC_LOGOSTATEMENTBBB = "SOHVAC_LOGOSTATEMENTBBB";
  public final static String SOHVAC_EMPLOYEESCOUNT = "SOHVAC_EMPLOYEESCOUNT";
  public final static String SOHVAC_UNIONCOMPANY = "SOHVAC_UNIONCOMPANY";
  public final static String SOHVAC_AFTERHOURSSERVICECHARGES = "SOHVAC_AFTERHOURSSERVICECHARGES";
  public final static String SOHVAC_NEWINSTALLDEPOSIT = "SOHVAC_NEWINSTALLDEPOSIT";
  public final static String SOHVAC_LASTMINUTESERVICECALLS = "SOHVAC_LASTMINUTESERVICECALLS";
  public final static String SOHVAC_INSURANCEWORK = "SOHVAC_INSURANCEWORK";
  public final static String SOHVAC_HOMEWARRANTYWORK = "SOHVAC_HOMEWARRANTYWORK";
  public final static String SOHVAC_FREEESTIMATES = "SOHVAC_FREEESTIMATES";
  public final static String SOHVAC_INSPECTIONREPORTS = "SOHVAC_INSPECTIONREPORTS";
  public final static String SOHVAC_COMPLAINTSINLASTFIVEYEARS = "SOHVAC_COMPLAINTSINLASTFIVEYEARS";
  public final static String SOHVAC_FURNISHALLMATERIALS = "SOHVAC_FURNISHALLMATERIALS";
  public final static String SOHVAC_WORKSWITHBROKERS = "SOHVAC_WORKSWITHBROKERS";
  public final static String SOHVAC_RENTALSLEASING = "SOHVAC_RENTALSLEASING";
  public final static String SOHVAC_OUTBIDCOMPETITORS = "SOHVAC_OUTBIDCOMPETITORS";
  public final static String SOHVAC_SERVICEWARRANTY = "SOHVAC_SERVICEWARRANTY";
  public final static String SOHVAC_NEWINSTALLWARRANTY = "SOHVAC_NEWINSTALLWARRANTY";
  public final static String SOHVAC_ADDITIONALWARRANTIES = "SOHVAC_ADDITIONALWARRANTIES";
  public final static String SOHVAC_WARRANTYSTATEMENT = "SOHVAC_WARRANTYSTATEMENT";
  protected Dbo_sohvac_listing_jdo(){
    
  }
  protected void setSohvac_listingid(Integer sohvac_listingid){
    this.setField(SOHVAC_LISTINGID,sohvac_listingid);
  }
  public final Integer getSohvac_listingid(){
    Object[] val = this.getField(SOHVAC_LISTINGID);
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
  public void setSohvac_businessname(String sohvac_businessname){
    this.setField(SOHVAC_BUSINESSNAME,sohvac_businessname);
  }
  public String getSohvac_businessname(){
    Object[] val = this.getField(SOHVAC_BUSINESSNAME);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_contactperson(String sohvac_contactperson){
    this.setField(SOHVAC_CONTACTPERSON,sohvac_contactperson);
  }
  public String getSohvac_contactperson(){
    Object[] val = this.getField(SOHVAC_CONTACTPERSON);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_address1(String sohvac_address1){
    this.setField(SOHVAC_ADDRESS1,sohvac_address1);
  }
  public String getSohvac_address1(){
    Object[] val = this.getField(SOHVAC_ADDRESS1);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_address2(String sohvac_address2){
    this.setField(SOHVAC_ADDRESS2,sohvac_address2);
  }
  public String getSohvac_address2(){
    Object[] val = this.getField(SOHVAC_ADDRESS2);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_city(String sohvac_city){
    this.setField(SOHVAC_CITY,sohvac_city);
  }
  public String getSohvac_city(){
    Object[] val = this.getField(SOHVAC_CITY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_regioncode(String sohvac_regioncode){
    this.setField(SOHVAC_REGIONCODE,sohvac_regioncode);
  }
  public String getSohvac_regioncode(){
    Object[] val = this.getField(SOHVAC_REGIONCODE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_countrycode(String sohvac_countrycode){
    this.setField(SOHVAC_COUNTRYCODE,sohvac_countrycode);
  }
  public String getSohvac_countrycode(){
    Object[] val = this.getField(SOHVAC_COUNTRYCODE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_postalcode(String sohvac_postalcode){
    this.setField(SOHVAC_POSTALCODE,sohvac_postalcode);
  }
  public String getSohvac_postalcode(){
    Object[] val = this.getField(SOHVAC_POSTALCODE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_phone1(String sohvac_phone1){
    this.setField(SOHVAC_PHONE1,sohvac_phone1);
  }
  public String getSohvac_phone1(){
    Object[] val = this.getField(SOHVAC_PHONE1);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_phone2(String sohvac_phone2){
    this.setField(SOHVAC_PHONE2,sohvac_phone2);
  }
  public String getSohvac_phone2(){
    Object[] val = this.getField(SOHVAC_PHONE2);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_businesswebsite(String sohvac_businesswebsite){
    this.setField(SOHVAC_BUSINESSWEBSITE,sohvac_businesswebsite);
  }
  public String getSohvac_businesswebsite(){
    Object[] val = this.getField(SOHVAC_BUSINESSWEBSITE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_logoimage(String sohvac_logoimage){
    this.setField(SOHVAC_LOGOIMAGE,sohvac_logoimage);
  }
  public String getSohvac_logoimage(){
    Object[] val = this.getField(SOHVAC_LOGOIMAGE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_bbb(Boolean sohvac_bbb){
    this.setField(SOHVAC_BBB,sohvac_bbb);
  }
  public Boolean getSohvac_bbb(){
    Object[] val = this.getField(SOHVAC_BBB);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_aboutbusiness(String sohvac_aboutbusiness){
    this.setField(SOHVAC_ABOUTBUSINESS,sohvac_aboutbusiness);
  }
  public String getSohvac_aboutbusiness(){
    Object[] val = this.getField(SOHVAC_ABOUTBUSINESS);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_inbusinesssince(Integer sohvac_inbusinesssince){
    this.setField(SOHVAC_INBUSINESSSINCE,sohvac_inbusinesssince);
  }
  public Integer getSohvac_inbusinesssince(){
    Object[] val = this.getField(SOHVAC_INBUSINESSSINCE);
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
  public void setSohvac_portalid(Integer sohvac_portalid){
    this.setField(SOHVAC_PORTALID,sohvac_portalid);
  }
  public Integer getSohvac_portalid(){
    Object[] val = this.getField(SOHVAC_PORTALID);
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
  public void setSohvac_userid(Integer sohvac_userid){
    this.setField(SOHVAC_USERID,sohvac_userid);
  }
  public Integer getSohvac_userid(){
    Object[] val = this.getField(SOHVAC_USERID);
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
  public void setSohvac_subscriptionid(Integer sohvac_subscriptionid){
    this.setField(SOHVAC_SUBSCRIPTIONID,sohvac_subscriptionid);
  }
  public Integer getSohvac_subscriptionid(){
    Object[] val = this.getField(SOHVAC_SUBSCRIPTIONID);
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
  public void setSohvac_residential(Boolean sohvac_residential){
    this.setField(SOHVAC_RESIDENTIAL,sohvac_residential);
  }
  public Boolean getSohvac_residential(){
    Object[] val = this.getField(SOHVAC_RESIDENTIAL);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_commercial(Boolean sohvac_commercial){
    this.setField(SOHVAC_COMMERCIAL,sohvac_commercial);
  }
  public Boolean getSohvac_commercial(){
    Object[] val = this.getField(SOHVAC_COMMERCIAL);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_government(Boolean sohvac_government){
    this.setField(SOHVAC_GOVERNMENT,sohvac_government);
  }
  public Boolean getSohvac_government(){
    Object[] val = this.getField(SOHVAC_GOVERNMENT);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_industrial(Boolean sohvac_industrial){
    this.setField(SOHVAC_INDUSTRIAL,sohvac_industrial);
  }
  public Boolean getSohvac_industrial(){
    Object[] val = this.getField(SOHVAC_INDUSTRIAL);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_24hour(Boolean sohvac_24hour){
    this.setField(SOHVAC_24HOUR,sohvac_24hour);
  }
  public Boolean getSohvac_24hour(){
    Object[] val = this.getField(SOHVAC_24HOUR);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_financing(Boolean sohvac_financing){
    this.setField(SOHVAC_FINANCING,sohvac_financing);
  }
  public Boolean getSohvac_financing(){
    Object[] val = this.getField(SOHVAC_FINANCING);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_creditcard(Boolean sohvac_creditcard){
    this.setField(SOHVAC_CREDITCARD,sohvac_creditcard);
  }
  public Boolean getSohvac_creditcard(){
    Object[] val = this.getField(SOHVAC_CREDITCARD);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_bonded(Boolean sohvac_bonded){
    this.setField(SOHVAC_BONDED,sohvac_bonded);
  }
  public Boolean getSohvac_bonded(){
    Object[] val = this.getField(SOHVAC_BONDED);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_insured(Boolean sohvac_insured){
    this.setField(SOHVAC_INSURED,sohvac_insured);
  }
  public Boolean getSohvac_insured(){
    Object[] val = this.getField(SOHVAC_INSURED);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_insurance(String sohvac_insurance){
    this.setField(SOHVAC_INSURANCE,sohvac_insurance);
  }
  public String getSohvac_insurance(){
    Object[] val = this.getField(SOHVAC_INSURANCE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_licenseinfo(String sohvac_licenseinfo){
    this.setField(SOHVAC_LICENSEINFO,sohvac_licenseinfo);
  }
  public String getSohvac_licenseinfo(){
    Object[] val = this.getField(SOHVAC_LICENSEINFO);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_createddate(java.sql.Timestamp sohvac_createddate){
    this.setField(SOHVAC_CREATEDDATE,sohvac_createddate);
  }
  public java.sql.Timestamp getSohvac_createddate(){
    Object[] val = this.getField(SOHVAC_CREATEDDATE);
    if(val != null && val[0] != null){
      return (java.sql.Timestamp)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_creatorid(Integer sohvac_creatorid){
    this.setField(SOHVAC_CREATORID,sohvac_creatorid);
  }
  public Integer getSohvac_creatorid(){
    Object[] val = this.getField(SOHVAC_CREATORID);
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
  public void setSohvac_emailaddress(String sohvac_emailaddress){
    this.setField(SOHVAC_EMAILADDRESS,sohvac_emailaddress);
  }
  public String getSohvac_emailaddress(){
    Object[] val = this.getField(SOHVAC_EMAILADDRESS);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_email(String sohvac_email){
    this.setField(SOHVAC_EMAIL,sohvac_email);
  }
  public String getSohvac_email(){
    Object[] val = this.getField(SOHVAC_EMAIL);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_contractortype(String sohvac_contractortype){
    this.setField(SOHVAC_CONTRACTORTYPE,sohvac_contractortype);
  }
  public String getSohvac_contractortype(){
    Object[] val = this.getField(SOHVAC_CONTRACTORTYPE);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_hoursofoperation(String sohvac_hoursofoperation){
    this.setField(SOHVAC_HOURSOFOPERATION,sohvac_hoursofoperation);
  }
  public String getSohvac_hoursofoperation(){
    Object[] val = this.getField(SOHVAC_HOURSOFOPERATION);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_logostatementbrand(String sohvac_logostatementbrand){
    this.setField(SOHVAC_LOGOSTATEMENTBRAND,sohvac_logostatementbrand);
  }
  public String getSohvac_logostatementbrand(){
    Object[] val = this.getField(SOHVAC_LOGOSTATEMENTBRAND);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_logostatementbbb(String sohvac_logostatementbbb){
    this.setField(SOHVAC_LOGOSTATEMENTBBB,sohvac_logostatementbbb);
  }
  public String getSohvac_logostatementbbb(){
    Object[] val = this.getField(SOHVAC_LOGOSTATEMENTBBB);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_employeescount(Integer sohvac_employeescount){
    this.setField(SOHVAC_EMPLOYEESCOUNT,sohvac_employeescount);
  }
  public Integer getSohvac_employeescount(){
    Object[] val = this.getField(SOHVAC_EMPLOYEESCOUNT);
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
  public void setSohvac_unioncompany(Boolean sohvac_unioncompany){
    this.setField(SOHVAC_UNIONCOMPANY,sohvac_unioncompany);
  }
  public Boolean getSohvac_unioncompany(){
    Object[] val = this.getField(SOHVAC_UNIONCOMPANY);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_afterhoursservicecharges(Boolean sohvac_afterhoursservicecharges){
    this.setField(SOHVAC_AFTERHOURSSERVICECHARGES,sohvac_afterhoursservicecharges);
  }
  public Boolean getSohvac_afterhoursservicecharges(){
    Object[] val = this.getField(SOHVAC_AFTERHOURSSERVICECHARGES);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_newinstalldeposit(String sohvac_newinstalldeposit){
    this.setField(SOHVAC_NEWINSTALLDEPOSIT,sohvac_newinstalldeposit);
  }
  public String getSohvac_newinstalldeposit(){
    Object[] val = this.getField(SOHVAC_NEWINSTALLDEPOSIT);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_lastminuteservicecalls(Boolean sohvac_lastminuteservicecalls){
    this.setField(SOHVAC_LASTMINUTESERVICECALLS,sohvac_lastminuteservicecalls);
  }
  public Boolean getSohvac_lastminuteservicecalls(){
    Object[] val = this.getField(SOHVAC_LASTMINUTESERVICECALLS);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_insurancework(Boolean sohvac_insurancework){
    this.setField(SOHVAC_INSURANCEWORK,sohvac_insurancework);
  }
  public Boolean getSohvac_insurancework(){
    Object[] val = this.getField(SOHVAC_INSURANCEWORK);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_homewarrantywork(Boolean sohvac_homewarrantywork){
    this.setField(SOHVAC_HOMEWARRANTYWORK,sohvac_homewarrantywork);
  }
  public Boolean getSohvac_homewarrantywork(){
    Object[] val = this.getField(SOHVAC_HOMEWARRANTYWORK);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_freeestimates(Boolean sohvac_freeestimates){
    this.setField(SOHVAC_FREEESTIMATES,sohvac_freeestimates);
  }
  public Boolean getSohvac_freeestimates(){
    Object[] val = this.getField(SOHVAC_FREEESTIMATES);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_inspectionreports(Boolean sohvac_inspectionreports){
    this.setField(SOHVAC_INSPECTIONREPORTS,sohvac_inspectionreports);
  }
  public Boolean getSohvac_inspectionreports(){
    Object[] val = this.getField(SOHVAC_INSPECTIONREPORTS);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_complaintsinlastfiveyears(String sohvac_complaintsinlastfiveyears){
    this.setField(SOHVAC_COMPLAINTSINLASTFIVEYEARS,sohvac_complaintsinlastfiveyears);
  }
  public String getSohvac_complaintsinlastfiveyears(){
    Object[] val = this.getField(SOHVAC_COMPLAINTSINLASTFIVEYEARS);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_furnishallmaterials(Boolean sohvac_furnishallmaterials){
    this.setField(SOHVAC_FURNISHALLMATERIALS,sohvac_furnishallmaterials);
  }
  public Boolean getSohvac_furnishallmaterials(){
    Object[] val = this.getField(SOHVAC_FURNISHALLMATERIALS);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_workswithbrokers(Boolean sohvac_workswithbrokers){
    this.setField(SOHVAC_WORKSWITHBROKERS,sohvac_workswithbrokers);
  }
  public Boolean getSohvac_workswithbrokers(){
    Object[] val = this.getField(SOHVAC_WORKSWITHBROKERS);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_rentalsleasing(Boolean sohvac_rentalsleasing){
    this.setField(SOHVAC_RENTALSLEASING,sohvac_rentalsleasing);
  }
  public Boolean getSohvac_rentalsleasing(){
    Object[] val = this.getField(SOHVAC_RENTALSLEASING);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_outbidcompetitors(Boolean sohvac_outbidcompetitors){
    this.setField(SOHVAC_OUTBIDCOMPETITORS,sohvac_outbidcompetitors);
  }
  public Boolean getSohvac_outbidcompetitors(){
    Object[] val = this.getField(SOHVAC_OUTBIDCOMPETITORS);
    if(val != null && val[0] != null){
      return (Boolean)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_servicewarranty(String sohvac_servicewarranty){
    this.setField(SOHVAC_SERVICEWARRANTY,sohvac_servicewarranty);
  }
  public String getSohvac_servicewarranty(){
    Object[] val = this.getField(SOHVAC_SERVICEWARRANTY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_newinstallwarranty(String sohvac_newinstallwarranty){
    this.setField(SOHVAC_NEWINSTALLWARRANTY,sohvac_newinstallwarranty);
  }
  public String getSohvac_newinstallwarranty(){
    Object[] val = this.getField(SOHVAC_NEWINSTALLWARRANTY);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_additionalwarranties(String sohvac_additionalwarranties){
    this.setField(SOHVAC_ADDITIONALWARRANTIES,sohvac_additionalwarranties);
  }
  public String getSohvac_additionalwarranties(){
    Object[] val = this.getField(SOHVAC_ADDITIONALWARRANTIES);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public void setSohvac_warrantystatement(String sohvac_warrantystatement){
    this.setField(SOHVAC_WARRANTYSTATEMENT,sohvac_warrantystatement);
  }
  public String getSohvac_warrantystatement(){
    Object[] val = this.getField(SOHVAC_WARRANTYSTATEMENT);
    if(val != null && val[0] != null){
      return (String)val[0];
    }else{
      return null;
    }
  }
  public String getIdentityName() {
    return "SOHVAC_LISTINGID";
  }
}