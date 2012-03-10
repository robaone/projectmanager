package com.robaone.dbase;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

public class RODataSourceFactory extends BasicDataSourceFactory {
	private static ROPasswordStoreInterface m_password_store;
	public RODataSourceFactory(ROPasswordStoreInterface store){
		RODataSourceFactory.m_password_store = store;
	}
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			@SuppressWarnings("rawtypes") Hashtable environment) throws Exception {
		Object o = super.getObjectInstance(obj, name, nameCtx, environment);
		if(o != null){
			BasicDataSource ds = (BasicDataSource) o;
			if (ds.getPassword() != null && ds.getPassword().length() > 0){
				String pwd = RODataSourceFactory.m_password_store.getPassword(ds.getPassword());
				ds.setPassword(pwd);
			}
			return ds;
		} else {
			return null;
		}
	}

}
