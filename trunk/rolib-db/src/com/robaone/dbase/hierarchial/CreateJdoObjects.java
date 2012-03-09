package com.robaone.dbase.hierarchial;

public class CreateJdoObjects {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ProjectDatabase database = new ProjectDatabase();
			com.robaone.jdo.RO_JDO_Generator.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}