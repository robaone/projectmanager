package com.robaone.dbase.hierarchial;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;

public class ProjectDatabase {
	private Connection m_con;
	//private static boolean m_dbcreated = false;
	private static HashMap m_dbcreated = new HashMap();
	private HDBConnectionManager m_cman;
	public ProjectDatabase(String table,HDBConnectionManager cman) throws Exception{
		try{
			this.m_cman = cman;
			this.m_con =  this.getConnection();
			if(m_dbcreated.get(table) == null){
				this.createDatabaseTables(table);
				m_dbcreated.put(table, new Boolean(true));
			}
		}catch(Exception e){
			throw new SQLException(e.getMessage());
		}finally{
			this.m_cman.closeConnection(this.m_con);
		}
	}
	public ProjectDatabase() {
		HDBConnectionManager man = new HDBConnectionManager(){

			public Connection getConnection() throws Exception {
				String driver,url,username,password;
				driver = System.getProperty("db_driver");
				url = System.getProperty("db_url");
				username = System.getProperty("db_username");
				password = System.getProperty("db_password");
				try {
					Class.forName(driver );
				} catch (Exception e) {
					System.err.println("ERROR: failed to load JDBC driver.");
					e.printStackTrace();
					throw e;
				}
				return DriverManager.getConnection(url, username, password);
			}

			public void closeConnection(Connection m_con) throws Exception {
				m_con.close();
			}

			public String getDriver() {
				return System.getProperty("db_driver");
			}

		};
		this.m_cman = man;
	}
	private String createConfigTableSQL(String table) {
		String str = "create table "+table+" (\n"+
		"  ID numeric(18,0) "+this.getPrimarySpec()+",\n"+
		"  NAME varchar(128) not null,\n"+
		"  PARENT numeric(18,0),\n"+
		"  TYPE integer not null,\n"+
		"  TITLE varchar(128) not null,\n"+
		"  DESCRIPTION "+this.getTextType()+",\n"+
		"  STRING_VALUE varchar(128),\n"+
		"  NUMBER_VALUE numeric(18,4),\n"+
		"  BOOL_VALUE tinyint,\n"+
		"  DATE_VALUE "+this.getDateType()+",\n"+
		"  TEXT_VALUE "+this.getTextType()+",\n"+
		"  BINARY_VALUE "+this.getBlobType()+",\n"+
		"  CONTENT_TYPE varchar(32),\n"+
		"  CREATED_BY varchar(128) not null,\n"+
		"  CREATED_DATE "+this.getDateType()+" not null,\n"+
		"  MODIFIED_BY varchar(128),\n"+
		"  MODIFIED_DATE "+this.getDateType()+",\n"+
		"  MODIFIER_HOST varchar(32) not null\n"+
		");";
		return str;
	}
	private String getPrimarySpec() {
		int dr = 0;
		try{
			dr = this.getDriverChoice();
		}catch(Exception e){}
		if(dr == 0){
			return "not null identity primary key";
		}else if(dr == 1){
			return "primary key auto_increment not null";
		}else if(dr == 2){
			return "identity (1,1) not null";
		}
		return null;
	}
	private String createHistoryTableSQL(String table){
		String str = "create table "+(table.equals("CONFIG") ? "HISTORY" : table+"_HISTORY")+" (\n"+
		"  ID numeric(18,0) "+this.getPrimarySpec()+",\n"+
		"  OBJECTID numeric(18,0) not null,\n"+
		"  NAME varchar(128) not null,\n"+
		"  PARENT numeric(18,0),\n"+
		"  TYPE integer not null,\n"+
		"  TITLE varchar(128) not null,\n"+
		"  DESCRIPTION "+this.getTextType()+",\n"+
		"  STRING_VALUE varchar(128),\n"+
		"  NUMBER_VALUE numeric(18,4),\n"+
		"  BOOL_VALUE tinyint,\n"+
		"  DATE_VALUE "+this.getDateType()+",\n"+
		"  TEXT_VALUE "+this.getTextType()+",\n"+
		"  BINARY_VALUE "+this.getBlobType()+",\n"+
		"  CONTENT_TYPE varchar(32),\n"+
		"  MODIFIED_BY varchar(128),\n"+
		"  MODIFIED_DATE "+this.getDateType()+",\n"+
		"  MODIFIER_HOST varchar(32) not null,\n"+
		"  DELETED_BY varchar(128),\n"+
		"  DELETED_DATE "+this.getDateType()+",\n"+
		"  DELETION_HOST varchar(32)\n"+
		");";
		return str;
	}
	private String getBlobType() {
		int dr = 0;
		try{
			dr = this.getDriverChoice();
		}catch(Exception e){}
		if(dr == 0 || dr == 1){
			return "blob";
		}else if(dr == 2){
			return "varbinary(MAX)";
		}
		return null;
	}
	private int getDriverChoice() {
		String driver = System.getProperty("db_driver");
		if(driver.startsWith("org.hsqldb.")){
			return 0;
		}else if(driver.startsWith("com.mysql.")){
			return 1;
		}else if(driver.startsWith("com.microsoft.sqlserver.")){
			return 2;
		}
		return 0;
	}
	private String getDateType() {
		int dr = 0;
		try{
			dr = this.getDriverChoice();
		}catch(Exception e){}
		if(dr == 0){
			return "timestamp";
		}else if(dr == 1){
			return "timestamp";
		}else if(dr == 2){
			return "datetime";
		}
		return null;
	}
	private String getTextType() {
		int dr = 0;
		try{
			dr = this.getDriverChoice();
		}catch(Exception e){}
		if(dr == 0){
			return "clob";
		}else if(dr == 1){
			return "text";
		}else if(dr == 2){
			return "text";
		}
		return null;
	}
	private void createDatabaseTables(String table) throws Exception {

		java.util.Vector parameters = new java.util.Vector();
		try{
			//this.executeUpdate(this.createNextIndexTableSQL(table), parameters);
			this.executeUpdate(this.createConfigTableSQL(table),parameters);
			this.executeUpdate(this.createUniqueConfigConstraint(table), parameters);
			this.executeUpdate(this.createHistoryTableSQL(table), parameters);
		}catch(Exception e){
			if(!e.getMessage().startsWith("object name already exists") && !e.getMessage().endsWith("already exists")
					&& !e.getMessage().startsWith("There is already an object named")){
				e.printStackTrace();
				throw e;
			}
		}
	}	
	private String createNextIndexTableSQL(String table) {
		String str = "create table next_index (\n"+
		" name varchar(64) primary key not null,\n"+
		" next_index numeric(12,0) not null\n"+
		");";
		return str;
	}
	private String createUniqueConfigConstraint(String table){
		String str = "alter table "+table+" add constraint "+table+"_idx1 UNIQUE (NAME,PARENT"+
		");";
		return str;
	}
	private int executeUpdate(String str, Vector parameters) throws Exception{
		java.sql.PreparedStatement ps = null;
		int updated = 0;
		try{
			ps = this.m_con.prepareStatement(str);
			for(int i = 0 ; i < parameters.size();i++){
				ps.setObject(i+1, parameters.get(i));
			}
			updated = ps.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally{
			try{ps.close();}catch(Exception e){}
		}
		return updated;
	}
	public java.sql.Connection getConnection() throws Exception{
		return this.m_cman.getConnection();
	}
	public BigDecimal getNextIndex(String table,HDBConnectionManager cman,String primary_key) throws Exception {
		return this.getNextIndex(table, cman,primary_key,1);
	}
	public BigDecimal getNextIndex(final String table,final HDBConnectionManager cman,final String primary_key,long allocation) throws Exception {
		BigDecimal retval = null;
		if(allocation < 1){
			allocation = 1;
		}
		final long allocation_f = allocation;
		final Vector v = new Vector();
		java.sql.Connection c = cman.getConnection();
		boolean autocommit = c.getAutoCommit();
		cman.closeConnection(c);
		final HDBConnectionManager transaction_man = new HDBConnectionManager(){
			private java.sql.Connection m_con = null;
			public Connection getConnection() throws Exception {
				if(m_con == null){
					m_con =  cman.getConnection();
				}
				return m_con;
			}

			public void closeConnection(Connection m_con) throws Exception {

			}

			public String getDriver() {
				return null;
			}

		};
		ConnectionBlock block = new ConnectionBlock(){

			protected void run() throws Exception {
				String str = "update next_index set next_index = (select MAX(+"+primary_key+")+1 from "+table+") where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, table);
				this.getPreparedStatement().executeUpdate();
				str = "select next_index from next_index where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, table);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					v.add(this.getResultSet().getBigDecimal(1));
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							String str = "update next_index set next_index = ? where name = ?";
							this.setPreparedStatement(this.getConnection().prepareStatement(str));
							BigDecimal next = ((BigDecimal)v.get(0)).add(new BigDecimal(allocation_f));
							this.getPreparedStatement().setBigDecimal(1, next);
							this.getPreparedStatement().setString(2, table);
							int updated = this.getPreparedStatement().executeUpdate();
						}

					};
					ConfigManager.runConnectionBlock(block, transaction_man);
				}else{
					v.add(new BigDecimal(0));
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							String str = "insert into next_index (name,next_index) values (?,?)";
							this.setPreparedStatement(this.getConnection().prepareStatement(str));
							this.getPreparedStatement().setString(1, table);
							BigDecimal next = ((BigDecimal)v.get(0)).add(new BigDecimal(allocation_f));
							this.getPreparedStatement().setBigDecimal(2, next);
							int updated = this.getPreparedStatement().executeUpdate();
							str = "update next_index set next_index = (select MAX(+"+primary_key+")+1 from "+table+") where name = ?";
							this.setPreparedStatement(this.getConnection().prepareStatement(str));
							this.getPreparedStatement().setString(1, table);
							this.getPreparedStatement().executeUpdate();
						}
						
					};
					ConfigManager.runConnectionBlock(block, transaction_man);
				}

			}

		};
		try{
			ConfigManager.runConnectionBlock(block, transaction_man);
			if(v.size() > 0){
				retval = (BigDecimal)v.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			transaction_man.getConnection().commit();
			cman.closeConnection(transaction_man.getConnection());
		}
		return retval;
	}
}
