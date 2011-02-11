package com.robaone.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	public static final String SOHVAC = "MicrosoftSQL";
	public static final String WORDPRESS = "MySQL";
	public static Connection getConnection(String name) throws NamingException, SQLException{
		// Get DataSource
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/"+name);
		// Get Connection and Statement
		Connection c = ds.getConnection();
		return c;
	}
	public static String getPagedQuery(String orderby, String fields,
			String table, int page, int limit) {
		int start = ((page-1) * limit)+1;
		int end = page*limit;
		String str = "select * from\n"+
		"(select row_number() over(order by "+orderby+") as rownum,"+fields+" fro"+
		"m "+table+")\n"+
		"a\n"+
		"where rownum between "+start+" and "+end+";";
		return str;
	}
	public static String encrypt(String password) {
		String retval;
		try {
			retval = Database.byteArrayToHexString(Database.computeHash(password));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return retval;
	}
	private static byte[] computeHash(String x)   
	  throws Exception  
	  {
	     java.security.MessageDigest d =null;
	     d = java.security.MessageDigest.getInstance("SHA-1");
	     d.reset();
	     d.update(x.getBytes());
	     return  d.digest();
	  }
	  
	  private static String byteArrayToHexString(byte[] b){
	     StringBuffer sb = new StringBuffer(b.length * 2);
	     for (int i = 0; i < b.length; i++){
	       int v = b[i] & 0xff;
	       if (v < 16) {
	         sb.append('0');
	       }
	       sb.append(Integer.toHexString(v));
	     }
	     return sb.toString().toUpperCase();
	  }
}
