package com.robaone.gwt.projectmanager.server.business;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class TestDatabase {
	private java.sql.Connection m_con;
	public TestDatabase() throws SQLException{
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
		" id numeric(18,0) primary key not null,\n"+
		" name varchar(128) not null,\n"+
		" parent numeric(18,0),\n"+
		" type varchar(32) not null,\n"+
		" title varchar(128) not null,\n"+
		" description clob,\n"+
		" value clob\n"+
		");";
		java.util.Vector<Object> parameters = new java.util.Vector<Object>();
		this.executeUpdate(str,parameters);
	}
	private int executeUpdate(String str, Vector<Object> parameters) throws Exception{
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
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
