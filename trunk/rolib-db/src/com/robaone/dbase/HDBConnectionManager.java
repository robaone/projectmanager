package com.robaone.dbase;

import java.sql.Connection;

public interface HDBConnectionManager {

	Connection getConnection() throws Exception;

	void closeConnection(Connection m_con) throws Exception;

}
