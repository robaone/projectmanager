package com.robaone.jdo;

import java.sql.Connection;

public interface RO_JDO_IdentityManager<T> {

	public T getIdentity(String table,Connection con) throws Exception;
}
