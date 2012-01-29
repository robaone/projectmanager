/*
* Created on Jan 15, 2012
*
* Author: Ansel Robateau
*         http://www.robaone.com
*/
package com.robaone.api.data.jdo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.robaone.jdo.*;
import org.json.*;
import java.util.HashMap;
import java.util.Iterator;

public class Roles_jdoManager {
  private Connection m_con;
  private String m_options = "";
  private final static String SELECT = "select ~ from #TABLE# #OPTION#  where idroles = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# #OPTION#  where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(1) from #TABLE# #OPTION# where #TABLE#.IDROLES = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.IDROLES = ?";
  private final static String IDENTITY = "IDROLES";
  private RO_JDO_IdentityManager<Long> NEXT_SQL;
  public final static String FIELDS = "#TABLE#.IDROLES,#TABLE#.IDUSER,#TABLE#.ROLE";
  private String TABLE = "ROLES";
  protected boolean debug = false;
  public Roles_jdoManager(Connection con){
    this.m_con = con;
    this.setIdentityClass();
    try{
    	if(System.getProperty("debug").equals("Y")){
    		debug = true;
    	}
    }catch(Exception e){}
  }
  protected void setIdentityClass(){
     this.NEXT_SQL = new RO_JDO_MySQL<Long>();
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
  public Roles_jdo bindRoles(ResultSet rs) throws SQLException{
Roles_jdo retval = null;
    retval = Roles_jdoManager.createObject(rs);
    return retval;
  }

  public Roles_jdo getRoles(Integer idroles){
    PreparedStatement ps = null;
    ResultSet rs = null;
    Roles_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,idroles);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = Roles_jdoManager.createObject(rs);
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
  private static Roles_jdo createObject(ResultSet rs) throws SQLException {
    Roles_jdo retval = null;
    retval = new Roles_jdo();
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
  private void setTableOptions(String str){
    this.m_options = str;
  }
  private String getTableOptions(){
    return this.m_options;
  }
  public void save(Roles_jdo record) throws Exception {
    this.save(record,false);
  }
  public void save(Roles_jdo record,boolean dirty) throws Exception {
    Connection con = this.getConnection();
    if(dirty){
      this.setTableOptions("");
    }
    boolean finished = false;
    if(record.getDirtyFieldCount() == 0){
      return;
    }
    try{
      String search = this.getSQL(SEARCH);
      int count = 0;
      if(record.getField(record.getIdentityName()) != null  && record.getField(record.getIdentityName())[0] != null){
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
        if(debug) System.out.println("Updating...");
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
        finished = true;
        if(updated == 0){
          throw new Exception("No rows updated.");
        }
        if(debug) System.out.println(updated +" rows updated.");
        this.handleAfterUpdate(record);
        /**
         * Mark all fields as clean
         */
        this.setAllClean(record);
        update_ps.close();
      }else{
        // Insert
        if(debug) System.out.println("Inserting...");
        String insert_sql = this.getSQL(INSERT);
        String insert_pre,insert_post;
        insert_pre = "("; insert_post = "(";
        for(int i = 0;i < record.getFieldCount();i++){
          String fieldname = record.getField(i);
          if(fieldname.equalsIgnoreCase(record.getIdentityName())){
            continue;
          }
          if(i > 0 && insert_pre.length() > 1 && insert_post.length() > 1){
            insert_pre += ",";
            insert_post += ",";
          }
          insert_pre += fieldname;
          insert_post += "?";
        }
        insert_pre += ") values ";
        insert_post += ")";
        insert_sql += insert_pre + insert_post;
        PreparedStatement insert_ps = con.prepareStatement(insert_sql);
        int field_index = 1;
        for(int i = 0; i < record.getFieldCount();i++){
          String fieldname = record.getField(i);
          if(fieldname.equalsIgnoreCase(record.getIdentityName())){
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
        record.setIdroles(new Integer(NEXT_SQL.getIdentity(this.getTableName(),this.m_con).toString()));
        finished = true;
        if(updated == 0){
          throw new Exception("No rows added.");
        }else{
          this.handleAfterInsert(record);
          this.setAllClean(record);
        }
        if(debug) System.out.println(updated+" rows added.");
        insert_ps.close();
      }
    }finally{}
  }
	protected void handleAfterInsert(Roles_jdo record) {}
	protected void handleAfterUpdate(Roles_jdo record) {}
	protected void handleBeforeUpdate(Roles_jdo record) {}
  private void setAllClean(Roles_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(Roles_jdo record) throws Exception {
    Connection con = this.getConnection();
    String sql_delete = this.getSQL(DELETE);
    PreparedStatement ps = con.prepareStatement(sql_delete);
    ps.setObject(1, record.getField(IDENTITY)[0]);
    int updated = ps.executeUpdate();
    if(debug) System.out.println(updated +" records deleted.");
  }
  public PreparedStatement prepareStatement(String query) throws SQLException{
    String sql = this.getSQL(QUERY.split("[~]")[0]+FIELDS+QUERY.split("[~]")[1] + query);
    if(debug) System.out.println(sql);
    PreparedStatement ps = this.getConnection().prepareStatement(sql);
    return ps;
  }
public Roles_jdo newRoles() {
Roles_jdo retval = new Roles_jdo();
 retval.setIdroles(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    retval = retval.replaceAll("#OPTION#",this.getTableOptions());
    return retval;
  }
  public JSONObject toJSONObject(Roles_jdo record) throws Exception {
    JSONObject retval = null;
    if(record != null){
      JSONObject object = new JSONObject();
      for(int i = 0; i < record.getFieldCount(); i++){
        String fieldname = record.getField(i);
        Object value = record.getField(fieldname)[0];
        object.put(fieldname.toLowerCase(), value);
      }
      retval = object;
    }
    return retval;
  }
  public void bindRolesJSON(Roles_jdo record,String jsondata) throws Exception {
    JSONObject jo = new JSONObject(jsondata);
    bindRolesJSON(record,jo);
  }
  public void bindRolesJSON(Roles_jdo record, JSONObject jo) throws Exception {
    Iterator keys = jo.keys();
    HashMap keymap = new HashMap();
    while(keys.hasNext()){
      String key = keys.next().toString();
      String lc_key = key.toUpperCase();
      keymap.put(lc_key, key);
    }
    if(record != null && jo != null){
      try{
        if(jo.isNull((String)keymap.get(Roles_jdo.IDROLES))){
          if(keymap.get(Roles_jdo.IDROLES) != null)
            record.setIdroles(null);
        }else{
             record.setIdroles(new Integer(jo.getInt((String)keymap.get(Roles_jdo.IDROLES))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Roles_jdo.IDUSER))){
          if(keymap.get(Roles_jdo.IDUSER) != null)
            record.setIduser(null);
        }else{
             record.setIduser(new Integer(jo.getInt((String)keymap.get(Roles_jdo.IDUSER))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Roles_jdo.ROLE))){
          if(keymap.get(Roles_jdo.ROLE) != null)
            record.setRole(null);
        }else{
             record.setRole(new Integer(jo.getInt((String)keymap.get(Roles_jdo.ROLE))));
        }
      }catch(org.json.JSONException e){
      }
    }
  }
	public Roles_jdo bindRolesJSON(String jsondata) throws Exception {
		Roles_jdo retval = null;
		JSONObject jo = new JSONObject(jsondata);
		retval = this.bindRolesJSON(jo);
		return retval;
	}
	private Roles_jdo bindRolesJSON(JSONObject jo) throws Exception{
		Iterator keys = jo.keys();
		HashMap keymap = new HashMap();
		while(keys.hasNext()){
			String key = keys.next().toString();
			String lc_key = key.toUpperCase();
			keymap.put(lc_key, key);
		}
		Roles_jdo record = null;
		try{
			if(!jo.isNull((String)keymap.get(IDENTITY))){
			record = this.getRoles(new Integer(jo.getInt((String)keymap.get(IDENTITY))));
			}
		}catch(JSONException e){}
		if(record == null){
			record = this.newRoles();
		}
		bindRolesJSON(record, jo);
		return record;
	}
}
