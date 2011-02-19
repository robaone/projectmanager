package com.robaone.gwt.projectmanager.server.util;

import com.robaone.gwt.projectmanager.client.UserData;
import com.robaone.gwt.projectmanager.server.ConfigManager;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.ConfigManager.TYPE;
import com.robaone.gwt.projectmanager.server.business.ProjectDatabase;

public class TestJdoObjects {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			System.setProperty("driver_choice","1");
			ProjectDatabase database = new ProjectDatabase();
			SessionData session = new SessionData();
			session.setCurrentHost("localhost");
			session.setUserData(new UserData());
			session.getUserData().setUsername("test_user");
			ConfigManager object1 = new ConfigManager("/test","testobject1",TYPE.STRING,"My first string","This is a test string",session);
			System.out.println("object1.name = "+object1.getName());
			System.out.println("object1.value = "+object1.getString());
			System.out.println(" - Modifying object1.value");
			object1.setValue("testobject1 has been modified");
			System.out.println("object1.value = "+object1.getString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
