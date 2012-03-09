package com.robaone.dbase.hierarchial;

import java.sql.Connection;
import java.util.Vector;

import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.jdo.RO_JDO_IdentityManager;

public class ConfigIDManager implements RO_JDO_IdentityManager<Integer> {
	@Override
	public Integer getIdentity(final String table,final Connection con) throws Exception {
		final Vector<Integer> retval = new Vector<Integer>();
		new ConnectionBlock(){

			@Override
			protected void run() throws Exception {
				String str = "exec get_next_id "+table;
				this.setCallableStatement(this.getConnection().prepareCall(str));
				this.setResultSet(this.getCall().executeQuery());
				if(next()){
					Integer id = this.getResultSet().getInt(1);
					retval.add(id);
				}
			}
			
		}.run(new HDBConnectionManager(){

			@Override
			public Connection getConnection() throws Exception {
				return con;
			}

			@Override
			public void closeConnection(Connection m_con) throws Exception {
				
			}
			
		});
		return retval.size() > 0 ? retval.get(0) : null;
	}

}
