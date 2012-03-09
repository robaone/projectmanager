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
import java.util.Vector;

import com.robaone.dbase.HDBConnectionManager;

public class Config_jdoManager {
	private Connection m_con;
	private final static String SELECT = "select ~ from #TABLE# where id = ?";
	private final static String INSERT = "insert into #TABLE# ";
	private final static String QUERY = "select ~ from #TABLE# where ";
	private final static String UPDATE = "update #TABLE# set ";
	private final static String SEARCH = "select COUNT(#TABLE#.ID) from #TABLE# where #TABLE#.ID = ?";
	private final static String DELETE = "delete from #TABLE# where #TABLE#.ID = ?";
	private final static String IDENTITY = "ID";
	private final static String NEXT_SQL = "Select max(id) +1 from #TABLE#";
	public final static String FIELDS = "#TABLE#.ID,#TABLE#.NAME,#TABLE#.PARENT,#TABLE#.TYPE,#TABLE#.TITLE,#TABLE#.DESCRIPTION,#TABLE#.STRING_VALUE,#TABLE#.NUMBER_VALUE,#TABLE#.BOOL_VALUE,#TABLE#.DATE_VALUE,#TABLE#.TEXT_VALUE,#TABLE#.BINARY_VALUE,#TABLE#.CONTENT_TYPE,#TABLE#.CREATED_BY,#TABLE#.CREATED_DATE,#TABLE#.MODIFIED_BY,#TABLE#.MODIFIED_DATE,#TABLE#.MODIFIER_HOST";
	private String TABLE = "CONFIG";
	protected boolean debug = false;
	public Config_jdoManager(Connection con){
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
	public static Config_jdo bindConfig(ResultSet rs) throws SQLException{
		Config_jdo retval = null;
		retval = Config_jdoManager.createObject(rs);
		return retval;
	}

	public Config_jdo getConfig(BigDecimal id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Config_jdo retval = null;
		try{
			String sql = this.getSQL(SELECT.split("[~]")[0]+FIELDS+SELECT.split("[~]")[1]);
			ps = this.getConnection().prepareStatement(sql);
			ps.setObject(1,id);
			rs = ps.executeQuery();
			if(rs.next()){
				retval = Config_jdoManager.createObject(rs);
			}
		}catch(Exception e){
			e.printStackTrace();
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
	private static Config_jdo createObject(ResultSet rs) throws SQLException {
		Config_jdo retval = null;
		retval = new Config_jdo();
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
			if(fieldname.equals(Config_jdo.BINARY_VALUE)){
				val[0] = rs.getBlob(i+1);
			}else if(fieldname.equals(Config_jdo.DESCRIPTION) || fieldname.equals(Config_jdo.TEXT_VALUE)){
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
	public void save(Config_jdo record) throws Exception {
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
				BigDecimal new_id = null;
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
						HDBConnectionManager cman = new HDBConnectionManager(){

							public Connection getConnection() throws Exception {
								return Config_jdoManager.this.getConnection();
							}

							public void closeConnection(Connection m_con)
									throws Exception {
								
							}

							public String getDriver() {
								return null;
							}
							
						};
						ProjectDatabase db = new ProjectDatabase(this.getTableName(),cman);
						new_id = db.getNextIndex(this.getTableName(), cman,IDENTITY);
						insert_post += new_id;
						
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
					}else if(val[0] instanceof java.util.Date){
						val[0] = new java.sql.Date(((java.util.Date)val[0]).getTime());
						insert_ps.setObject(field_index,val[0]);
					}else if(val[0] == null){
						insert_ps.setNull(field_index, this.getSQLType(fieldname));
					}else{
						insert_ps.setObject(field_index,val[0]);
					}
					field_index ++;
				}
				int updated = insert_ps.executeUpdate();
				con.commit();
				record.setId(new_id);
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
	protected void handleAfterInsert(Config_jdo record) {}
	protected void handleAfterUpdate(Config_jdo record) {}
	protected void handleBeforeUpdate(Config_jdo record) {}
	protected void handleBeforeDelete(Config_jdo record) {}
	protected void handleAfterDelete(Config_jdo record) {}
	private void setAllClean(Config_jdo record){
		try{
			for(int i = 0; i < record.getFieldCount();i++){
				String fieldname = record.getField(i);
				Object[] val = record.getField(fieldname);
				if(val != null)
					val[1] = new Boolean(false);
			}
		}catch(Exception e){}
	}
	public void delete(Config_jdo record) throws Exception {
		this.handleBeforeDelete(record);
		Connection con = this.getConnection();
		String sql_delete = this.getSQL(DELETE);
		PreparedStatement ps = con.prepareStatement(sql_delete);
		ps.setObject(1, record.getField(IDENTITY)[0]);
		int updated = ps.executeUpdate();
		if(debug) System.out.println(updated +" records deleted.");
		this.handleAfterDelete(record);
	}
	public PreparedStatement prepareStatement(String query) throws SQLException{
		String sql = this.getSQL(QUERY.split("[~]")[0]+FIELDS+QUERY.split("[~]")[1] + query);
		if(debug) System.out.println(sql);
		PreparedStatement ps = this.getConnection().prepareStatement(sql);
		return ps;
	}
	public Config_jdo newConfig() {
		Config_jdo retval = new Config_jdo();
		retval.setId(null);
		return retval;
	}
	public String getSQL(String sql){
		String retval = "";
		retval = sql.replaceAll("#TABLE#",TABLE);
		return retval;
	}
	private int getSQLType(String fieldname) {
		java.util.HashMap map = new java.util.HashMap();
		map.put(Config_jdo.ID, new Integer(java.sql.Types.NUMERIC));
		map.put(Config_jdo.NAME, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.PARENT, new Integer(java.sql.Types.NUMERIC));
		map.put(Config_jdo.TYPE, new Integer(java.sql.Types.INTEGER));
		map.put(Config_jdo.TITLE, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.DESCRIPTION, new Integer(java.sql.Types.CLOB));
		map.put(Config_jdo.STRING_VALUE, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.NUMBER_VALUE, new Integer(java.sql.Types.NUMERIC));
		map.put(Config_jdo.BOOL_VALUE, new Integer(java.sql.Types.TINYINT));
		map.put(Config_jdo.DATE_VALUE,new Integer(java.sql.Types.TIMESTAMP));
		map.put(Config_jdo.TEXT_VALUE, new Integer(java.sql.Types.CLOB));
		map.put(Config_jdo.BINARY_VALUE, new Integer(java.sql.Types.BLOB));
		map.put(Config_jdo.CONTENT_TYPE, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.MODIFIED_BY, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.MODIFIED_DATE,new Integer(java.sql.Types.TIMESTAMP));
		map.put(Config_jdo.MODIFIER_HOST, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.CREATED_BY, new Integer(java.sql.Types.VARCHAR));
		map.put(Config_jdo.CREATED_DATE, new Integer(java.sql.Types.TIMESTAMP));
		return ((Integer)map.get(fieldname)).intValue();
	}
}
