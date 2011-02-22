package com.robaone.gwt.projectmanager.server.util;

import org.json.JSONObject;

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
			System.setProperty("driver_choice","2");
			@SuppressWarnings("unused")
			ProjectDatabase database = new ProjectDatabase();
			SessionData session = new SessionData();
			session.setCurrentHost("localhost");
			session.setUserData(new UserData());
			session.getUserData().setUsername("test_user");
			for(int i = 0 ; i < 500;i++){
				ConfigManager object1 = new ConfigManager("/test","testobject1",TYPE.STRING,"My first string","This is a test string",session);
				printObjectName(object1,"testobject1");
				object1.setValue("testobject1 has been modified at "+new java.util.Date(),session);
				System.out.println("object1.value = "+object1.getString());
				ConfigManager textobject = new ConfigManager("/objects/text","textobject",TYPE.TEXT,"This is a text object","This text object is to test retrieval, editing and history",session);
				printObjectName(textobject,"textobject");
				textobject.setValue("text that will be modified again", session);
				printObjectName(textobject,"textobject");
				ConfigManager boolobject = new ConfigManager("/objects/bool",true,"This is a boolean object","This boolean object is to test the retrieval, editing and history",session);
				printObjectName(boolobject,"boolobject");
				boolobject.setValue(false, session);
				ConfigManager intobject = new ConfigManager("/objects/int",1,"This is an int object","Int object",session);
				//intobject.supressHistory();
				printObjectName(intobject,"intobject");
				intobject.setValue(intobject.getInt()+1, session);
				printObjectName(intobject,"intobject");
				ConfigManager doubleobject = new ConfigManager("/objects/double",2.75,"This is a double","Double object",session);
				doubleobject.supressHistory();
				printObjectName(doubleobject,"doubleobject");
				doubleobject.setValue(doubleobject.getDouble().doubleValue()+0.24, session);
				printObjectName(doubleobject,"doubleobject");
				ConfigManager timeobject = new ConfigManager("/objects/time",new java.sql.Timestamp(new java.util.Date().getTime()),"Time","Time tester",session);
				timeobject.supressHistory();
				printObjectName(timeobject,"timeobject");
				timeobject.setValue(new java.sql.Timestamp(new java.util.Date().getTime()), session);
				ConfigManager byteobject = new ConfigManager("/object/bytes",new String("This is a set of bytes").getBytes(),"text/plain","Bytes","testing bytes",session);
				byteobject.supressHistory();
				printObjectName(byteobject,"byteobject");
				byteobject.setValue(new String("This is a set of bytes too.").getBytes(), session);
				byteobject.setContentType("text/plain",session);
				JSONObject jo = new JSONObject();
				jo.put("name", "test");
				jo.put("bool", true);
				jo.put("number", 23);
				ConfigManager json = new ConfigManager("/objects/json",jo,"json","test",session);
				ConfigManager counter = new ConfigManager("/objects/counter",i,"counter","How many times the loop has been run",session);
				counter.supressHistory();
				counter.setValue(i, session);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void printObjectName(ConfigManager object1,String name) {
		System.out.println(name+".name = "+object1.getName());
		printObjectValue(object1,name);
	}
	private static void printObjectValue(ConfigManager object1,String name) {
		try {
			if(object1.getType().equals(TYPE.STRING) || object1.getType().equals(TYPE.TEXT) || object1.getType().equals(TYPE.JSON)){
				System.out.println(name+".value = "+object1.getString());
			}else if(object1.getType().equals(TYPE.BOOLEAN)){
				System.out.println(name+".value = "+object1.getBoolean());
			}else if(object1.getType().equals(TYPE.BINARY)){
				System.out.println(name+".value.length = "+(object1.getBinary() == null ? "null" : object1.getBinary().length));
			}else if(object1.getType().equals(TYPE.INT)){
				System.out.println(name+".value = "+(object1.getInt() == null ? "null" : object1.getInt().intValue()));
			}else if(object1.getType().equals(TYPE.DOUBLE)){
				System.out.println(name+".value = "+(object1.getDouble() == null ? "null" : object1.getDouble()));
			}else if(object1.getType().equals(TYPE.DATETIME)){
				System.out.println(name+".value = "+object1.getDateTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
