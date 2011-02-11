package com.robaone.data;

import com.sohvac.data.Dbo_users_jdo;
import com.sohvac.data.Dbo_users_jdoManager;

public class UserManagement {

	public static boolean emailExists(String value) {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_users_jdoManager man = new Dbo_users_jdoManager(con);
			ps = man.prepareStatement(Dbo_users_jdo.EMAIL + " = ?");
			ps.setString(1, value);
			rs = ps.executeQuery();
			if(rs.next()){
				// A match has been found
				return true;
			}else{
				// No match found
				return false;
			}
		}catch(Exception e){
		  e.printStackTrace();
		}finally{
		  try{rs.close();}catch(Exception e){}
		  try{ps.close();}catch(Exception e){}
		  try{con.close();}catch(Exception e){}
		}
		return false;
	}

}
