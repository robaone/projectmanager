package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.server.ConfigManager.TYPE;
import com.robaone.gwt.projectmanager.server.business.TestDatabase;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdo;
import com.robaone.gwt.projectmanager.server.jdo.Config_jdoManager;

public class ConfigManager {
	public static enum TYPE {STRING,INT,DOUBLE,BOOLEAN,FOLDER}
	private String m_default_value;
	private String m_title;
	private TYPE m_type;
	private String m_description;;
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
		try{
			TestDatabase db = DataServiceImpl.getDatabase();
			con = db.getConnection();
			Config_jdoManager man = new Config_jdoManager(con);
			Config_jdo record = this.findValue(path,true);
		}catch(Exception e){
			
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
	}
	private Config_jdo findValue(String path, boolean create) {
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
					TestDatabase db = DataServiceImpl.getDatabase();
					con = db.getConnection();
					Config_jdoManager man = new Config_jdoManager(con);
					ps = man.prepareStatement(Config_jdo.PARENT + " = ? and "+Config_jdo.NAME + " = ?");
					ps.setBigDecimal(1, parent == null ? null : parent.getId());
					ps.setString(2, tokens[i]);
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
						//record.setTitle(m_title);
						//record.setDescription(m_description);
						man.save(record);
					}
				}catch(Exception e){
					
				}
			}
		}
		return null;
	}
	private Config_jdo findFolder(Object object, String folder, boolean create) {
		// TODO Auto-generated method stub
		return null;
	}
	public ConfigManager(String path) throws Exception {
		
	}
	public ConfigManager(int id) throws Exception {
		
	}
	public String getString(){
		return null;
	}
	public Integer getInt(){
		return null;
	}
	public Double getDouble(){
		return null;
	}
	public Boolean getBoolean(){
		return null;
	}
	public Integer getFolderID(){
		return null;
	}
}