package com.robaone.gwt.projectmanager.server;

import java.math.BigDecimal;

import com.robaone.gwt.projectmanager.server.ConfigManager.TYPE;
import com.robaone.gwt.projectmanager.server.business.ProjectDatabase;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdo;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdoManager;

public class ConfigManager {
	public static enum TYPE {STRING,INT,DOUBLE,BOOLEAN,FOLDER}
	private String m_default_value;
	private String m_title;
	private TYPE m_type;
	private String m_description;
	private Config_jdo m_cfg;
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
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
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
			Config_jdoManager man = new Config_jdoManager(con);
			Config_jdo record = this.findValue(path,true);
			this.m_cfg = record;
			if(record == null){
				throw new Exception("Cannot create config value");
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
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
						record.setType(m_type.toString());
						record.setValue(m_default_value);
						record.setTitle(m_title);
						record.setDescription(m_description);
						man.save(record);
						return record;
					}
				}catch(Exception e){
					throw e;
				}
			}
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
				record.setType(TYPE.FOLDER.toString());
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
		
	}
	public ConfigManager(int id) throws Exception {
		
	}
	public String getString(){
		return this.m_cfg.getValue();
	}
	public Integer getInt(){
		return Integer.parseInt(this.m_cfg.getValue());
	}
	public Double getDouble(){
		return Double.parseDouble(this.m_cfg.getValue());
	}
	public Boolean getBoolean(){
		return this.m_cfg.getValue().equals("true") ? true : false;
	}
	public BigDecimal getFolderID(){
		return this.m_cfg.getParent();
	}
}