/*
 * Created on Apr 8, 2010
 *
 */
package com.robaone.jdo;

import java.sql.Connection;

import com.robaone.util.INIFileReader;




/**
 * @author arobateau  http://www.robaone.com
 *
 */
public class Jdo_TestMain {

	public static void main(String[] args) throws Exception {
		Connection con = null;
		if(args.length < 1){
			throw new Exception("Not enough arguments");
		}
		INIFileReader ini = new INIFileReader(args[0]);
		String driver = ini.getValue("driver");
		String url = ini.getValue("url");
		String username = ini.getValue("username");
		String password = ini.getValue("password");
		con = com.robaone.dbase.DBManager.getConnection(driver, url, username, password);
		/*
		Profile_jdoManager man = new Profile_jdoManager(con);
		PreparedStatement ps = con.prepareStatement("select * from "+man.getTableName());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Profile_jdo record = man.bindProfile(rs);
			String json = man.toJSONObject(record).toString();
			System.out.println(json);
			JSONObject jo;
			jo = new JSONObject(json);
			jo.put("password", "hello world!");
			man.bindProfileJSON(record, jo);
			jo = new JSONObject(record);
			json = jo.toString();
			System.out.println(json);
			jo.put("idprofile", 19);
			man.bindProfileJSON(record, jo.toString());
			json = new JSONObject(record).toString();
			System.out.println(json);
			jo = man.toJSONObject(record);
			System.out.println(jo);
		}
		*/
	}
}
