package com.robaone.gwt.projectmanager.server.util;

public class CreateJdoObjects {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ProjectDatabase database = new ProjectDatabase();
			com.robaone.jdo.RO_JDO_Generator.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}