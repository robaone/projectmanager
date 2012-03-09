package com.robaone.dbase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ConnectionBlock {
	private java.sql.Connection con = null;
	private java.sql.PreparedStatement ps = null;
	private java.sql.ResultSet rs = null;
	private java.sql.CallableStatement call = null;
	private HDBConnectionManager m_cman = null;
	public ConnectionBlock(){}
	public void run(HDBConnectionManager cman) throws Exception{
		try{
			this.setConnectionManager(cman);
			this.run();
		}finally{
			this.close();
		}
	}
	protected abstract void run() throws Exception;
	protected java.sql.Connection getConnection(){
		return this.con;
	}
	protected void prepareStatement(String sql) throws SQLException{
		this.setPreparedStatement(this.getConnection().prepareStatement(sql));
	}
	protected PreparedStatement getPS(){
		return this.getPreparedStatement();
	}
	protected CallableStatement getCall(){
		return this.getCallableStatement();
	}
	protected void executeCall() throws SQLException{
		this.setResultSet(this.getCallableStatement().executeQuery());
	}
	protected void executeQuery() throws SQLException{
		this.setResultSet(this.getPreparedStatement().executeQuery());
	}
	protected int executeUpdate() throws SQLException {
		return this.getPreparedStatement().executeUpdate();
	}
	protected boolean next() throws SQLException{
		return this.getResultSet().next();
	}
	protected void setConnection(java.sql.Connection connection){
		this.con = connection;
	}
	protected HDBConnectionManager getConnectionManager(){
		return new HDBConnectionManager(){

			public Connection getConnection() throws Exception {
				return con;
			}

			public void closeConnection(Connection m_con) throws Exception {
				
			}

		};
	}
	protected void setConnectionManager(HDBConnectionManager cman){
		this.m_cman  = cman;
		try{this.con = this.m_cman.getConnection();}catch(Exception e){}
	}
	protected void setCallableStatement(java.sql.CallableStatement c){
		this.call = c;
	}
	protected java.sql.CallableStatement getCallableStatement(){
		return this.call;
	}
	protected java.sql.PreparedStatement getPreparedStatement(){
		return this.ps;
	}
	protected void setPreparedStatement(java.sql.PreparedStatement p){
		this.ps = p;
	}
	protected java.sql.ResultSet getResultSet(){
		return this.rs;
	}
	protected void setResultSet(java.sql.ResultSet r){
		this.rs = r;
	}
	protected void close(){
		try{rs.close();}catch(Exception e){}
		try{call.close();}catch(Exception e){}
		try{ps.close();}catch(Exception e){}
		if(m_cman != null){
			try{m_cman.closeConnection(con);}catch(Exception e){}
		}else{
			try{con.close();}catch(Exception e){}
		}
	}
}
