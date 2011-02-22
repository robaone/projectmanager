package com.robaone.gwt.projectmanager.server.util;

import java.io.FileWriter;
import java.sql.SQLException;

import com.robaone.gwt.projectmanager.server.business.ProjectDatabase;

public class CreateJdoObjects {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("driver_choice","2");
			@SuppressWarnings("unused")
			ProjectDatabase database = new ProjectDatabase();
			com.robaone.jdo.RO_JDO_Generator.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
