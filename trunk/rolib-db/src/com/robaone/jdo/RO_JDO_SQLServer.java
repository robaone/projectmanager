package com.robaone.jdo;

import java.sql.Connection;
import java.util.Vector;

import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.dbase.hierarchial.ConfigManager;

public class RO_JDO_SQLServer<Type> implements RO_JDO_IdentityManager<Type> {

	@Override
	public Type getIdentity(String table,final Connection con) throws Exception {
		final Vector<Type> retval = new Vector<Type>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			protected void run() throws Exception {
				String str = "select @@IDENTITY";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					retval.add((Type)this.getResultSet().getObject(1));
				}else{
					throw new Exception("Unable to retrieve @@Identity from database");
				}
			}
			
		};
		ConfigManager.runConnectionBlock(block, new HDBConnectionManager(){

			@Override
			public Connection getConnection() throws Exception {
				return con;
			}

			@Override
			public void closeConnection(Connection m_con) throws Exception {
				
			}

			
		});
		return retval.get(0);
	}

}
