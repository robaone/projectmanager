package com.robaone.gwt.projectmanager.server;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONObject;

import com.robaone.gwt.projectmanager.server.business.ProjectDatabase;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdo;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdoManager;
import com.robaone.gwt.projectmanager.server.jdo.History_jdo;
import com.robaone.gwt.projectmanager.server.jdo.History_jdoManager;

public class ConfigManager {
	public static enum TYPE {STRING,INT,DOUBLE,BOOLEAN,FOLDER, DATETIME, TEXT, BINARY, JSON}
	private String m_default_value;
	private String m_title;
	private TYPE m_type;
	private String m_description;
	private Config_jdo m_cfg;
	private byte[] m_bin_value;
	private Timestamp m_date_value;
	private SessionData m_userdata;
	private String m_content_type;
	public ConfigManager(String path,String default_value,TYPE type,String title,String description,SessionData session){
		try {
			this.m_userdata = session;
			this.m_default_value = default_value;
			this.createConfigValue(path,type,title,description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ConfigManager(String path,java.sql.Timestamp default_value,String title,String description,SessionData session){
		try {
			this.m_userdata = session;
			this.m_date_value = default_value;
			this.createConfigValue(path, TYPE.DATETIME, title, description);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ConfigManager(String path,byte[] default_value,String content_type,String title,String description,SessionData session){
		try{
			this.m_userdata = session;
			this.m_bin_value = default_value;
			this.m_content_type = content_type;
			this.createConfigValue(path, TYPE.BINARY, title, description);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ConfigManager(String path,int default_value,String title,String description,SessionData session){
		try{
			this.m_userdata = session;
			this.m_default_value = default_value+"";
			this.createConfigValue(path, TYPE.INT, title, description);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ConfigManager(String path,boolean default_value,String title,String description,SessionData session){
		try{
			this.m_userdata = session;
			this.m_default_value = default_value ? "1" : "0";
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private ConfigManager(String path,JSONObject default_value,String title,String description,SessionData session){
		try{
			this.m_userdata = session;
			this.m_default_value = default_value.toString();
			this.createConfigValue(path, TYPE.JSON, title, description);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void createConfigValue(String path, TYPE type, String title, String description) throws Exception {
		java.sql.Connection con = null;
		this.m_title = title;
		this.m_type = type;
		this.m_description = description;
		if(path != null && path.startsWith("/")){
			path = path.substring(1);
		}
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
				if(folder.length() == 0){
					continue;
				}
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
					if(parent == null){
						ps = man.prepareStatement(Config_jdo.PARENT + " is null and "+Config_jdo.NAME + " = ? and "+Config_jdo.TYPE +" != ?");
						ps.setString(1, tokens[i]);
						ps.setInt(2, this.getDBType(TYPE.FOLDER));
					}else{
						ps = man.prepareStatement(Config_jdo.PARENT + " = ? and "+Config_jdo.NAME + " = ? and "+Config_jdo.TYPE +" != ?");
						ps.setBigDecimal(1, parent.getId());
						ps.setString(2, tokens[i]);
						ps.setInt(3,this.getDBType(TYPE.FOLDER));
					}
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
						record.setCreated_by(this.m_userdata.getUserData().getUsername());
						record.setCreated_date(new java.sql.Timestamp(new java.util.Date().getTime()));
						record.setModifier_host(this.m_userdata.getCurrentHost());
						record.setModified_date(new java.sql.Timestamp(new java.util.Date().getTime()));
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
			record.setContent_type(this.m_content_type);
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
				ps.setInt(2, this.getDBType(TYPE.FOLDER));
			}else{
				ps = man.prepareStatement(Config_jdo.NAME + " = ? and "+ Config_jdo.PARENT +" = ? and "+Config_jdo.TYPE + " = ?");
				ps.setBigDecimal(2, object.getId());
				ps.setInt(3, this.getDBType(TYPE.FOLDER));
			}
			ps.setString(1, folder);
			rs = ps.executeQuery();
			if(rs.next()){
				Config_jdo record = Config_jdoManager.bindConfig(rs);
				return record;
			}else if(create){
				Config_jdo record = man.newConfig();
				record.setParent(object == null ? null : object.getId());
				record.setType(this.getDBType(TYPE.FOLDER));
				record.setName(folder);
				record.setTitle(record.getName() + " folder");
				record.setCreated_by(this.m_userdata.getUserData().getUsername());
				record.setCreated_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				record.setModifier_host(this.m_userdata.getCurrentHost());
				record.setModified_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				record.setModified_by(this.m_userdata.getUserData().getUsername());
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
	public byte[] getBinary() throws Exception{
		if(this.getType().equals(TYPE.BINARY)){
			return this.m_cfg.getBinary_value();
		}else{
			throw new Exception("Invalid type");
		}
	}
	public String getContentType() {
		return this.m_cfg.getContent_type();
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
		if(this.m_cfg == null){
			return this.m_type;
		}else{
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
	public void setValue(byte[] val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.BINARY)){
			this.m_cfg.setBinary_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(java.sql.Timestamp val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.DATETIME)){
			this.m_cfg.setDate_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(int val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.INT)){
			this.m_cfg.setNumber_value(new BigDecimal(val));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(float val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.DOUBLE)){
			this.m_cfg.setNumber_value(new BigDecimal(val));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(double val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.DOUBLE)){
			this.m_cfg.setNumber_value(new BigDecimal(val));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(BigDecimal val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.DOUBLE)){
			this.m_cfg.setNumber_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(boolean val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.DOUBLE)){
			this.m_cfg.setBool_value(val ? 1 : 0);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValueAsNull(SessionData session) throws Exception {
		if(this.getType().equals(TYPE.BINARY)){
			this.m_cfg.setBinary_value(null);
		}else if(this.getType().equals(TYPE.BOOLEAN)){
			this.m_cfg.setBool_value(null);
		}else if(this.getType().equals(TYPE.DATETIME)){
			this.m_cfg.setDate_value(null);
		}else if(this.getType().equals(TYPE.DOUBLE)){
			this.m_cfg.setNumber_value(null);
		}else if(this.getType().equals(TYPE.INT)){
			this.m_cfg.setNumber_value(null);
		}else if(this.getType().equals(TYPE.JSON) || this.getType().equals(TYPE.STRING) || this.getType().equals(TYPE.TEXT)){
			this.m_cfg.setString_value(null);
		}
		this.save(session);
	}
	public void setValue(String string,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.STRING)){
			this.m_cfg.setString_value(string);
			this.save(session);
		}else if(this.getType().equals(TYPE.TEXT)){
			this.m_cfg.setText_value(string);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(JSONObject val,SessionData session) throws Exception {
		if(this.getType().equals(TYPE.JSON)){
			this.m_cfg.setString_value(val == null ? null : val.toString());
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	private void save(final SessionData session) throws Exception {
		java.sql.Connection con = null;
		try{
			con = DataServiceImpl.getDatabase().getConnection();
			class HistorySaver extends Config_jdoManager {
				private Config_jdo m_old_record;
				public HistorySaver(java.sql.Connection con){
					super(con);
				}
				protected void handleAfterInsert(Config_jdo record) {
					if(debug) System.out.println("ConfigManager: record inserted");
				}
				protected void handleAfterUpdate(Config_jdo record) {
					ConfigManager.this.saveHistory(this.m_old_record);
				}
				protected void handleBeforeUpdate(Config_jdo record) {
					Config_jdo old_record = this.getConfig(record.getId());
					this.m_old_record = old_record;
				}
			}
			Config_jdoManager man = new HistorySaver(con);
			man.save(this.m_cfg);
		}catch(Exception e){
			throw e;
		}finally{
			try{con.close();}catch(Exception e){}
		}
	}
	public void saveHistory(Config_jdo record) {
		java.sql.Connection con = null;
		try{
			con = DataServiceImpl.getDatabase().getConnection();
			History_jdoManager man = new History_jdoManager(con);
			History_jdo history = man.newHistory();
			history.setName(record.getName());
			history.setTitle(record.getTitle());
			history.setDescription(record.getDescription());
			history.setParent(record.getParent());
			history.setObjectid(record.getId());
			history.setType(record.getType());
			history.setModified_by(record.getModified_by());
			history.setModified_date(record.getModified_date());
			history.setModifier_host(record.getModifier_host());
			history.setContent_type(record.getContent_type());
			man.save(history);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{con.close();}catch(Exception e){}
		}
	}
	public String getName() {
		return this.m_cfg == null ? null : this.m_cfg.getName();
	}
}