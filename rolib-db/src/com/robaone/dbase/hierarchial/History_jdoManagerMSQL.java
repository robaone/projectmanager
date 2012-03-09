/*
 * Created on Feb 20, 2011
 *
 */
package com.robaone.dbase.hierarchial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.robaone.dbase.HDBConnectionManager;
import com.robaone.jdo.RO_JDO_IdentityManager;


public class History_jdoManagerMSQL {
	private Connection m_con;
	private final static String SELECT = "select ~ from #TABLE# where id = ?";
	private final static String INSERT = "insert into #TABLE# ";
	private final static String QUERY = "select ~ from #TABLE# where ";
	private final static String UPDATE = "update #TABLE# set ";
	private final static String SEARCH = "select COUNT(#TABLE#.ID) from #TABLE# where #TABLE#.ID = ?";
	private final static String DELETE = "delete from #TABLE# where #TABLE#.ID = ?";
	private final static String IDENTITY = "ID";
	private final static RO_JDO_IdentityManager NEXT_SQL = new ConfigIDManager();
	public final static String FIELDS = "#TABLE#.ID,#TABLE#.OBJECTID,#TABLE#.NAME,#TABLE#.PARENT,#TABLE#.TYPE,#TABLE#.TITLE,#TABLE#.DESCRIPTION,#TABLE#.STRING_VALUE,#TABLE#.NUMBER_VALUE,#TABLE#.BOOL_VALUE,#TABLE#.DATE_VALUE,#TABLE#.TEXT_VALUE,#TABLE#.BINARY_VALUE,#TABLE#.CONTENT_TYPE,#TABLE#.MODIFIED_BY,#TABLE#.MODIFIED_DATE,#TABLE#.MODIFIER_HOST";
	private String TABLE = "HISTORY";
	private boolean debug = false;
	public History_jdoManagerMSQL(Connection con){
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
	public static History_jdoMSQL bindHistory(ResultSet rs) throws SQLException{
		History_jdoMSQL retval = null;
		retval = History_jdoManagerMSQL.createObject(rs);
		return retval;
	}

	public History_jdoMSQL getHistory(Integer id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		History_jdoMSQL retval = null;
		try{
			String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
			ps = this.getConnection().prepareStatement(sql);
			ps.setObject(1,id);
			rs = ps.executeQuery();
			if(rs.next()){
				retval = History_jdoManagerMSQL.createObject(rs);
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
	private static History_jdoMSQL createObject(ResultSet rs) throws SQLException {
		History_jdoMSQL retval = null;
		retval = new History_jdoMSQL();
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
			if(fieldname.equals(History_jdoMSQL.BINARY_VALUE)){
				val[0] = rs.getBlob(i+1);
			}else if(fieldname.equals(History_jdoMSQL.DESCRIPTION) || fieldname.equals(History_jdoMSQL.TEXT_VALUE)){
				val[0] = rs.getClob(i+1);
			}else{
				val[0] = rs.getObject(i+1);
				if(val[0] instanceof java.sql.Date){
					val[0] = new java.util.Date(rs.getTimestamp(i+1).getTime());
				}
			}
			retval.bindField(fieldname,val);
		}

		return retval;
	}
	public void save(History_jdoMSQL record) throws Exception {
		Connection con = this.getConnection();
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
					String fieldname = record.getDirtyField(i);
					Object value = record.getField(fieldname)[0];
					if(value instanceof java.sql.Timestamp){
						update_ps.setObject(i+1,value);
					}else if(value instanceof java.sql.Date){
						update_ps.setObject(i+1,new java.sql.Timestamp(((java.sql.Date)value).getTime()));
					}else if(value instanceof java.util.Date){
						value = new java.sql.Timestamp(((java.util.Date)value).getTime());
						update_ps.setObject(i+1,value);
					}else if(value == null){
						update_ps.setNull(i+1, this.getSQLType(fieldname));
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
				Integer new_id = ((ConfigIDManager)NEXT_SQL).getIdentity(getTableName(),getConnection());
				String insert_pre,insert_post;
				insert_pre = "("; insert_post = "(";
				for(int i = 0;i < record.getFieldCount();i++){
					String fieldname = record.getField(i);
					if(i > 0){
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
					if(fieldname.equals(record.getIdentityName())){
						insert_ps.setInt(field_index, new_id);
					}else{
						Object[] val = record.getField(fieldname);
						if(val[0] instanceof java.sql.Timestamp){
							insert_ps.setObject(field_index,val[0]);
						}else if(val[0] instanceof java.sql.Date){
							insert_ps.setObject(field_index,new java.sql.Timestamp(((java.sql.Date)val[0]).getTime()));
						}else
							if(val[0] instanceof java.util.Date){
								val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime());
								insert_ps.setObject(field_index,val[0]);
							}else if(val[0] == null){
								insert_ps.setNull(field_index, this.getSQLType(fieldname));
							}else{
								insert_ps.setObject(field_index,val[0]);
							}
					}
					field_index ++;
				}
				int updated = insert_ps.executeUpdate();
				record.setId(new Integer(new_id));
				con.commit();
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
		}
	}
	private HDBConnectionManager getConnectionManager() {
		return new HDBConnectionManager(){

			public Connection getConnection() throws Exception {
				return History_jdoManagerMSQL.this.getConnection();
			}

			public void closeConnection(Connection m_con) throws Exception {

			}

			public String getDriver() {
				return null;
			}

		};
	}
	private int getSQLType(String fieldname) {
		java.util.HashMap map = new java.util.HashMap();
		map.put(History_jdoMSQL.ID, new Integer(java.sql.Types.INTEGER));
		map.put(History_jdoMSQL.OBJECTID, new Integer(java.sql.Types.INTEGER));
		map.put(History_jdoMSQL.NAME, new Integer(java.sql.Types.VARCHAR));
		map.put(History_jdoMSQL.PARENT, new Integer(java.sql.Types.INTEGER));
		map.put(History_jdoMSQL.TYPE, new Integer(java.sql.Types.INTEGER));
		map.put(History_jdoMSQL.TITLE, new Integer(java.sql.Types.VARCHAR));
		map.put(History_jdoMSQL.DESCRIPTION,new Integer( java.sql.Types.CLOB));
		map.put(History_jdoMSQL.STRING_VALUE, new Integer(java.sql.Types.VARCHAR));
		map.put(History_jdoMSQL.NUMBER_VALUE, new Integer(java.sql.Types.NUMERIC));
		map.put(History_jdoMSQL.BOOL_VALUE, new Integer(java.sql.Types.TINYINT));
		map.put(History_jdoMSQL.DATE_VALUE,new Integer(java.sql.Types.TIMESTAMP));
		map.put(History_jdoMSQL.TEXT_VALUE, new Integer(java.sql.Types.CLOB));
		map.put(History_jdoMSQL.BINARY_VALUE, new Integer(java.sql.Types.BLOB));
		map.put(History_jdoMSQL.CONTENT_TYPE, new Integer(java.sql.Types.VARCHAR));
		map.put(History_jdoMSQL.MODIFIED_BY, new Integer(java.sql.Types.VARCHAR));
		map.put(History_jdoMSQL.MODIFIED_DATE,new Integer(java.sql.Types.TIMESTAMP));
		map.put(History_jdoMSQL.MODIFIER_HOST, new Integer(java.sql.Types.VARCHAR));
		return ((Integer)map.get(fieldname)).intValue();
	}
	protected void handleAfterInsert(History_jdoMSQL record) {}
	protected void handleAfterUpdate(History_jdoMSQL record) {}
	protected void handleBeforeUpdate(History_jdoMSQL record) {}
	private void setAllClean(History_jdoMSQL record){
		try{
			for(int i = 0; i < record.getFieldCount();i++){
				String fieldname = record.getField(i);
				Object[] val = record.getField(fieldname);
				if(val != null)
					val[1] = new Boolean(false);
			}
		}catch(Exception e){}
	}
	public void delete(History_jdoMSQL record) throws Exception {
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
	public History_jdoMSQL newHistory() {
		History_jdoMSQL retval = new History_jdoMSQL();
		retval.setId(null);
		return retval;
	}
	public String getSQL(String sql){
		String retval = "";
		retval = sql.replaceAll("#TABLE#",this.getTableName());
		return retval;
	}
}
