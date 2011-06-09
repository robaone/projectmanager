package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConfigStruct;
import com.robaone.dbase.hierarchial.Config_jdo;
import com.robaone.dbase.hierarchial.Config_jdoManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.dbase.hierarchial.HDBSessionData;
import com.robaone.dbase.hierarchial.types.ConfigType;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.data.DBData;

public class TagManager {
	private DataServiceImpl parent;
	public TagManager(DataServiceImpl p){
		this.parent = p;
	}
	public BigDecimal getTagIdForName(String tagname) throws Exception {
		BigDecimal retval = null;
		String path;
		String[] params = new String[1];
		params[0] = tagname;
		path = ConfigManager.path(this, params);
		ConfigManager cfg = new DBData().setdefault(new ConfigStruct(path,tagname,ConfigType.STRING,"Tag Name","This is a tag name that is assigned a specifig id number"),this.getHDBSessionData());
		retval = cfg.getId();
		return retval;
	}
	public void updateTagName(BigDecimal id,String newname) throws Exception {
		ConfigManager cfg = new DBData().findConfig(id);
		cfg.setValue(newname, this.getHDBSessionData());
	}
	public BigDecimal updateTagName(String oldname,String newname) throws Exception {
		String path;
		String[] params = new String[1];
		params[0] = oldname;
		path = ConfigManager.path(this, params);
		ConfigManager cfg = new DBData().findConfig(path);
		cfg.setValue(newname, this.getHDBSessionData());
		return cfg.getId();
	}
	public HDBSessionData getHDBSessionData(){
		HDBSessionData session = new HDBSessionData(this.parent.getSessionData().getUserData().getUsername(),this.parent.getSessionData().getCurrentHost());
		return session;
	}
	public String getTagNameforId(String id) throws Exception {
		ConfigManager cfg = new DBData().findConfig(new BigDecimal(id));
		return cfg.getName();
	}

}
