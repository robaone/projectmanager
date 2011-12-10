/*
* Created on Dec 09, 2011
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

public class Comments_jdoManager {
  private Connection m_con;
  private final static String SELECT = "select ~ from #TABLE# where idcomments = ?";
  private final static String INSERT = "insert into #TABLE# ";
  private final static String QUERY = "select ~ from #TABLE# where ";
  private final static String UPDATE = "update #TABLE# set ";
  private final static String SEARCH = "select COUNT(#TABLE#.IDCOMMENTS) from #TABLE# where #TABLE#.IDCOMMENTS = ?";
  private final static String DELETE = "delete from #TABLE# where #TABLE#.IDCOMMENTS = ?";
  private final static String IDENTITY = "IDCOMMENTS";
  private final static RO_JDO_IdentityManager NEXT_SQL = new RO_JDO_MySQL();
  public final static String FIELDS = "#TABLE#.IDCOMMENTS,#TABLE#.REFERENCEID,#TABLE#.COMMENT,#TABLE#.CREATED_BY,#TABLE#.CREATION_DATE,#TABLE#.CREATION_HOST,#TABLE#.MODIFIED_BY,#TABLE#.MODIFIED_DATE,#TABLE#.MODIFICATION_HOST,#TABLE#._VOID";
  private String TABLE = "COMMENTS";
  protected boolean debug = false;
  public Comments_jdoManager(Connection con){
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
  public static Comments_jdo bindComments(ResultSet rs) throws SQLException{
Comments_jdo retval = null;
    retval = Comments_jdoManager.createObject(rs);
    return retval;
  }

  public Comments_jdo getComments(Integer idcomments){
    PreparedStatement ps = null;
    ResultSet rs = null;
    Comments_jdo retval = null;
    try{
      String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
      ps = this.getConnection().prepareStatement(sql);
      ps.setObject(1,idcomments);
      rs = ps.executeQuery();
      if(rs.next()){
        retval = Comments_jdoManager.createObject(rs);
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
  private static Comments_jdo createObject(ResultSet rs) throws SQLException {
    Comments_jdo retval = null;
    retval = new Comments_jdo();
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
  public void save(Comments_jdo record) throws Exception {
    Connection con = this.getConnection();
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
        record.setIdcomments(NEXT_SQL.getIdentity(this.m_con));
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
			  }
			  con.setAutoCommit(true);
  }
}
	protected void handleAfterInsert(Comments_jdo record) {}
	protected void handleAfterUpdate(Comments_jdo record) {}
	protected void handleBeforeUpdate(Comments_jdo record) {}
  private void setAllClean(Comments_jdo record){
    try{
      for(int i = 0; i < record.getFieldCount();i++){
         String fieldname = record.getField(i);
         Object[] val = record.getField(fieldname);
         if(val != null)
           val[1] = new Boolean(false);
      }
    }catch(Exception e){}
  }
  public void delete(Comments_jdo record) throws Exception {
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
public Comments_jdo newComments() {
Comments_jdo retval = new Comments_jdo();
 retval.setIdcomments(null);
 return retval;
}
  public String getSQL(String sql){
    String retval = "";
    retval = sql.replaceAll("#TABLE#",TABLE);
    return retval;
  }
  public static JSONObject toJSONObject(Comments_jdo record) throws Exception {
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
  public static void bindCommentsJSON(Comments_jdo record,String jsondata) throws Exception {
    JSONObject jo = new JSONObject(jsondata);
    Comments_jdoManager.bindCommentsJSON(record,jo);
  }
  public static void bindCommentsJSON(Comments_jdo record, JSONObject jo) throws Exception {
    Iterator keys = jo.keys();
    HashMap keymap = new HashMap();
    while(keys.hasNext()){
      String key = keys.next().toString();
      String lc_key = key.toUpperCase();
      keymap.put(lc_key, key);
    }
    if(record != null && jo != null){
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.IDCOMMENTS))){
          if(keymap.get(Comments_jdo.IDCOMMENTS) != null)
            record.setIdcomments(null);
        }else{
             record.setIdcomments(new Integer(jo.getInt((String)keymap.get(Comments_jdo.IDCOMMENTS))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.REFERENCEID))){
          if(keymap.get(Comments_jdo.REFERENCEID) != null)
            record.setReferenceid(null);
        }else{
             record.setReferenceid(new String(jo.getString((String)keymap.get(Comments_jdo.REFERENCEID))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.COMMENT))){
          if(keymap.get(Comments_jdo.COMMENT) != null)
            record.setComment(null);
        }else{
             record.setComment(new String(jo.getString((String)keymap.get(Comments_jdo.COMMENT))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.CREATED_BY))){
          if(keymap.get(Comments_jdo.CREATED_BY) != null)
            record.setCreated_by(null);
        }else{
             record.setCreated_by(new Integer(jo.getInt((String)keymap.get(Comments_jdo.CREATED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.CREATION_DATE))){
          if(keymap.get(Comments_jdo.CREATION_DATE) != null)
            record.setCreation_date(null);
        }else{
          try{
             record.setCreation_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Comments_jdo.CREATION_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Comments_jdo.CREATION_DATE))).getTime();
            record.setCreation_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.CREATION_HOST))){
          if(keymap.get(Comments_jdo.CREATION_HOST) != null)
            record.setCreation_host(null);
        }else{
             record.setCreation_host(new String(jo.getString((String)keymap.get(Comments_jdo.CREATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.MODIFIED_BY))){
          if(keymap.get(Comments_jdo.MODIFIED_BY) != null)
            record.setModified_by(null);
        }else{
             record.setModified_by(new Integer(jo.getInt((String)keymap.get(Comments_jdo.MODIFIED_BY))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.MODIFIED_DATE))){
          if(keymap.get(Comments_jdo.MODIFIED_DATE) != null)
            record.setModified_date(null);
        }else{
          try{
             record.setModified_date(new java.sql.Timestamp(jo.getLong((String)keymap.get(Comments_jdo.MODIFIED_DATE))));
          }catch(JSONException e){
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
            long time = df.parse(jo.getString((String)keymap.get(Comments_jdo.MODIFIED_DATE))).getTime();
            record.setModified_date(new java.sql.Timestamp(time));
          }
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo.MODIFICATION_HOST))){
          if(keymap.get(Comments_jdo.MODIFICATION_HOST) != null)
            record.setModification_host(null);
        }else{
             record.setModification_host(new String(jo.getString((String)keymap.get(Comments_jdo.MODIFICATION_HOST))));
        }
      }catch(org.json.JSONException e){
      }
      try{
        if(jo.isNull((String)keymap.get(Comments_jdo._VOID))){
          if(keymap.get(Comments_jdo._VOID) != null)
            record.set_void(null);
        }else{
             record.set_void(new Boolean(jo.getBoolean((String)keymap.get(Comments_jdo._VOID))));
        }
      }catch(org.json.JSONException e){
      }
    }
  }
	public Comments_jdo bindCommentsJSON(String jsondata) throws Exception {
		Comments_jdo retval = null;
		JSONObject jo = new JSONObject(jsondata);
		retval = this.bindCommentsJSON(jo);
		return retval;
	}
	private Comments_jdo bindCommentsJSON(JSONObject jo) throws Exception{
		Iterator keys = jo.keys();
		HashMap keymap = new HashMap();
		while(keys.hasNext()){
			String key = keys.next().toString();
			String lc_key = key.toUpperCase();
			keymap.put(lc_key, key);
		}
		Comments_jdo record = null;
		try{
			if(!jo.isNull((String)keymap.get(IDENTITY))){
			record = this.getComments(new Integer(jo.getInt((String)keymap.get(IDENTITY))));
			}
		}catch(JSONException e){}
		if(record == null){
			record = this.newComments();
		}
		Comments_jdoManager.bindCommentsJSON(record, jo);
		return record;
	}}
