package com.robaone.gwt.projectmanager.server.data;


import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.HDBConnectionManager;

public class DBData extends ConfigManager {

	@Override
	protected HDBConnectionManager getConnectionManager() {
		return new MemoryDatabase();
	}

	@Override
	protected String getTableName() {
		return "CONFIG";
	}

	@Override
	public ConfigManager newInstance() {
		return new DBData();
	}

}
