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

public class Bids_jdoManager {
  private Connection m_con;
  private String m_options = "";
  private final static String SELECT = "select ~ from #TABLE# #OPTION#  where idbids = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# #OPTION#  where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(1) from #TABLE# #OPTION# where #TABLE#.IDBIDS = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.IDBIDS = ?";
  private final static String IDENTITY = "IDBIDS";
  private RO_JDO_IdentityManager<Long> NEXT_SQL;
  public final static String FIELDS = "#TABLE#.IDBIDS,#TABLE#.CONTRACTORID,#TABLE#.PROJECTID,#TABLE#.TOTAL,#TABLE#.DETAILS,#TABLE#.CREATED_BY,#TABLE#.CREATION_DATE,#TABLE#.CREATION_HOST,#TABLE#.MODIFIED_BY,#TABLE#.MODIFIED_DATE,#TABLE#.MODIFIER_HOST,#TABLE#.STATUS";
  private String TABLE = "BIDS";
  protected boolean debug = false;
  public Bids_jdoManager(Connection con){
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
  public Bids_jdo bindBids(ResultSet rs) throws SQLException{
Bids_jdo retval = null;
    retval = Bids_jdoManager.createObject(rs);
    return retval;
  }

  public Bids_jdo getBids(Integer idbids){
    PreparedStatement ps = null;
    ResultSet rs = null;
    Bids_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,idbids);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = Bids_jdoManager.createObject(rs);
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
  private static Bids_jdo createObject(ResultSet rs) throws SQLException {
    Bids_jdo retval = null;
    retval = new Bids_jdo();
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
  public void save(Bids_jdo record) throws Exception {
    this.save(record,false);
  }
  public void save(Bids_jdo record,boolean dirty) throws Exception {
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
        record.setIdbids(new Integer(NEXT_SQL.getIdentity(this.getTableName(),this.m_con).toString()));
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
	protected void handleAfterInsert(Bids_jdo record) {}
	protected void handleAfterUpdate(Bids_jdo record) {}
	protected void handleBeforeUpdate(Bids_jdo record) {}
  private void setAllClean(Bids_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(Bids_jdo record) throws Exception {
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
public Bids_jdo newBids() {
Bids_jdo retval = new Bids_jdo();
 retval.setIdbids(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    retval = retval.replaceAll("#OPTION#",this.getTableOptions());
    return retval;
  }
  public JSONObject toJSONObject(Bids_jdo record) throws Exception {
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
  public void bindBidsJSON(Bids_jdo record,String jsondata) throws Exception {
    JSONObject jo = new JSONObject(jsondata);
    bindBidsJSON(record,jo);
  }
  public void bindBidsJSON(Bids_jdo record, JSONObject jo) throws Exception {
    Iterator keys = jo.keys();
    HashMap keymap = new HashMap();
    while(keys.hasNext()){
      String key = keys.next().toString();
      String lc_key = key.toUpperCase();
      keymap.put(lc_key, key);
    }
    if(record != null && jo != null){
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.IDBIDS))){
          if(keymap.get(Bids_jdo.IDBIDS) != null)
            record.setIdbids(null);
        }else{
             record.setIdbids(new Integer(jo.getInt((String)keymap.get(Bids_jdo.IDBIDS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.CONTRACTORID))){
          if(keymap.get(Bids_jdo.CONTRACTORID) != null)
            record.setContractorid(null);
        }else{
             record.setContractorid(new Integer(jo.getInt((String)keymap.get(Bids_jdo.CONTRACTORID))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.PROJECTID))){
          if(keymap.get(Bids_jdo.PROJECTID) != null)
            record.setProjectid(null);
        }else{
             record.setProjectid(new Integer(jo.getInt((String)keymap.get(Bids_jdo.PROJECTID))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.TOTAL))){
          if(keymap.get(Bids_jdo.TOTAL) != null)
            record.setTotal(null);
        }else{
             record.setTotal(new BigDecimal(jo.getDouble((String)keymap.get(Bids_jdo.TOTAL))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.DETAILS))){
          if(keymap.get(Bids_jdo.DETAILS) != null)
            record.setDetails(null);
        }else{
             record.setDetails(new String(jo.getString((String)keymap.get(Bids_jdo.DETAILS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.CREATED_BY))){
          if(keymap.get(Bids_jdo.CREATED_BY) != null)
            record.setCreated_by(null);
        }else{
             record.setCreated_by(new Integer(jo.getInt((String)keymap.get(Bids_jdo.CREATED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.CREATION_DATE))){
          if(keymap.get(Bids_jdo.CREATION_DATE) != null)
            record.setCreation_date(null);
        }else{
          try{
             record.setCreation_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Bids_jdo.CREATION_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Bids_jdo.CREATION_DATE))).getTime();
            record.setCreation_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.CREATION_HOST))){
          if(keymap.get(Bids_jdo.CREATION_HOST) != null)
            record.setCreation_host(null);
        }else{
             record.setCreation_host(new String(jo.getString((String)keymap.get(Bids_jdo.CREATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.MODIFIED_BY))){
          if(keymap.get(Bids_jdo.MODIFIED_BY) != null)
            record.setModified_by(null);
        }else{
             record.setModified_by(new Integer(jo.getInt((String)keymap.get(Bids_jdo.MODIFIED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.MODIFIED_DATE))){
          if(keymap.get(Bids_jdo.MODIFIED_DATE) != null)
            record.setModified_date(null);
        }else{
          try{
             record.setModified_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Bids_jdo.MODIFIED_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Bids_jdo.MODIFIED_DATE))).getTime();
            record.setModified_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.MODIFIER_HOST))){
          if(keymap.get(Bids_jdo.MODIFIER_HOST) != null)
            record.setModifier_host(null);
        }else{
             record.setModifier_host(new String(jo.getString((String)keymap.get(Bids_jdo.MODIFIER_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Bids_jdo.STATUS))){
          if(keymap.get(Bids_jdo.STATUS) != null)
            record.setStatus(null);
        }else{
             record.setStatus(new Integer(jo.getInt((String)keymap.get(Bids_jdo.STATUS))));
        }
      }catch(org.json.JSONException e){
      }
    }
  }
	public Bids_jdo bindBidsJSON(String jsondata) throws Exception {
		Bids_jdo retval = null;
		JSONObject jo = new JSONObject(jsondata);
		retval = this.bindBidsJSON(jo);
		return retval;
	}
	private Bids_jdo bindBidsJSON(JSONObject jo) throws Exception{
		Iterator keys = jo.keys();
		HashMap keymap = new HashMap();
		while(keys.hasNext()){
			String key = keys.next().toString();
			String lc_key = key.toUpperCase();
			keymap.put(lc_key, key);
		}
		Bids_jdo record = null;
		try{
			if(!jo.isNull((String)keymap.get(IDENTITY))){
			record = this.getBids(new Integer(jo.getInt((String)keymap.get(IDENTITY))));
			}
		}catch(JSONException e){}
		if(record == null){
			record = this.newBids();
		}
		bindBidsJSON(record, jo);
		return record;
	}
}
