package com.robaone.gwt.projectmanager.server.business;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

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
	private void createDatabaseTables() throws Exception {
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
		java.util.Vector<Object> parameters = new java.util.Vector<Object>();
		this.executeUpdate(str,parameters);
		
		str = "create table history (\n"+
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
		
		this.executeUpdate(str, parameters);
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
			if(!e.getMessage().startsWith("object name already exists")){
				e.printStackTrace();
				throw e;
			}
		}finally{
			try{ps.close();}catch(Exception e){}
		}
		return updated;
	}
	public java.sql.Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
	}
}
