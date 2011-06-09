package com.robaone.gwt.projectmanager.server.data;

import java.sql.Connection;
import java.sql.DriverManager;

import com.robaone.dbase.hierarchial.HDBConnectionManager;

public class MemoryDatabase implements HDBConnectionManager {

	@Override
	public Connection getConnection() throws Exception {
		String driver,url,username,password;
		driver = this.getDriver();
		url = "jdbc:hsqldb:mem:mymemdb";
		username = "SA";
		password = "";
		try {
			Class.forName(driver );
		} catch (Exception e) {
			System.err.println("ERROR: failed to load JDBC driver.");
			e.printStackTrace();
			throw e;
		}
		return DriverManager.getConnection(url, username, password);
	}

	@Override
	public void closeConnection(Connection m_con) throws Exception {
		m_con.close();
	}

	@Override
	public String getDriver() {
		return "org.hsqldb.jdbc.JDBCDriver";
	}

}
