package com.robaone.api.data.blocks;

import java.util.Vector;

import com.robaone.api.data.jdo.Roles_jdo;
import com.robaone.api.data.jdo.Roles_jdoManager;
import com.robaone.dbase.ConnectionBlock;

public class UserRolesBlock extends ConnectionBlock{
	private Vector<Roles_jdo> retval;
	private Integer iduser; 
	public UserRolesBlock(Vector<Roles_jdo> retval,Integer iduser){
		this.retval = retval;
		this.iduser = iduser;
	}
	@Override
	public void run() throws Exception {
		Roles_jdoManager man = new Roles_jdoManager(this.getConnection());
		this.setPreparedStatement(man.prepareStatement(Roles_jdo.IDUSER + " = ?"));
		this.getPreparedStatement().setInt(1, iduser);
		this.setResultSet(this.getPreparedStatement().executeQuery());
		while(this.getResultSet().next()){
			Roles_jdo role = man.bindRoles(this.getResultSet());
			retval.add(role);
		}
	}
	
};
