package com.robaone.dbase.hierarchial;

import java.sql.Connection;
import java.sql.DriverManager;

import org.json.JSONObject;

import com.robaone.dbase.HDBConnectionManager;
import com.robaone.dbase.HDBSessionData;
import com.robaone.dbase.hierarchial.types.ConfigType;


public class TestJdoObjects {

	/**
	 * @param args
	 * 
	 * -Ddb_driver=org.hsqldb.jdbc.JDBCDriver
	 * -Ddb_url=jdbc:hsqldb:mem:mymemdb
	 * -Ddb_username=SA
	 * -Ddb_password=
	 */
	public static void main(String[] args) {
		try{
			HDBSessionData session = new HDBSessionData("test_user","localhost");
			class Data extends ConfigManager {
				@Override
				protected HDBConnectionManager getConnectionManager() {
					HDBConnectionManager cman = new HDBConnectionManager(){

						@Override
						public Connection getConnection() throws Exception {
							String driver,url,username,password;
							driver = "org.hsqldb.jdbc.JDBCDriver";
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

						
					};
					return cman;
				}

				@Override
				public String getTableName() {
					return "MyData";
				}

				@Override
				public ConfigManager newInstance() {
					return new Data();
				}
				
			}
			for(int i = 0 ; i < 500;i++){
				
				ConfigManager object1 = new Data();//new ConfigManager("/test","testobject1",ConfigType.STRING,"My first string","This is a test string",session);
				ConfigStruct cfg = new ConfigStruct();
				cfg.setPath("/test");
				cfg.setDefault_str_val("testobject1");
				cfg.setType(ConfigType.STRING);
				cfg.setTitle("My first string");
				cfg.setDescription("This is a test string");
				object1.setdefault(cfg , session);
				printObjectName(object1,"testobject1");
				object1.setValue("testobject1 has been modified at "+new java.util.Date(),session);
				System.out.println("object1.value = "+object1.getString());
				ConfigManager textobject = new Data().setdefault(new ConfigStruct("/objects/text","textobject",ConfigType.TEXT,"This is a text object","This text object is to test retrieval, editing and history"), session); //new ConfigManager("/objects/text","textobject",ConfigType.TEXT,"This is a text object","This text object is to test retrieval, editing and history",session);
				printObjectName(textobject,"textobject");
				textobject.setValue("text that will be modified again", session);
				printObjectName(textobject,"textobject");
				ConfigManager boolobject = new Data().setdefault(new ConfigStruct("/objects/bool",new Boolean(true),ConfigType.BOOLEAN,"This is a boolean object","This boolean object is to test the retrieval, editing and history"), session);// new ConfigManager("/objects/bool",true,"This is a boolean object","This boolean object is to test the retrieval, editing and history",session);
				printObjectName(boolobject,"boolobject");
				boolobject.setValue(false, session);
				ConfigManager intobject = new Data().setdefault(new ConfigStruct("/objects/int","1",ConfigType.INT,"Int object","This is an int object"), session);// new ConfigManager("/objects/int",1,"This is an int object","Int object",session);
				//intobject.supressHistory();
				printObjectName(intobject,"intobject");
				intobject.setValue((intobject.getInt().intValue()+1), session);
				printObjectName(intobject,"intobject");
				ConfigManager doubleobject = new Data().setdefault(new ConfigStruct("/objects/double",2.75,"This is a double","Double object"),session);
				doubleobject.supressHistory();
				printObjectName(doubleobject,"doubleobject");
				doubleobject.setValue(doubleobject.getDouble().doubleValue()+0.24, session);
				printObjectName(doubleobject,"doubleobject");
				ConfigManager timeobject = new Data().setdefault(new ConfigStruct("/objects/time",new java.sql.Timestamp(new java.util.Date().getTime()),ConfigType.DATETIME,"Time","Time tester"),session);
				timeobject.supressHistory();
				printObjectName(timeobject,"timeobject");
				timeobject.setValue(new java.sql.Timestamp(new java.util.Date().getTime()), session);
				ConfigManager byteobject = new Data().setdefault(new ConfigStruct("/object/bytes",new String("This is a set of bytes").getBytes(),"text/plain","Bytes","testing bytes"),session);
				byteobject.supressHistory();
				printObjectName(byteobject,"byteobject");
				byteobject.setValue(new String("This is a set of bytes too.").getBytes(), session);
				byteobject.setContentType("text/plain",session);
				JSONObject jo = new JSONObject();
				jo.put("name", "test");
				jo.put("bool", true);
				jo.put("number", 23);
				ConfigManager json = new Data().setdefault(new ConfigStruct("/objects/json",jo,"json","test"),session);
				jo = json.getJSON();
				jo.put("name", "changed");
				json.setValue(jo, session);
				printObjectName(json,"json");
				ConfigManager counter = new Data().setdefault(new ConfigStruct("/objects/counter",i,"counter","How many times the loop has been run"),session);
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
			if(object1.getType() == ConfigType.STRING || object1.getType() == ConfigType.TEXT){
				System.out.println(name+".value = "+object1.getString());
			}else if(object1.getType() == ConfigType.JSON){
				System.out.println(name+".value = "+object1.getJSON().toString());
			}else if(object1.getType()==ConfigType.BOOLEAN){
				System.out.println(name+".value = "+object1.getBoolean());
			}else if(object1.getType()==ConfigType.BINARY){
				System.out.println(name+".value.length = "+(object1.getBinary() == null ? "null" : (object1.getBinary().length+"")));
			}else if(object1.getType()==ConfigType.INT){
				System.out.println(name+".value = "+(object1.getInt() == null ? "null" : (object1.getInt().intValue()+"")));
			}else if(object1.getType()==ConfigType.DOUBLE){
				System.out.println(name+".value = "+(object1.getDouble() == null ? "null" : object1.getDouble()+""));
			}else if(object1.getType()==ConfigType.DATETIME){
				System.out.println(name+".value = "+object1.getDateTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
