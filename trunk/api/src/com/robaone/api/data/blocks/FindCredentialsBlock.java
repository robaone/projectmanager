package com.robaone.api.data.blocks;

import java.util.Vector;

import com.robaone.api.data.jdo.Credentials_jdo;
import com.robaone.api.data.jdo.Credentials_jdoManager;
import com.robaone.dbase.ConnectionBlock;

public class FindCredentialsBlock extends ConnectionBlock {

	private Vector<Credentials_jdo> retval;
	private String authenticator;
	private String authdata;

	public FindCredentialsBlock(Vector<Credentials_jdo> retval, String string,
			String token) {
		this.retval = retval;
		this.authenticator = string;
		this.authdata = token;
	}

	@Override
	public void run() throws Exception {
		/**
		 * find the credentials record
		 */
		Credentials_jdoManager man = new Credentials_jdoManager(this.getConnection());
		this.setPreparedStatement(man.prepareStatement(Credentials_jdo.AUTHENTICATOR + " = ? and "+Credentials_jdo.AUTHDATA + " = ?"));
		this.getPreparedStatement().setString(1, authenticator);
		this.getPreparedStatement().setString(2, authdata);
		this.setResultSet(this.getPreparedStatement().executeQuery());
		if(this.getResultSet().next()){
			Credentials_jdo record = man.bindCredentials(getResultSet());
			retval.add(record);
		}
	}

}
