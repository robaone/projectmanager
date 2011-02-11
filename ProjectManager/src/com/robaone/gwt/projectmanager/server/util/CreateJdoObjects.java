package com.robaone.gwt.projectmanager.server.util;

import java.io.FileWriter;
import java.sql.SQLException;

import com.robaone.gwt.projectmanager.server.business.TestDatabase;

public class CreateJdoObjects {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestDatabase database = new TestDatabase();
			com.robaone.jdo.RO_JDO_Generator.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
