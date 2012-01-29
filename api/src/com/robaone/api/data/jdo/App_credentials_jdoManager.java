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

public class App_credentials_jdoManager {
  private Connection m_con;
  private String m_options = "";
  private final static String SELECT = "select ~ from #TABLE# #OPTION#  where idapp_credentials = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# #OPTION#  where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(1) from #TABLE# #OPTION# where #TABLE#.IDAPP_CREDENTIALS = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.IDAPP_CREDENTIALS = ?";
  private final static String IDENTITY = "IDAPP_CREDENTIALS";
  private RO_JDO_IdentityManager<Long> NEXT_SQL;
  public final static String FIELDS = "#TABLE#.IDAPP_CREDENTIALS,#TABLE#.REQUEST_TOKEN,#TABLE#.ACCESS_TOKEN,#TABLE#.CREATED_BY,#TABLE#.CREATION_HOST,#TABLE#.EXPIRATION_DATE,#TABLE#.CREATION_DATE,#TABLE#.ACTIVE,#TABLE#.IDAPPS,#TABLE#.IDUSER,#TABLE#.TOKEN_SECRET";
  private String TABLE = "APP_CREDENTIALS";
  protected boolean debug = false;
  public App_credentials_jdoManager(Connection con){
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
  public App_credentials_jdo bindApp_credentials(ResultSet rs) throws SQLException{
App_credentials_jdo retval = null;
    retval = App_credentials_jdoManager.createObject(rs);
    return retval;
  }

  public App_credentials_jdo getApp_credentials(Integer idapp_credentials){
    PreparedStatement ps = null;
    ResultSet rs = null;
    App_credentials_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,idapp_credentials);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = App_credentials_jdoManager.createObject(rs);
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
  private static App_credentials_jdo createObject(ResultSet rs) throws SQLException {
    App_credentials_jdo retval = null;
    retval = new App_credentials_jdo();
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
  public void save(App_credentials_jdo record) throws Exception {
    this.save(record,false);
  }
  public void save(App_credentials_jdo record,boolean dirty) throws Exception {
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
        record.setIdapp_credentials(new Integer(NEXT_SQL.getIdentity(this.getTableName(),this.m_con).toString()));
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
	protected void handleAfterInsert(App_credentials_jdo record) {}
	protected void handleAfterUpdate(App_credentials_jdo record) {}
	protected void handleBeforeUpdate(App_credentials_jdo record) {}
  private void setAllClean(App_credentials_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(App_credentials_jdo record) throws Exception {
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
public App_credentials_jdo newApp_credentials() {
App_credentials_jdo retval = new App_credentials_jdo();
 retval.setIdapp_credentials(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    retval = retval.replaceAll("#OPTION#",this.getTableOptions());
    return retval;
  }
  public JSONObject toJSONObject(App_credentials_jdo record) throws Exception {
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
  public void bindApp_credentialsJSON(App_credentials_jdo record,String jsondata) throws Exception {
    JSONObject jo = new JSONObject(jsondata);
    bindApp_credentialsJSON(record,jo);
  }
  public void bindApp_credentialsJSON(App_credentials_jdo record, JSONObject jo) throws Exception {
    Iterator keys = jo.keys();
    HashMap keymap = new HashMap();
    while(keys.hasNext()){
      String key = keys.next().toString();
      String lc_key = key.toUpperCase();
      keymap.put(lc_key, key);
    }
    if(record != null && jo != null){
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.IDAPP_CREDENTIALS))){
          if(keymap.get(App_credentials_jdo.IDAPP_CREDENTIALS) != null)
            record.setIdapp_credentials(null);
        }else{
             record.setIdapp_credentials(new Integer(jo.getInt((String)keymap.get(App_credentials_jdo.IDAPP_CREDENTIALS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.REQUEST_TOKEN))){
          if(keymap.get(App_credentials_jdo.REQUEST_TOKEN) != null)
            record.setRequest_token(null);
        }else{
             record.setRequest_token(new String(jo.getString((String)keymap.get(App_credentials_jdo.REQUEST_TOKEN))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.ACCESS_TOKEN))){
          if(keymap.get(App_credentials_jdo.ACCESS_TOKEN) != null)
            record.setAccess_token(null);
        }else{
             record.setAccess_token(new String(jo.getString((String)keymap.get(App_credentials_jdo.ACCESS_TOKEN))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.CREATED_BY))){
          if(keymap.get(App_credentials_jdo.CREATED_BY) != null)
            record.setCreated_by(null);
        }else{
             record.setCreated_by(new String(jo.getString((String)keymap.get(App_credentials_jdo.CREATED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.CREATION_HOST))){
          if(keymap.get(App_credentials_jdo.CREATION_HOST) != null)
            record.setCreation_host(null);
        }else{
             record.setCreation_host(new String(jo.getString((String)keymap.get(App_credentials_jdo.CREATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.EXPIRATION_DATE))){
          if(keymap.get(App_credentials_jdo.EXPIRATION_DATE) != null)
            record.setExpiration_date(null);
        }else{
          try{
             record.setExpiration_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(App_credentials_jdo.EXPIRATION_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(App_credentials_jdo.EXPIRATION_DATE))).getTime();
            record.setExpiration_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.CREATION_DATE))){
          if(keymap.get(App_credentials_jdo.CREATION_DATE) != null)
            record.setCreation_date(null);
        }else{
          try{
             record.setCreation_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(App_credentials_jdo.CREATION_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(App_credentials_jdo.CREATION_DATE))).getTime();
            record.setCreation_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.ACTIVE))){
          if(keymap.get(App_credentials_jdo.ACTIVE) != null)
            record.setActive(null);
        }else{
             record.setActive(new Integer(jo.getInt((String)keymap.get(App_credentials_jdo.ACTIVE))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.IDAPPS))){
          if(keymap.get(App_credentials_jdo.IDAPPS) != null)
            record.setIdapps(null);
        }else{
             record.setIdapps(new Integer(jo.getInt((String)keymap.get(App_credentials_jdo.IDAPPS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.IDUSER))){
          if(keymap.get(App_credentials_jdo.IDUSER) != null)
            record.setIduser(null);
        }else{
             record.setIduser(new Integer(jo.getInt((String)keymap.get(App_credentials_jdo.IDUSER))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(App_credentials_jdo.TOKEN_SECRET))){
          if(keymap.get(App_credentials_jdo.TOKEN_SECRET) != null)
            record.setToken_secret(null);
        }else{
             record.setToken_secret(new String(jo.getString((String)keymap.get(App_credentials_jdo.TOKEN_SECRET))));
        }
      }catch(org.json.JSONException e){
      }
    }
  }
	public App_credentials_jdo bindApp_credentialsJSON(String jsondata) throws Exception {
		App_credentials_jdo retval = null;
		JSONObject jo = new JSONObject(jsondata);
		retval = this.bindApp_credentialsJSON(jo);
		return retval;
	}
	private App_credentials_jdo bindApp_credentialsJSON(JSONObject jo) throws Exception{
		Iterator keys = jo.keys();
		HashMap keymap = new HashMap();
		while(keys.hasNext()){
			String key = keys.next().toString();
			String lc_key = key.toUpperCase();
			keymap.put(lc_key, key);
		}
		App_credentials_jdo record = null;
		try{
			if(!jo.isNull((String)keymap.get(IDENTITY))){
			record = this.getApp_credentials(new Integer(jo.getInt((String)keymap.get(IDENTITY))));
			}
		}catch(JSONException e){}
		if(record == null){
			record = this.newApp_credentials();
		}
		bindApp_credentialsJSON(record, jo);
		return record;
	}
}
