/*
* Created on Sep 25, 2011
*
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

public class Apps_jdoManager {
  private Connection m_con;
  private final static String SELECT = "select ~ from #TABLE# where idapps = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(#TABLE#.IDAPPS) from #TABLE# where #TABLE#.IDAPPS = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.IDAPPS = ?";
  private final static String IDENTITY = "IDAPPS";
  private final static RO_JDO_IdentityManager NEXT_SQL = new RO_JDO_MySQL();
  public final static String FIELDS = "#TABLE#.IDAPPS,#TABLE#.NAME,#TABLE#.CALLBACK_URL,#TABLE#.DESCRIPTION,#TABLE#.CONSUMER_KEY,#TABLE#.CONSUMER_SECRET,#TABLE#.ACTIVE,#TABLE#.IDUSER,#TABLE#.MODIFIED_BY,#TABLE#.CREATED_BY,#TABLE#.CREATED_DATE,#TABLE#.MODIFIED_DATE,#TABLE#.CREATION_HOST,#TABLE#.MODIFICATION_HOST,#TABLE#.META_DATA";
  private String TABLE = "APPS";
  protected boolean debug = false;
  public Apps_jdoManager(Connection con){
    this.m_con = con;
    try{
    	if(System.getProperty("debug").equals("Y")){
    		debug = true;
    	}
    }catch(Exception e){}
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
  public static Apps_jdo bindApps(ResultSet rs) throws SQLException{
Apps_jdo retval = null;
    retval = Apps_jdoManager.createObject(rs);
    return retval;
  }

  public Apps_jdo getApps(Integer idapps){
    PreparedStatement ps = null;
    ResultSet rs = null;
    Apps_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,idapps);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = Apps_jdoManager.createObject(rs);
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
  private static Apps_jdo createObject(ResultSet rs) throws SQLException {
    Apps_jdo retval = null;
    retval = new Apps_jdo();
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
  public void save(Apps_jdo record) throws Exception {
    Connection con = this.getConnection();
    con.setAutoCommit(false);
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
        con.commit();
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
        record.setIdapps(NEXT_SQL.getIdentity(this.m_con));
        con.commit();
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
  }finally{
			 if(finished){
				  con.commit();
			  }else{
				  con.rollback();
			  }
			  con.setAutoCommit(true);
  }
}
	protected void handleAfterInsert(Apps_jdo record) {}
	protected void handleAfterUpdate(Apps_jdo record) {}
	protected void handleBeforeUpdate(Apps_jdo record) {}
  private void setAllClean(Apps_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(Apps_jdo record) throws Exception {
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
public Apps_jdo newApps() {
Apps_jdo retval = new Apps_jdo();
 retval.setIdapps(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    return retval;
  }
  public static JSONObject toJSONObject(Apps_jdo record) throws Exception {
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
  public static void bindAppsJSON(Apps_jdo record,String jsondata) throws Exception {
    JSONObject jo = new JSONObject(jsondata);
    Apps_jdoManager.bindAppsJSON(record,jo);
  }
  public static void bindAppsJSON(Apps_jdo record, JSONObject jo) throws Exception {
    Iterator keys = jo.keys();
    HashMap keymap = new HashMap();
    while(keys.hasNext()){
      String key = keys.next().toString();
      String lc_key = key.toUpperCase();
      keymap.put(lc_key, key);
    }
    if(record != null && jo != null){
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.IDAPPS))){
          if(keymap.get(Apps_jdo.IDAPPS) != null)
            record.setIdapps(null);
        }else{
             record.setIdapps(new Integer(jo.getInt((String)keymap.get(Apps_jdo.IDAPPS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.NAME))){
          if(keymap.get(Apps_jdo.NAME) != null)
            record.setName(null);
        }else{
             record.setName(new String(jo.getString((String)keymap.get(Apps_jdo.NAME))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CALLBACK_URL))){
          if(keymap.get(Apps_jdo.CALLBACK_URL) != null)
            record.setCallback_url(null);
        }else{
             record.setCallback_url(new String(jo.getString((String)keymap.get(Apps_jdo.CALLBACK_URL))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.DESCRIPTION))){
          if(keymap.get(Apps_jdo.DESCRIPTION) != null)
            record.setDescription(null);
        }else{
             record.setDescription(new String(jo.getString((String)keymap.get(Apps_jdo.DESCRIPTION))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CONSUMER_KEY))){
          if(keymap.get(Apps_jdo.CONSUMER_KEY) != null)
            record.setConsumer_key(null);
        }else{
             record.setConsumer_key(new String(jo.getString((String)keymap.get(Apps_jdo.CONSUMER_KEY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CONSUMER_SECRET))){
          if(keymap.get(Apps_jdo.CONSUMER_SECRET) != null)
            record.setConsumer_secret(null);
        }else{
             record.setConsumer_secret(new String(jo.getString((String)keymap.get(Apps_jdo.CONSUMER_SECRET))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.ACTIVE))){
          if(keymap.get(Apps_jdo.ACTIVE) != null)
            record.setActive(null);
        }else{
             record.setActive(new Integer(jo.getInt((String)keymap.get(Apps_jdo.ACTIVE))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.IDUSER))){
          if(keymap.get(Apps_jdo.IDUSER) != null)
            record.setIduser(null);
        }else{
             record.setIduser(new Integer(jo.getInt((String)keymap.get(Apps_jdo.IDUSER))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.MODIFIED_BY))){
          if(keymap.get(Apps_jdo.MODIFIED_BY) != null)
            record.setModified_by(null);
        }else{
             record.setModified_by(new String(jo.getString((String)keymap.get(Apps_jdo.MODIFIED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CREATED_BY))){
          if(keymap.get(Apps_jdo.CREATED_BY) != null)
            record.setCreated_by(null);
        }else{
             record.setCreated_by(new String(jo.getString((String)keymap.get(Apps_jdo.CREATED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CREATED_DATE))){
          if(keymap.get(Apps_jdo.CREATED_DATE) != null)
            record.setCreated_date(null);
        }else{
          try{
             record.setCreated_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Apps_jdo.CREATED_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Apps_jdo.CREATED_DATE))).getTime();
            record.setCreated_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.MODIFIED_DATE))){
          if(keymap.get(Apps_jdo.MODIFIED_DATE) != null)
            record.setModified_date(null);
        }else{
          try{
             record.setModified_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Apps_jdo.MODIFIED_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Apps_jdo.MODIFIED_DATE))).getTime();
            record.setModified_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.CREATION_HOST))){
          if(keymap.get(Apps_jdo.CREATION_HOST) != null)
            record.setCreation_host(null);
        }else{
             record.setCreation_host(new String(jo.getString((String)keymap.get(Apps_jdo.CREATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.MODIFICATION_HOST))){
          if(keymap.get(Apps_jdo.MODIFICATION_HOST) != null)
            record.setModification_host(null);
        }else{
             record.setModification_host(new String(jo.getString((String)keymap.get(Apps_jdo.MODIFICATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Apps_jdo.META_DATA))){
          if(keymap.get(Apps_jdo.META_DATA) != null)
            record.setMeta_data(null);
        }else{
             record.setMeta_data(new String(jo.getString((String)keymap.get(Apps_jdo.META_DATA))));
        }
      }catch(org.json.JSONException e){
      }
    }
  }
	public Apps_jdo bindAppsJSON(String jsondata) throws Exception {
		Apps_jdo retval = null;
		JSONObject jo = new JSONObject(jsondata);
		retval = this.bindAppsJSON(jo);
		return retval;
	}
	private Apps_jdo bindAppsJSON(JSONObject jo) throws Exception{
		Iterator keys = jo.keys();
		HashMap keymap = new HashMap();
		while(keys.hasNext()){
			String key = keys.next().toString();
			String lc_key = key.toUpperCase();
			keymap.put(lc_key, key);
		}
		Apps_jdo record = null;
		try{
			if(!jo.isNull((String)keymap.get(IDENTITY))){
			record = this.getApps(new Integer(jo.getInt((String)keymap.get(IDENTITY))));
			}
		}catch(JSONException e){}
		if(record == null){
			record = this.newApps();
		}
		Apps_jdoManager.bindAppsJSON(record, jo);
		return record;
	}}
