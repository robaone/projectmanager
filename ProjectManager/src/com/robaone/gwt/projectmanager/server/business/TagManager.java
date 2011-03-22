package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.HDBSessionData;
import com.robaone.dbase.hierarchial.types.ConfigType;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;

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
		ConfigManager cfg = new ConfigManager(path,tagname,ConfigType.STRING,"Tag Name","This is a tag name that is assigned a specifig id number",this.getHDBSessionData());
		retval = cfg.getId();
		return retval;
	}
	public void updateTagName(BigDecimal id,String newname) throws Exception {
		ConfigManager cfg = new ConfigManager(id);
		cfg.setValue(newname, this.getHDBSessionData());
	}
	public BigDecimal updateTagName(String oldname,String newname) throws Exception {
		String path;
		String[] params = new String[1];
		params[0] = oldname;
		path = ConfigManager.path(this, params);
		ConfigManager cfg = ConfigManager.findConfig(path);
		cfg.setValue(newname, this.getHDBSessionData());
		return cfg.getId();
	}
	public HDBSessionData getHDBSessionData(){
		HDBSessionData session = new HDBSessionData(this.parent.getSessionData().getUserData().getUsername(),this.parent.getSessionData().getCurrentHost());
		return session;
	}

}
