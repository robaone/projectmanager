package com.robaone.api.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.dbase.hierarchial.HDBConnectionManager;

public class DatabaseImpl{
	public static final String INSUFFICIENT_RIGHTS_MSG = "You do not have access rights to perform this action";
	public static final String NOT_IMPLEMENTED_MSG = "Not Yet Implemented";
	public DatabaseImpl(){
		
	}
	public java.sql.Connection getConnection() throws Exception {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		String env = AppDatabase.getProperty("env");
		DataSource ds;
		if(env != null && env.equals("dev"))
			ds = (DataSource)envContext.lookup("jdbc/mydatabase_dev");
		else
			ds = (DataSource)envContext.lookup("jdbc/mydatabase");
		Connection conn = ds.getConnection();
		return conn;
	}

	public void writeLog(String string) {
		AppDatabase.writeLog(string);
	}

	public BigDecimal getNextID(final String string) throws Exception {
		final Vector<BigDecimal> retval = new Vector<BigDecimal>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				String str = "update next_index set next_index = next_index + 1 where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, string);
				int updated = this.getPreparedStatement().executeUpdate();
				this.getPreparedStatement().close();
				if(updated == 0){
					str = "insert into next_index (name,next_index) values (?,0)";
					this.setPreparedStatement(this.getConnection().prepareStatement(str));
					this.getPreparedStatement().setString(1, string);
					this.getPreparedStatement().executeUpdate();
					this.getPreparedStatement().close();
				}
				str = "select next_index from next_index where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, string);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					retval.add(this.getResultSet().getBigDecimal(1));
				}
			}

		};
		ConfigManager.runConnectionBlock(block, this.getConnectionManager());
		return retval.size() > 0? retval.get(0):null;
	}

	public HDBConnectionManager getConnectionManager() {
		return new HDBConnectionManager(){

			@Override
			public Connection getConnection() throws Exception {
				return DatabaseImpl.this.getConnection();
			}

			@Override
			public void closeConnection(Connection m_con) throws Exception {
				m_con.close();
			}

			@Override
			public String getDriver() {
				return "org.hsqldb.";
			}

		};
	}

}
