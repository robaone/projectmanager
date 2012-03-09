package com.robaone.dbase.hierarchial;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;

import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.dbase.HDBSessionData;
import com.robaone.dbase.hierarchial.types.*;

abstract public class ConfigManager {
	private String m_default_value;
	private String m_title;
	private int m_type;
	private String m_description;
	private Config_jdo m_cfg;
	private byte[] m_bin_value;
	private Timestamp m_date_value;
	private HDBSessionData m_userdata;
	private String m_content_type;
	private boolean m_save_history = true;
	private java.io.InputStream m_in_stream;
	private static List m_cached_paths =  Collections.synchronizedList(new LinkedList());
	private static HashMap m_cached_paths_map = new HashMap();
	protected ConfigManager(){

	}
	public ConfigManager set(ConfigStruct cfg,HDBSessionData session) throws Exception{
		this.m_userdata = session;
		if(cfg.getType() == ConfigType.DATETIME){
			this.m_date_value = cfg.getDefault_time_val();
			this.createConfigValue(cfg.getPath(), ConfigType.DATETIME, cfg.getTitle(), cfg.getDescription());
			this.setValue(cfg.getDefault_time_val(), session);
		}else if(cfg.getType() == ConfigType.STRING || cfg.getType() == ConfigType.TEXT ||
				cfg.getType() == ConfigType.JSON){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(cfg.getDefault_str_val(), session);
		}else if(cfg.getType() == ConfigType.BOOLEAN){
			this.m_default_value = cfg.getDefault_bool_val() ? "1" : "0";
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(cfg.getDefault_str_val(), session);
		}else if(cfg.getType() == ConfigType.INT){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(new Integer(cfg.getDefault_str_val()).intValue(), session);
		}else if(cfg.getType() == ConfigType.DOUBLE){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(new Double(cfg.getDefault_str_val()).doubleValue(), session);
		}else if(cfg.getType() == ConfigType.BINARY){
			this.m_bin_value = cfg.getDefault_bytes();
			this.m_content_type = cfg.getContent_type();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(cfg.getDefault_bytes(), session);
		}
		return this;
	}
	public ConfigManager setdefault(ConfigStruct cfg,HDBSessionData session) throws Exception{
		this.m_userdata = session;
		if(cfg.getType() == ConfigType.DATETIME){
			this.m_date_value = cfg.getDefault_time_val();
			this.createConfigValue(cfg.getPath(), ConfigType.DATETIME, cfg.getTitle(), cfg.getDescription());
		}else if(cfg.getType() == ConfigType.STRING || cfg.getType() == ConfigType.TEXT ||
				cfg.getType() == ConfigType.JSON){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
		}else if(cfg.getType() == ConfigType.BOOLEAN){
			this.m_default_value = cfg.getDefault_bool_val() ? "1" : "0";
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
		}else if(cfg.getType() == ConfigType.INT){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(new Integer(cfg.getDefault_str_val()).intValue(), session);
		}else if(cfg.getType() == ConfigType.DOUBLE){
			this.m_default_value = cfg.getDefault_str_val();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(new Double(cfg.getDefault_str_val()).doubleValue(), session);
		}else if(cfg.getType() == ConfigType.BINARY){
			this.m_bin_value = cfg.getDefault_bytes();
			this.m_content_type = cfg.getContent_type();
			this.createConfigValue(cfg.getPath(), cfg.getType(), cfg.getTitle(), cfg.getDescription());
			this.setValue(cfg.getDefault_bytes(), session);
		}
		return this;
	}

	private void createConfigValue(String path, int type, String title, String description) throws Exception {
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
			ConnectionBlock block = new ConnectionBlock(){

				protected void run() throws Exception {


				}

			};
			//ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			Config_jdo record = this.findValue(path,true);
			this.m_cfg = record;
			if(record == null){
				throw new Exception("Cannot create config value");
			}
		}catch(Exception e){
			throw e;
		}finally{
		}
	}
	abstract public ConfigManager newInstance();

	abstract protected HDBConnectionManager getConnectionManager();
	abstract protected String getTableName();
	class SetValueBlock extends ConnectionBlock{
		private Vector m_retval;
		private Config_jdo m_parent;
		private String m_name;
		private boolean m_create;
		public SetValueBlock(Vector retval,Config_jdo parent,String name,boolean create){
			m_retval = retval;
			m_parent = parent;
			m_name = name;
			m_create = create;
		}
		protected void run() throws Exception {
			Config_jdo record = null;
			Config_jdoManager man = new Config_jdoManager(this.getConnection());
			man.setTableName(getTableName());
			if(m_parent == null){
				this.setPreparedStatement(man.prepareStatement(Config_jdo.PARENT + " is null and "+Config_jdo.NAME + " = ? "));
				this.getPreparedStatement().setString(1, m_name);
			}else{
				this.setPreparedStatement(man.prepareStatement(Config_jdo.PARENT + " = ? and "+Config_jdo.NAME + " = ? "));
				this.getPreparedStatement().setBigDecimal(1, m_parent.getId());
				this.getPreparedStatement().setString(2, m_name);
			}
			this.setResultSet(this.getPreparedStatement().executeQuery());
			if(this.getResultSet().next()){
				record = Config_jdoManager.bindConfig(this.getResultSet());
				if(record != null){
					m_retval.add(record);
				}
			}else if(m_create){
				record = man.newConfig();
				record.setName(m_name);
				record.setParent(m_parent == null ? null : m_parent.getId());
				record.setType(getDBType(m_type));
				setDBValue(record);
				record.setTitle(m_title);
				record.setDescription(m_description);
				String username = "anonymous";
				try{username = m_userdata.getUsername();}catch(Exception e){}
				record.setCreated_by(username);
				record.setCreated_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				record.setModified_by(username);
				record.setModifier_host(m_userdata.getHost());
				record.setModified_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				man.save(record);
				m_retval.add(record);
			}else{

			}

		}

	};
	private Config_jdo findValue(String path, final boolean create) throws Exception {
		if(path.startsWith("/")){
			path = path.substring(1);
		}
		final String[] tokens = path.split("/");
		Config_jdo parent = null;
		for(int i = 0;i<tokens.length;i++){
			if(tokens.length > 1){
				/******
				 * Optimization code
				 **************/
				final BigDecimal cached_parent = ConfigManager.findCachedParentID(tokens);
				if(cached_parent != null){
					final Vector retval = new Vector();
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							Config_jdoManager man = new Config_jdoManager(this.getConnection());
							man.setTableName(getTableName());
							Config_jdo parent = man.getConfig(cached_parent);
							ConnectionBlock savevalueblock = new SetValueBlock(retval,parent,tokens[tokens.length-1],create);
							ConfigManager.runConnectionBlock(savevalueblock, getConnectionManager());
						}

					};
					ConfigManager.runConnectionBlock(block, this.getConnectionManager());
					if(retval.size() > 0) return (Config_jdo)retval.get(0);
					else return null;
				}
				/**********************
				 * End Optimization
				 ***************/
			}
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

				try{
					if(tokens.length > 1)
						ConfigManager.addCachedParentID(ConfigManager.getPathbyDepth(tokens, tokens.length-1), parent.getId());
					final Config_jdo parent_f = parent;
					final int index = i;
					final Vector retval = new Vector();
					ConnectionBlock block = new SetValueBlock(retval,parent,tokens[index],create);
					ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
					ConfigManager.runConnectionBlock(block, getConnectionManager());
					if(retval.size() > 0){
						return (Config_jdo) retval.get(0);
					}
				}catch(Exception e){
					throw e;
				}finally{
				}
			}
		}
		return null;
	}
	private static String getPathbyDepth(String[] tokens,int depth){
		String path = "";
		if(tokens == null) return null;
		for(int i = 0; i < tokens.length && i < depth;i++){
			path += (i == 0 ? "" : "/") + tokens[i];
		}
		return path;
	}
	private static BigDecimal findCachedParentID(String[] tokens) {
		String parent_path = getPathbyDepth(tokens,tokens.length-1);
		BigDecimal retval = (BigDecimal)ConfigManager.m_cached_paths_map.get(parent_path);
		return retval;
	}
	private static void addCachedParentID(String parent_path,BigDecimal id){
		if(ConfigManager.m_cached_paths_map.get(parent_path) == null){
			ConfigManager.m_cached_paths.add(parent_path);
			ConfigManager.m_cached_paths_map.put(parent_path, id);
		}


		/***********
		 * Remove item that is oldest if list is larger than 250  
		 ***********/
		if(ConfigManager.m_cached_paths.size() > 250){
			for(int i = 250;i < ConfigManager.m_cached_paths.size();){
				String path_to_remove = (String)ConfigManager.m_cached_paths.get(i);
				if(path_to_remove != null){
					ConfigManager.m_cached_paths_map.remove(path_to_remove);
				}
				ConfigManager.m_cached_paths.remove(i);
			}
		}
	}
	private void setDBValue(Config_jdo record) throws Exception {
		if(this.m_type == ConfigType.BINARY){
			if(this.m_in_stream == null){
				record.setBinary_value(this.m_bin_value);
			}else{
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				byte[] buffer = new byte[128];
				for(int i = this.m_in_stream.read(buffer);i > -1;i = this.m_in_stream.read(buffer)){
					bout.write(buffer, 0, i);
				}
				record.setBinary_value(bout.toByteArray());
			}
			record.setContent_type(this.m_content_type);
		}else if(this.m_type == ConfigType.BOOLEAN){
			record.setBool_value(new Integer(this.m_default_value));
		}else if(this.m_type == ConfigType.DATETIME){
			record.setDate_value(this.m_date_value);
		}else if(this.m_type == ConfigType.DOUBLE){
			record.setNumber_value(new BigDecimal(this.m_default_value));
		}else if(this.m_type == ConfigType.INT){
			record.setNumber_value(new BigDecimal(this.m_default_value));
		}else if(this.m_type == ConfigType.STRING){
			record.setString_value(this.m_default_value);
		}else if(this.m_type == ConfigType.TEXT){
			record.setText_value(this.m_default_value);
		}else if(this.m_type == ConfigType.JSON){
			record.setText_value(this.m_default_value);
		}else if(this.m_type == ConfigType.FOLDER){

		}else{
			throw new Exception("Invalid type");
		}
	}
	private Integer getDBType(int mType) {
		return new Integer(mType);
	}
	private Config_jdo findFolder(Config_jdo object, String folder, boolean create) throws Exception {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			con = this.getConnectionManager().getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			man.setTableName(this.getTableName());
			if(object == null){
				ps = man.prepareStatement(Config_jdo.NAME + " = ? and "+ Config_jdo.PARENT +" is null and "+Config_jdo.TYPE + " = ?");
				ps.setInt(2, ConfigType.FOLDER);
			}else{
				ps = man.prepareStatement(Config_jdo.NAME + " = ? and "+ Config_jdo.PARENT +" = ? and "+Config_jdo.TYPE + " = ?");
				ps.setBigDecimal(2, object.getId());
				ps.setInt(3, ConfigType.FOLDER);
			}
			ps.setString(1, folder);
			rs = ps.executeQuery();
			if(rs.next()){
				Config_jdo record = Config_jdoManager.bindConfig(rs);
				return record;
			}else if(create){
				Config_jdo record = man.newConfig();
				record.setParent(object == null ? null : object.getId());
				record.setType(this.getDBType(ConfigType.FOLDER));
				record.setName(folder);
				record.setTitle(record.getName() + " folder");
				String username = "anonymous";
				try{username = this.m_userdata.getUsername();}catch(Exception e){}
				record.setCreated_by(username);
				record.setCreated_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				record.setModifier_host(this.m_userdata.getHost());
				record.setModified_date(new java.sql.Timestamp(new java.util.Date().getTime()));
				record.setModified_by(username);
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
			try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
		}
	}
	private ConfigManager(String path) throws Exception {
		try{
			this.m_cfg = this.findValue(path, false);
		}catch(Exception e){
			throw new Exception("Config Value not found");
		}
	}
	private ConfigManager find(BigDecimal id) throws Exception {
		java.sql.Connection con = null;
		try{
			ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			con = db.getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			man.setTableName(this.getTableName());
			Config_jdo record = man.getConfig(id);
			if(record == null){
				throw new Exception("Config Value not found");
			}
			ConfigManager retval = this.newInstance();
			retval.m_cfg = record;
			return retval;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
		}
	}
	public int newCounter(Object c,String[] params,HDBSessionData session) throws Exception {
		String path = ConfigManager.path(c, params);
		ConfigManager cfg = this.findConfig(path);
		if(cfg != null) cfg.supressHistory();
		int retval = 0;
		if(cfg == null){
			cfg = this.setdefault(new ConfigStruct(path,"1",ConfigType.INT,"The next id","This value is the counter for the unique ids.  It should not be edited manually unless necessary."), session);// new ConfigManager(path,1,"The next id","This value is the counter for the unique ids.  It should not be edited manually unless necessary.",session);
		}else{
			retval = cfg.getInt().intValue();
			cfg.setValue(cfg.getInt().intValue()+1, session);
		}

		return retval;
	}
	public String getAbsolutePath() throws Exception {
		String retval = "";
		java.sql.Connection con = null;
		try{
			ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			con = db.getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			man.setTableName(this.getTableName());
			BigDecimal parentid = null;
			BigDecimal current = this.m_cfg.getId();
			do{
				Config_jdo rec = man.getConfig(current);
				retval = "/"+rec.getName()+retval;
				parentid = rec.getParent();
				current = parentid;
			}while(parentid != null);
			if(retval.startsWith("/")){
				retval = retval.substring(1);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
		}
		return retval;
	}
	public byte[] getBinary() throws Exception{
		if(this.getType() == ConfigType.BINARY){
			return this.m_cfg.getBinary_value(this.getTableName(),this.getConnectionManager());
		}else{
			throw new Exception("Invalid type");
		}
	}
	public java.sql.ResultSet getBinaryInputStream(java.sql.Connection con) throws Exception{
		if(this.m_cfg == null){
			return null;
		}else{
			return this.m_cfg.getBinaryValueReader(this.getTableName(),con);
		}
	}
	public void setBinaryValue(java.io.InputStream stream,HDBSessionData session) throws Exception {
		if(this.m_cfg == null){
			return;
		}else{
			ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			java.sql.Connection con = null;
			try{
				con = db.getConnection();
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				byte[] buffer = new byte[128];
				for(int i = stream.read(buffer);i > -1;i = stream.read(buffer)){
					bout.write(buffer,0,i);
				}
				this.setValue(bout.toByteArray(), session);
			}finally{
				try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
			}
		}
	}
	public String getContentType() {
		return this.m_cfg.getContent_type();
	}
	public String getString() throws Exception{
		if(this.getType() == ConfigType.STRING){
			return this.m_cfg.getString_value();
		}else if(this.getType() == ConfigType.TEXT){
			return this.m_cfg.getText_value(this.getTableName(),this.getConnectionManager());
		}else{
			throw new Exception("Invalid type");
		}
	}
	public int getType() throws Exception {
		if(this.m_cfg == null){
			return this.m_type;
		}else{
			switch (this.m_cfg.getType().intValue()){
			case 0:
				return ConfigType.FOLDER;
			case 1:
				return ConfigType.BOOLEAN;
			case 2:
				return ConfigType.DOUBLE;
			case 3:
				return ConfigType.INT;
			case 4:
				return ConfigType.STRING;
			case 5:
				return ConfigType.DATETIME;
			case 6:
				return ConfigType.TEXT;
			case 7:
				return ConfigType.BINARY;
			case 8:
				return ConfigType.JSON;
			default:
				throw new Exception("Invalid type");
			}
		}
	}
	public Integer getInt() throws Exception {
		if(this.getType() == ConfigType.INT){
			if(this.m_cfg.getNumber_value() != null){
				return new Integer(this.m_cfg.getNumber_value().intValue());
			}else return null;
		}else{
			throw new Exception("Invalid type");
		}
	}
	public Double getDouble() throws Exception {
		if(this.getType() == ConfigType.DOUBLE){
			return round4(this.m_cfg.getNumber_value().doubleValue());
		}else{
			throw new Exception("Invalid type");
		}
	}
	public static Double round4(double num) {
		double result = num * 10000;
		result = Math.round(result);
		result = result / 10000;
		return new Double(result);
	}
	public Boolean getBoolean() throws Exception {
		if(this.getType() == ConfigType.BOOLEAN){
			if(this.m_cfg.getBool_value() == null){
				return null;
			}else{
				return new Boolean(this.m_cfg.getBool_value().intValue() == 1 ? true : false);
			}
		}else{
			throw new Exception("Invalid type");
		}
	}
	public java.sql.Timestamp getDateTime() throws Exception {
		if(this.getType() == ConfigType.DATETIME){
			return this.m_cfg.getDate_value();
		}else{
			throw new Exception("Invalid type");
		}
	}
	public JSONObject getJSON() throws Exception {
		if(this.getType() == ConfigType.JSON){
			if(this.m_cfg.getText_value(this.getTableName(),this.getConnectionManager()) != null){
				JSONObject retval = new JSONObject(this.m_cfg.getText_value(this.getTableName(),this.getConnectionManager()));
				return retval;
			}else{
				return null;
			}
		}else{
			throw new Exception("Invalid type");
		}
	}
	public BigDecimal getFolderID(){
		return this.m_cfg.getParent();
	}
	public BigDecimal getId(){
		return this.m_cfg.getId();
	}
	public ConfigManager[] findFolderContentbyPath(String path) {
		if(path != null){
			if(path.startsWith("/")){
				path.substring(1);
			}
			String[] tokens = path.split("/");
			String sql = "select * from "+this.getTableName()+" where parent ";
			String suffix = "";
			for(int i = tokens.length;i >= 0;i--){
				if(i > 0){
					sql += "= (select id from "+this.getTableName()+" where name = ? and parent ";
					suffix += ")";
				}else{
					sql += "is null";
				}
			}
			if(tokens.length == 0) sql += "is null";
			else sql = sql + suffix;
			java.sql.Connection con = null;
			java.sql.PreparedStatement ps = null;
			java.sql.ResultSet rs = null;
			try{
				ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
				con = db.getConnection();
				ps = con.prepareStatement(sql);
				for(int i = tokens.length;i > 0;i--){
					ps.setString(tokens.length+1-i, tokens[i-1]);
				}
				rs = ps.executeQuery();
				Vector retval = new Vector();
				while(rs.next()){
					Config_jdo record = Config_jdoManager.bindConfig(rs);
					ConfigManager cfg = this.newInstance();
					cfg.m_cfg = record;
					retval.add(cfg);
				}
				return (ConfigManager[])retval.toArray(new ConfigManager[0]);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{rs.close();}catch(Exception e){}
				try{ps.close();}catch(Exception e){}
				try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
			}
		}
		return null;
	}
	public ConfigManager[] findFolderContentbyId(BigDecimal id) {
		if(id != null){
			String sql = "select * from "+this.getTableName()+" where parent = ?";
			java.sql.Connection con = null;
			java.sql.PreparedStatement ps = null;
			java.sql.ResultSet rs = null;
			try{
				ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
				con = db.getConnection();
				ps = con.prepareStatement(sql);
				ps.setBigDecimal(1, id);
				rs = ps.executeQuery();
				Vector retval = new Vector();
				while(rs.next()){
					Config_jdo record = Config_jdoManager.bindConfig(rs);
					ConfigManager cfg = this.newInstance();
					cfg.m_cfg = record;
					retval.add(cfg);
				}
				return (ConfigManager[]) retval.toArray(new ConfigManager[0]);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{rs.close();}catch(Exception e){}
				try{ps.close();}catch(Exception e){}
				try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
			}
		}
		return null;
	}
	public ConfigManager findConfig(String path, String variable) {
		ConfigManager cfg = this.newInstance();
		Config_jdo val = null;
		try{
			val = cfg.findValue(path + "/" + variable, false);
			cfg.m_cfg = val;
			if(val == null){
				return null;
			}else{
				return cfg;
			}
		}catch(Exception e){

		}
		return null;
	}
	public ConfigManager findConfig(String path) {
		ConfigManager cfg = this.newInstance();
		try{
			cfg.m_cfg = cfg.findValue(path, false);
			if(cfg.m_cfg == null){
				return null;
			}else{
				return cfg;
			}
		}catch(Exception e){

		}
		return null;
	}
	public void setValue(byte[] val,HDBSessionData session) throws Exception {
		if(val == null){
			this.setValueAsNull(session);
		}else if(this.getType() == ConfigType.BINARY){
			this.m_cfg.setBinary_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(java.sql.Timestamp val,HDBSessionData session) throws Exception {
		if(val == null){
			this.setValueAsNull(session);
		}else if(this.getType() == ConfigType.DATETIME){
			this.m_cfg.setDate_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(int val,HDBSessionData session) throws Exception {
		if(this.getType() == ConfigType.INT){
			this.m_cfg.setNumber_value(new BigDecimal(val));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(float val,HDBSessionData session) throws Exception {
		if(this.getType() == ConfigType.DOUBLE){
			this.m_cfg.setNumber_value(new BigDecimal(val));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(double val,HDBSessionData session) throws Exception {
		if(this.getType() == ConfigType.DOUBLE){
			this.m_cfg.setNumber_value(new BigDecimal(val+""));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(BigDecimal val,HDBSessionData session) throws Exception {
		if(val == null){
			this.setValueAsNull(session);
		}else if(this.getType() == ConfigType.DOUBLE){
			this.m_cfg.setNumber_value(val);
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValue(boolean val,HDBSessionData session) throws Exception {
		if(this.getType() == ConfigType.BOOLEAN){
			this.m_cfg.setBool_value(val ? new Integer(1) : new Integer(0));
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	public void setValueAsNull(HDBSessionData session) throws Exception {
		if(this.getType() == ConfigType.BINARY){
			this.m_cfg.setBinary_value(null);
		}else if(this.getType() == ConfigType.BOOLEAN){
			this.m_cfg.setBool_value(null);
		}else if(this.getType() == ConfigType.DATETIME){
			this.m_cfg.setDate_value(null);
		}else if(this.getType() == ConfigType.DOUBLE){
			this.m_cfg.setNumber_value(null);
		}else if(this.getType() == ConfigType.INT){
			this.m_cfg.setNumber_value(null);
		}else if(this.getType() == ConfigType.JSON || this.getType() == ConfigType.STRING || this.getType() == ConfigType.TEXT){
			this.m_cfg.setString_value(null);
		}
		this.save(session);
	}
	public void setValue(String string,HDBSessionData session) throws Exception {
		if(string == null){
			this.setValueAsNull(session);
		}else 
			if(this.getType() == ConfigType.STRING){
				this.m_cfg.setString_value(string);
				this.save(session);
			}else if(this.getType() == ConfigType.TEXT){
				this.m_cfg.setText_value(string);
				this.save(session);
			}else if(this.getType() == ConfigType.JSON){
				JSONObject jo = new JSONObject(string);
				this.setValue(jo, session);
				this.save(session);
			}else if(this.getType() == ConfigType.BOOLEAN){
				if(string.equalsIgnoreCase("1") || string.equalsIgnoreCase("y") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("true")){
					this.setValue(true, session);
				}else{
					this.setValue(false, session);
				}

			}else{
				throw new Exception("Invalid type");
			}
	}
	public void setValue(JSONObject val,HDBSessionData session) throws Exception {
		if(val == null){
			this.setValueAsNull(session);
		}else if(this.getType() == ConfigType.JSON){
			this.m_cfg.setText_value(val == null ? null : val.toString());
			this.save(session);
		}else{
			throw new Exception("Invalid type");
		}
	}
	private void save(final HDBSessionData session) throws Exception {
		java.sql.Connection con = null;
		try{
			ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
			con = db.getConnection();
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
					String username = "anonymous";
					try{username = session.getUsername();}catch(Exception e){}
					record.setModified_by(username);
					record.setModified_date(new java.sql.Timestamp(new java.util.Date().getTime()));
					record.setModifier_host(session.getHost());
					this.m_old_record = old_record;
				}
			}
			Config_jdoManager man = new HistorySaver(con);
			man.setTableName(this.getTableName());
			man.save(this.m_cfg);
		}catch(Exception e){
			throw e;
		}finally{
			try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
		}
	}
	public void saveHistory(final Config_jdo record) {
		try{
			if(System.getProperty("history").equals("N")){
				this.m_save_history = false;
			}
		}catch(Exception e){}
		ConnectionBlock block = new ConnectionBlock(){

			protected void run() throws Exception {
				History_jdoManager man = new History_jdoManager(this.getConnection());
				man.setTableName(getHistoryTableName());
				History_jdo history = man.newHistory();
				history.setName(record.getName());
				history.setTitle(record.getTitle());
				history.setDescription(record.getDescription(getTableName(),getConnectionManager()));
				history.setParent(record.getParent());
				history.setObjectid(record.getId());
				history.setType(record.getType());
				history.setBinary_value(record.getBinary_value(getTableName(),getConnectionManager()));
				history.setBool_value(record.getBool_value());
				history.setDate_value(record.getDate_value());
				history.setNumber_value(record.getNumber_value());
				history.setString_value(record.getString_value());
				history.setText_value(record.getText_value(getTableName(),getConnectionManager()));
				history.setModified_by(record.getModified_by());
				history.setModified_date(record.getModified_date());
				history.setModifier_host(record.getModifier_host());
				history.setContent_type(record.getContent_type());
				man.save(history);
			}

		};
		if(this.m_save_history){
			try{
				ConfigManager.runConnectionBlock(block, this.getConnectionManager());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public String getName() {
		return this.m_cfg == null ? null : this.m_cfg.getName();
	}
	public String getDescription() {
		return this.m_cfg == null ? null : this.m_cfg.getDescription(getTableName(),getConnectionManager());
	}
	public String getTitle() {
		return this.m_cfg == null ? null : this.m_cfg.getTitle();
	}
	public String getModifiedBy() {
		return this.m_cfg == null ? null : this.m_cfg.getModified_by();
	}
	public String getCreatedBy() {
		return this.m_cfg == null ? null : this.m_cfg.getCreated_by();
	}
	public java.util.Date getModifiedDate() {
		return this.m_cfg == null ? null : this.m_cfg.getModified_date();
	}
	public java.util.Date getCreatedDate() {
		return this.m_cfg == null ? null : this.m_cfg.getCreated_date();
	}
	public void setContentType(String string,HDBSessionData session) throws Exception {
		this.m_cfg.setContent_type(string);
		this.save(session);
	}
	public static String getClobString(Clob clob) {
		try {
			Reader r = ((java.sql.Clob)clob).getCharacterStream();
			StringBuffer sbuff = new StringBuffer();
			char[] cbuf = new char[500];
			for(int i = r.read(cbuf); i > -1; i = r.read(cbuf)){
				sbuff.append(cbuf, 0, i);
			}
			return sbuff.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void supressHistory() {
		this.m_save_history  = false;
	}
	public static String path(Object c,
			String[] params) {
		String name = c.getClass().getName();
		name = name.replaceAll("[.]", "/");
		for(int i = 0; i < params.length;i++){
			name += "/";
			name += params[i];
		}
		return name;
	}
	private String getHistoryTableName() {
		return this.getTableName().equals("CONFIG") ? "HISTORY" : this.getTableName() + "_HISTORY";
	}

	public void delete(final HDBSessionData sessiondata) throws Exception {
		if(this.m_cfg == null) return;
		ConfigManager[] children = this.findFolderContentbyId(this.getId());
		ProjectDatabase db = new ProjectDatabase(this.getTableName(),this.getConnectionManager());
		java.sql.Connection con = null;
		try{
			con = db.getConnection();
			class ConfigDeleteManager extends Config_jdoManager{
				public ConfigDeleteManager(Connection con){
					super(con);
				}
				public void handleBeforeDelete(final Config_jdo record){
					/**
					 * Check for record in history table
					 */
					final History_jdoManager man = new History_jdoManager(this.getConnection());
					man.setTableName(getHistoryTableName());
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							this.setPreparedStatement(man.prepareStatement(History_jdo.ID + " = ?"));
							this.getPreparedStatement().setBigDecimal(1, record.getId());
							this.setResultSet(this.getPreparedStatement().executeQuery());
							if(this.getResultSet().next()){
								// Do nothing
							}else{
								// Create the record
								saveHistory(record);
							}
						}

					};
					try {
						ConfigManager.runConnectionBlock(block,getConnectionManager());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				public void handleAfterDelete(final Config_jdo record){
					final History_jdoManager man = new History_jdoManager(this.getConnection());
					man.setTableName(getHistoryTableName());
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							this.setPreparedStatement(man.prepareStatement(History_jdo.ID + " = (select max("+
									History_jdo.ID+") from "+man.getTableName()+
									" where "+History_jdo.OBJECTID + " = ?)"));
							this.getPreparedStatement().setBigDecimal(1, record.getId());
							this.setResultSet(this.getPreparedStatement().executeQuery());
							if(this.getResultSet().next()){
								History_jdo record = History_jdoManager.bindHistory(this.getResultSet());
								record.setDeleted_by(sessiondata.getUsername());
								record.setDeletion_host(sessiondata.getHost());
								record.setDeleted_date(new java.sql.Timestamp(new java.util.Date().getTime()));
								man.save(record);
							}
						}

					};
					try {
						ConfigManager.runConnectionBlock(block,getConnectionManager());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			Config_jdoManager man = new ConfigDeleteManager(con);
			for(int i = 0 ; i < children.length;i++){
				Config_jdo record = man.getConfig(children[i].getId());
				ConfigManager child = this.findConfig(record.getId());
				child.delete(sessiondata);
			}
			Config_jdo record = man.getConfig(this.getId());
			man.delete(record);
		}finally{
			try{this.getConnectionManager().closeConnection(con);}catch(Exception e){}
		}
	}
	public static HashMap getMap(ConfigManager[] cfs) {
		HashMap retval = new HashMap();
		for(int i = 0; i < cfs.length;i++){
			retval.put(cfs[i].getName(), cfs[i]);
		}
		return retval;
	}
	public static void runConnectionBlock(ConnectionBlock block,HDBConnectionManager cman) throws Exception{
		block.run(cman);
	}
	public ConfigManager findConfig(BigDecimal bigDecimal) throws Exception {
		ConfigManager cfg = this.find(bigDecimal);
		return cfg;
	}
	public void setValue(Date due_date, HDBSessionData session) throws Exception {
		if(due_date == null){
			this.setValueAsNull(session);
		}else{
			this.setValue(new java.sql.Timestamp(due_date.getTime()), session);
		}

	}
}