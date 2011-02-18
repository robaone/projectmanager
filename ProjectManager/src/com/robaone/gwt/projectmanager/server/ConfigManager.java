package com.robaone.gwt.projectmanager.server;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.json.JSONObject;

import com.robaone.gwt.projectmanager.server.business.ProjectDatabase;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdo;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdoManager;

public class ConfigManager {
	public static enum TYPE {STRING,INT,DOUBLE,BOOLEAN,FOLDER, DATETIME, TEXT, BINARY, JSON}
	private String m_default_value;
	private String m_title;
	private TYPE m_type;
	private String m_description;
	private Config_jdo m_cfg;
	private byte[] m_bin_value;
	private Timestamp m_date_value;
	public ConfigManager(String path,String default_value,TYPE type,String title,String description){
		try {
			this.createConfigValue(path,default_value,type,title,description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createConfigValue(String path, String default_value,
			TYPE type, String title, String description) throws Exception {
		java.sql.Connection con = null;
		this.m_default_value = default_value;
		this.m_title = title;
		this.m_type = type;
		this.m_description = description;
		if(title == null || title.trim().length() == 0){
			throw new Exception("Variable description title needed");
		}
		if(description == null || description.trim().length() == 0){
			throw new Exception("Variable description needed");
		}
		try{
			ProjectDatabase db = DataServiceImpl.getDatabase();
			con = db.getConnection();
			Config_jdo record = this.findValue(path,true);
			this.m_cfg = record;
			if(record == null){
				throw new Exception("Cannot create config value");
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{con.close();}catch(Exception e){}
		}
	}
	private Config_jdo findValue(String path, boolean create) throws Exception {
		String[] tokens = path.split("/");
		Config_jdo parent = null;
		for(int i = 0;i<tokens.length;i++){
			if(i != tokens.length-1){
				String folder = tokens[i];
				Config_jdo folder_record = this.findFolder(parent,folder,create);
				parent = folder_record;
				if(parent == null){
					return null;
				}
			}else{
				Config_jdo record = null;
				java.sql.Connection con = null;
				java.sql.PreparedStatement ps = null;
				java.sql.ResultSet rs = null;
				try{
					ProjectDatabase db = DataServiceImpl.getDatabase();
					con = db.getConnection();
					Config_jdoManager man = new Config_jdoManager(con);
					ps = man.prepareStatement(Config_jdo.PARENT + " = ? and "+Config_jdo.NAME + " = ? and "+Config_jdo.TYPE +" != ?");
					ps.setBigDecimal(1, parent == null ? null : parent.getId());
					ps.setString(2, tokens[i]);
					ps.setString(3, TYPE.FOLDER.toString());
					rs = ps.executeQuery();
					if(rs.next()){
						record = Config_jdoManager.bindConfig(rs);
						return record;
					}else if(create){
						record = man.newConfig();
						record.setName(tokens[i]);
						record.setParent(parent == null ? null : parent.getId());
						record.setType(this.getDBType(m_type));
						this.setDBValue(record);
						record.setTitle(m_title);
						record.setDescription(m_description);
						man.save(record);
						return record;
					}else{
						return null;
					}
				}catch(Exception e){
					throw e;
				}
			}
		}
		return null;
	}
	private void setDBValue(Config_jdo record) throws Exception {
		if(this.m_type.equals(TYPE.BINARY)){
			record.setBinary_value(this.m_bin_value);
		}else if(this.m_type.equals(TYPE.BOOLEAN)){
			record.setBool_value(new Integer(this.m_default_value));
		}else if(this.m_type.equals(TYPE.DATETIME)){
			record.setDate_value(this.m_date_value);
		}else if(this.m_type.equals(TYPE.DOUBLE)){
			record.setNumber_value(new BigDecimal(this.m_default_value));
		}else if(this.m_type.equals(TYPE.INT)){
			record.setNumber_value(new BigDecimal(this.m_default_value));
		}else if(this.m_type.equals(TYPE.STRING)){
			record.setString_value(this.m_default_value);
		}else if(this.m_type.equals(TYPE.TEXT)){
			record.setText_value(this.m_default_value);
		}else if(this.m_type.equals(TYPE.JSON)){
			record.setText_value(this.m_default_value);
		}else{
			throw new Exception("Invalid type");
		}
	}
	private Integer getDBType(TYPE mType) {
		if(mType.equals(TYPE.BOOLEAN)){
			return 1;
		}else if(mType.equals(TYPE.DOUBLE)){
			return 2;
		}else if(mType.equals(TYPE.FOLDER)){
			return 0;
		}else if(mType.equals(TYPE.INT)){
			return 3;
		}else if(mType.equals(TYPE.STRING)){
			return 4;
		}else if(mType.equals(TYPE.DATETIME)){
			return 5;
		}else if(mType.equals(TYPE.TEXT)){
			return 6;
		}else if(mType.equals(TYPE.BINARY)){
			return 7;
		}else if(mType.equals(TYPE.JSON)){
			return 8;
		}
		return null;
	}
	private Config_jdo findFolder(Config_jdo object, String folder, boolean create) throws Exception {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			con = DataServiceImpl.getDatabase().getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			if(object == null){
				ps = man.prepareStatement(Config_jdo.NAME + " = ? and "+ Config_jdo.PARENT +" is null and "+Config_jdo.TYPE + " = ?");
				ps.setString(2, TYPE.FOLDER.toString());
			}else{
				ps = man.prepareStatement(Config_jdo.NAME + " = ? and "+ Config_jdo.PARENT +" = ? and "+Config_jdo.TYPE + " = ?");
				ps.setBigDecimal(2, object.getId());
				ps.setString(3, TYPE.FOLDER.toString());
			}
			ps.setString(1, folder);
			rs = ps.executeQuery();
			if(rs.next()){
				Config_jdo record = Config_jdoManager.bindConfig(rs);
				return record;
			}else if(create){
				Config_jdo record = man.newConfig();
				record.setParent(object.getId());
				record.setType(this.getDBType(TYPE.FOLDER));
				record.setName(folder);
				man.save(record);
				return record;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
	}
	public ConfigManager(String path) throws Exception {
		ConfigManager cfg = new ConfigManager();
		try{
			cfg.m_cfg = cfg.findValue(path, false);
		}catch(Exception e){
			throw new Exception("Config Value not found");
		}
	}
	public ConfigManager(BigDecimal id) throws Exception {
		java.sql.Connection con = null;
		try{
			con = DataServiceImpl.getDatabase().getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			Config_jdo record = man.getConfig(id);
			if(record == null){
				throw new Exception("Config Value not found");
			}
			this.m_cfg = record;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{con.close();}catch(Exception e){}
		}
	}
	private ConfigManager(){

	}
	public String getString() throws Exception{
		if(this.getType().equals(TYPE.STRING)){
			return this.m_cfg.getString_value();
		}else if(this.getType().equals(TYPE.TEXT)){
			return this.m_cfg.getText_value();
		}else{
			throw new Exception("Invalid type");
		}
	}
	private TYPE getType() throws Exception {
		switch (this.m_cfg.getType().intValue()){
		case 0:
			return TYPE.FOLDER;
		case 1:
			return TYPE.BOOLEAN;
		case 2:
			return TYPE.DOUBLE;
		case 3:
			return TYPE.INT;
		case 4:
			return TYPE.STRING;
		case 5:
			return TYPE.DATETIME;
		case 6:
			return TYPE.TEXT;
		case 7:
			return TYPE.BINARY;
		case 8:
			return TYPE.JSON;
		default:
			throw new Exception("Invalid type");
		}
		
	}
	public Integer getInt() throws Exception {
		if(this.getType().equals(TYPE.INT)){
			return this.m_cfg.getNumber_value().intValue();
		}else{
			throw new Exception("Invalid type");
		}
	}
	public Double getDouble() throws Exception {
		if(this.getType().equals(TYPE.DOUBLE)){
			return this.m_cfg.getNumber_value().doubleValue();
		}else{
			throw new Exception("Invalid type");
		}
	}
	public Boolean getBoolean() throws Exception {
		if(this.getType().equals(TYPE.BOOLEAN)){
			if(this.m_cfg.getBool_value() == null){
				return null;
			}else{
				return this.m_cfg.getBool_value() == 1 ? true : false;
			}
		}else{
			throw new Exception("Invalid type");
		}
	}
	public JSONObject getJSON() throws Exception {
		if(this.getType().equals(TYPE.JSON)){
			JSONObject retval = new JSONObject(this.m_cfg.getText_value());
			return retval;
		}else{
			throw new Exception("Invalid type");
		}
	}
	public BigDecimal getFolderID(){
		return this.m_cfg.getParent();
	}
	public static ConfigManager findConfig(String path, String variable) {
		ConfigManager cfg = new ConfigManager();
		Config_jdo val = null;
		try{
			val = cfg.findValue(path + "/" + variable, false);
			cfg.m_cfg = val;
			return cfg;
		}catch(Exception e){

		}
		return null;
	}
	public static ConfigManager findConfig(String path) {
		ConfigManager cfg = new ConfigManager();
		try{
			cfg.m_cfg = cfg.findValue(path, false);
			return cfg;
		}catch(Exception e){
			
		}
		return null;
	}
	public void setValue(java.sql.Timestamp val) throws Exception {
		if(this.getType().equals(TYPE.DATETIME)){
			this.m_cfg.setDate_value(val);
			java.sql.Connection con = null;
			try{
				con = DataServiceImpl.getDatabase().getConnection();
				Config_jdoManager man = new Config_jdoManager(con);
				man.save(this.m_cfg);
			}catch(Exception e){
				throw e;
			}finally{
				try{con.close();}catch(Exception e){}
			}
		}else{
			throw new Exception("Invalid type");
		}
	}
}