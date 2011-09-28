/*
* Created on Jan 11, 2011
*
*/
package com.sohvac.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Dbo_sohvac_service_jdoManager {
  private Connection m_con;
  private final static String SELECT = "select ~ from #TABLE# where Sohvac_ServiceID = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(#TABLE#.SOHVAC_SERVICEID) from #TABLE# where #TABLE#.SOHVAC_SERVICEID = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.SOHVAC_SERVICEID = ?";
  private final static String IDENTITY = "SOHVAC_SERVICEID";
  private final static String NEXT_SQL = "Select max(Sohvac_ServiceID) +1 from #TABLE#";
  public final static String FIELDS = "#TABLE#.SOHVAC_SERVICEID,#TABLE#.SOHVAC_NAME,#TABLE#.SOHVAC_DESCRIPTION,#TABLE#.SOHVAC_ACTIVE,#TABLE#.SOHVAC_MAIN";
  private String TABLE = "DBO.SOHVAC_SERVICE";
  public Dbo_sohvac_service_jdoManager(Connection con){
    this.m_con = con;
  }
  protected Connection getConnection(){
    return this.m_con;
  }
  public String getTableName(){
    return TABLE;
  }
  public void setTableName(String tablename){
    TABLE = tablename;
  }
  public static Dbo_sohvac_service_jdo bindDbo_sohvac_service(ResultSet rs) throws SQLException{
Dbo_sohvac_service_jdo retval = null;
    retval = Dbo_sohvac_service_jdoManager.createObject(rs);
    return retval;
  }

  public Dbo_sohvac_service_jdo getDbo_sohvac_service(Integer sohvac_serviceid){
    PreparedStatement ps = null;
    ResultSet rs = null;
    Dbo_sohvac_service_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,sohvac_serviceid);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = Dbo_sohvac_service_jdoManager.createObject(rs);
      }
    }catch(Exception e){

    }finally{
      try{rs.close();}catch(Exception e){}
      try{ps.close();}catch(Exception e){}
    }
    return retval;
  }
/**
* @param rs
* @return
* @throws SQLException
*/
  private static Dbo_sohvac_service_jdo createObject(ResultSet rs) throws SQLException {
    Dbo_sohvac_service_jdo retval = null;
    retval = new Dbo_sohvac_service_jdo();
    /*
     *
     * Insert values from Result Set into object
     */
    ResultSetMetaData mdata = rs.getMetaData();
    for(int i = 0;i< mdata.getColumnCount();i++){
      String fieldname = mdata.getColumnName(i+1).toUpperCase();
      Object[] val = new Object[2];
      val[1] = new Boolean(false);
      // Set the value
      		val[0] = rs.getObject(i+1);
		if(val[0] instanceof java.sql.Date){
			val[0] = new java.util.Date(rs.getTimestamp(i+1).getTime());
		}
      retval.bindField(fieldname,val);
    }

    return retval;
  }
  public void save(Dbo_sohvac_service_jdo record) throws Exception {
    Connection con = this.getConnection();
    con.setAutoCommit(false);
    if(record.getDirtyFieldCount() == 0){
      return;
    }
    try{
      String search = this.getSQL(SEARCH);
      int count = 0;
      if(record.getField(record.getIdentityName()) != null){
        PreparedStatement ps = con.prepareStatement(search);
        ps.setObject(1,record.getField(record.getIdentityName())[0]);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
          count = rs.getInt(1);
        }
        rs.close();
        ps.close();
      }
      if(count > 0){
      // Update
        System.out.println("Updating...");
        this.handleBeforeUpdate(record);
        String update_sql = this.getSQL(UPDATE);
        int dirtyfieldcount = 0;
        for(int i = 0; i < record.getDirtyFieldCount();i++){
          String fieldname = record.getDirtyField(i);
          if(i > 0){
            update_sql += ", ";
          }
          update_sql += fieldname +" = ?";
          dirtyfieldcount ++;
        }
        update_sql += " where "+record.getIdentityName()+" = ?";
        PreparedStatement update_ps = con.prepareStatement(update_sql);

        for(int i = 0; i < record.getDirtyFieldCount();i++){
          Object value = record.getField(record.getDirtyField(i))[0];
          if(value instanceof java.sql.Timestamp){
            update_ps.setObject(i+1,value);
          }else if(value instanceof java.sql.Date){
            update_ps.setObject(i+1,new java.sql.Timestamp(((java.sql.Date)value).getTime()));
          }else if(value instanceof java.util.Date){
            value = new java.sql.Timestamp(((java.util.Date)value).getTime());
            update_ps.setObject(i+1,value);
          }else{
            update_ps.setObject(i+1,value);
          }
        }
        update_ps.setObject(dirtyfieldcount+1,record.getField(record.getIdentityName())[0]);
        int updated = update_ps.executeUpdate();
        con.commit();
        if(updated == 0){
          throw new Exception("No rows updated.");
        }
        System.out.println(updated +" rows updated.");
        this.handleAfterUpdate(record);
        /**
         * Mark all fields as clean
         */
        this.setAllClean(record);
        update_ps.close();
      }else{
        // Insert
        System.out.println("Inserting...");
        String insert_sql = this.getSQL(INSERT);
        String insert_pre,insert_post;
        insert_pre = "("; insert_post = "(";
        for(int i = 0;i < record.getFieldCount();i++){
          String fieldname = record.getField(i);
          if(i > 0){
            insert_pre += ",";
            insert_post += ",";
          }
          insert_pre += fieldname;
          if(fieldname.equals(record.getIdentityName())){
            PreparedStatement count_ps = con.prepareStatement(this.getSQL(NEXT_SQL));
            ResultSet count_rs = count_ps.executeQuery();
            if(count_rs.next()){
              if(count_rs.getBigDecimal(1) == null){
                insert_post += "0";
              }else{
                insert_post += "("+this.getSQL(NEXT_SQL)+")";
              }
            }else{
              insert_post += "0";
            }
            count_rs.close();
            count_ps.close();
          }else{
            insert_post += "?";
          }
        }
        insert_pre += ") values ";
        insert_post += ")";
        insert_sql += insert_pre + insert_post;
        PreparedStatement insert_ps = con.prepareStatement(insert_sql);
        int field_index = 1;
        for(int i = 0; i < record.getFieldCount();i++){
          String fieldname = record.getField(i);
          if(fieldname.equals(record.getIdentityName())){
            continue;
          }
          Object[] val = record.getField(fieldname);
          if(val[0] instanceof java.sql.Timestamp){
            insert_ps.setObject(field_index,val[0]);
          }else if(val[0] instanceof java.sql.Date){
            insert_ps.setObject(field_index,new java.sql.Timestamp(((java.sql.Date)val[0]).getTime()));
          }else
            if(val[0] instanceof java.util.Date){
              val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime());
              insert_ps.setObject(field_index,val[0]);
          }else{
            insert_ps.setObject(field_index,val[0]);
          }
          field_index ++;
        }
        int updated = insert_ps.executeUpdate();
        String max_sql = "select max("+record.getIdentityName()+") from dbo.sohvac_service";
        PreparedStatement max_ps = con.prepareStatement(max_sql);
        ResultSet max_rs = max_ps.executeQuery();
        if(max_rs.next()){
          record.setSohvac_serviceid(max_rs.getInt(1));
        }
        max_rs.close();
        max_ps.close();
        con.commit();
        if(updated == 0){
          throw new Exception("No rows added.");
        }else{
          this.handleAfterInsert(record);
          this.setAllClean(record);
        }
        System.out.println(updated+" rows added.");
        insert_ps.close();
      }
  }finally{

  }
}
	protected void handleAfterInsert(Dbo_sohvac_service_jdo record) {}
	protected void handleAfterUpdate(Dbo_sohvac_service_jdo record) {}
	protected void handleBeforeUpdate(Dbo_sohvac_service_jdo record) {}
  private void setAllClean(Dbo_sohvac_service_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(Dbo_sohvac_service_jdo record) throws Exception {
    Connection con = this.getConnection();
    String sql_delete = this.getSQL(DELETE);
    PreparedStatement ps = con.prepareStatement(sql_delete);
    ps.setObject(1, record.getField(IDENTITY)[0]);
    int updated = ps.executeUpdate();
    System.out.println(updated +" records deleted.");
  }
  public PreparedStatement prepareStatement(String query) throws SQLException{
    String sql = this.getSQL(QUERY.split("[~]")[0]+FIELDS+QUERY.split("[~]")[1] + query);
    PreparedStatement ps = this.getConnection().prepareStatement(sql);
    return ps;
  }
public Dbo_sohvac_service_jdo newDbo_sohvac_service() {
Dbo_sohvac_service_jdo retval = new Dbo_sohvac_service_jdo();
 retval.setSohvac_serviceid(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    return retval;
  }
}