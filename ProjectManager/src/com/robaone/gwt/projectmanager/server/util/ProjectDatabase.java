package com.robaone.gwt.projectmanager.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import com.robaone.gwt.projectmanager.client.data.UserData;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.util.ConfigManager.TYPE;

public class ProjectDatabase {
	private String[] m_driver = {"org.hsqldb.jdbc.JDBCDriver","com.mysql.jdbc.Driver","com.microsoft.sqlserver.jdbc.SQLServerDriver"};
	private String[] m_url = {"jdbc:hsqldb:mem:mymemdb","jdbc:mysql://localhost:3306/test","jdbc:sqlserver://192.168.1.29;databaseName=eStmtDev;integratedSecurity=true"};
	private String[] m_username = {"SA","root",""};
	private String[] m_password = {"","",""};
	private Connection m_con;
	public ProjectDatabase() throws Exception{
		try{
			this.m_con =  this.getConnection();
			this.createDatabaseTables();
		}catch(Exception e){
			throw new SQLException(e.getMessage());
		}finally{
			try{this.m_con.close();}catch(Exception e){}
		}
	}
	private String createConfigTableSQL() {
		String str = "create table CONFIG (\n"+
		"  ID numeric(18,0) primary key not null,\n"+
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
	private String createHistoryTableSQL(){
		String str = "create table HISTORY (\n"+
		"  ID numeric(18,0) primary key not null,\n"+
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
		"  MODIFIER_HOST varchar(32) not null\n"+
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
		if(driver.startsWith("com.hsqldb.")){
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
	private void createDatabaseTables() throws Exception {

		java.util.Vector<Object> parameters = new java.util.Vector<Object>();
		try{
			this.executeUpdate(this.createConfigTableSQL(),parameters);
			this.executeUpdate(this.createUniqueConfigConstraint(), parameters);
			this.executeUpdate(this.createHistoryTableSQL(), parameters);
			parameters.add(new java.sql.Timestamp(new java.util.Date().getTime()));
			//this.executeUpdate(this.initializedata(),parameters);
			SessionData session = new SessionData();
			session.setCurrentHost("localhost");
			session.setUserData(new UserData());
			session.getUserData().setUsername("root");
			ConfigManager root_user = new ConfigManager("/administration/users/root/password","password",TYPE.STRING,"The superuser password","This is the password that you need to gain complete access to the application.",session);
			System.out.println("root password set to "+root_user.getString());
			ConfigManager root_role = new ConfigManager("/administration/users/root/role","0",TYPE.INT,"The super user role","Valid roles are number 0 for superuser through x",session);
			System.out.println("root role set to "+root_role.getInt());
		}catch(Exception e){
			if(!e.getMessage().startsWith("object name already exists") && !e.getMessage().endsWith("already exists")
					&& !e.getMessage().startsWith("There is already an object named")){
				e.printStackTrace();
				throw e;
			}
		}
	}	
	private String createUniqueConfigConstraint(){
		String str = "alter table CONFIG add constraint UC_NAME UNIQUE (NAME,PARENT"+
		");";
		return str;
	}
	private int executeUpdate(String str, Vector<Object> parameters) throws Exception{
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
}
