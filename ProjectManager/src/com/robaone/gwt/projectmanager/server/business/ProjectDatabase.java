package com.robaone.gwt.projectmanager.server.business;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.server.ConfigManager;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.ConfigManager.TYPE;

public class ProjectDatabase {
	private java.sql.Connection m_con;
	public ProjectDatabase() throws SQLException{
		try {
		      Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		  } catch (Exception e) {
		      System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		      e.printStackTrace();
		      return;
		  }
		  java.sql.Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
		  m_con = c;
		  try{
			  this.createDatabaseTables();
		  }catch(Exception e){
			  throw new SQLException(e.getMessage());
		  }
	}
	private String createConfigTableSQL() {
		String str = "create table config (\n"+
		"  id numeric(18,0) primary key not null,\n"+
		"  name varchar(128) not null,\n"+
		"  parent numeric(18,0),\n"+
		"  type integer not null,\n"+
		"  title varchar(128) not null,\n"+
		"  description clob,\n"+
		"  string_value varchar(128),\n"+
		"  number_value numeric(18,4),\n"+
		"  bool_value tinyint,\n"+
		"  date_value timestamp,\n"+
		"  text_value clob,\n"+
		"  binary_value blob,\n"+
		"  created_by varchar(128) not null,\n"+
		"  created_date timestamp not null,\n"+
		"  modified_by varchar(128),\n"+
		"  modified_date timestamp,\n"+
		"  modifier_host varchar(32) not null\n"+
		");";
		return str;
	}
	private String createHistoryTableSQL(){
		String str = "create table history (\n"+
		"  id numeric(18,0) primary key not null,\n"+
		"  objectid numeric(18,0) not null,\n"+
		"  name varchar(128) not null,\n"+
		"  parent numeric(18,0),\n"+
		"  type integer not null,\n"+
		"  title varchar(128) not null,\n"+
		"  description clob,\n"+
		"  string_value varchar(128),\n"+
		"  number_value numeric(18,4),\n"+
		"  bool_value tinyint,\n"+
		"  date_value timestamp,\n"+
		"  text_value clob,\n"+
		"  binary_value blob,\n"+
		"  modified_by varchar(128),\n"+
		"  modified_date timestamp,\n"+
		"  modifier_host varchar(32) not null\n"+
		");";
		return str;
	}
	private void createDatabaseTables() throws Exception {
		
		java.util.Vector<Object> parameters = new java.util.Vector<Object>();
		try{
			this.executeUpdate(this.createConfigTableSQL(),parameters);
			this.executeUpdate(this.createUniqueConfigConstraint(), parameters);
			this.executeUpdate(this.createHistoryTableSQL(), parameters);
			parameters.add(new java.sql.Timestamp(new java.util.Date().getTime()));
			this.executeUpdate(this.initializedata(),parameters);
			SessionData session = new SessionData();
			session.setCurrentHost("localhost");
			session.setUserData(new UserData());
			session.getUserData().setUsername("root");
			ConfigManager root_user = new ConfigManager("/administration/users/root/password","password",TYPE.STRING,"The superuser password","This is the password that you need to gain complete access to the application.",session);
			
		}catch(Exception e){
			if(!e.getMessage().startsWith("object name already exists")){
				e.printStackTrace();
				throw e;
			}
		}
	}	
	private String createUniqueConfigConstraint(){
		String str = "alter table config add constraint uc_name UNIQUE (name,paren"+
		"t);";
		return str;
	}
	private String initializedata() {
		String str = "insert into config (ID,NAME,TYPE,TITLE,DESCRIPTION,CREATED_B"+
		"Y,CREATED_DATE,MODIFIER_HOST)\n"+
		"values \n"+
		"(0,'ROOT',0,'The Root Folder','This is the main folder of wh"+
		"ich all objects are children.',\n"+
		"'ROOT',?,'localhost');";
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
	public java.sql.Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
	}
}
