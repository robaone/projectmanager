package com.robaone.dbase.hierarchial;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.robaone.dbase.hierarchial.types.ConfigType;

public class ConfigStruct{
	private String name;
	private int type;
	private String title;
	private String description;
	private String m_default_str_val;
	private boolean m_default_bool_val;
	private java.sql.Timestamp m_default_time_val;
	private byte[] m_default_bytes;
	private String m_content_type;
	private String m_path;
	public ConfigStruct(){

	}
	public ConfigStruct(String path,int val,String title,String description) throws Exception {
		this(path,new Integer(val),ConfigType.INT,title,description);
	}
	public ConfigStruct(String path,double val,String title,String description) throws Exception {
		this(path,new Double(val),ConfigType.DOUBLE,title,description);
	}
	public ConfigStruct(String path,boolean val,String title,String description) throws Exception {
		this(path,new Boolean(val),ConfigType.BOOLEAN, title,description);
	}
	public ConfigStruct(String path,Object value,int type,String title,String description) throws Exception{
		this.setPath(path);
		this.setTitle(title);
		this.setDescription(description);
		this.setType(type);
		if(value instanceof String){
			if(type == ConfigType.STRING || type == ConfigType.TEXT || type == ConfigType.DOUBLE || type == ConfigType.INT || type == ConfigType.JSON){
				this.setDefault_str_val(value.toString());
			}else if(type == ConfigType.BOOLEAN){
				if(value.toString().equalsIgnoreCase("true") || value.toString().equalsIgnoreCase("y") || value.toString().equalsIgnoreCase("yes") || value.toString().equals("1")){
					this.setDefault_bool_val(true);
				}else{
					this.setDefault_bool_val(false);
				}
			}else if(type == ConfigType.FOLDER){
				this.setDefault_str_val(value.toString());
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof java.util.Date){
			if(type == ConfigType.DATETIME){
				this.setDefault_time_val(new java.sql.Timestamp(((java.util.Date)value).getTime()));
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof Double){
			if(type == ConfigType.DOUBLE){
				this.setDefault_str_val(value.toString());
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof Integer){
			if(type == ConfigType.INT){
				this.setDefault_str_val(value.toString());
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof BigDecimal){
			if(type == ConfigType.INT || type == ConfigType.DOUBLE){
				this.setDefault_str_val(value.toString());
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof byte[]){
			if(type == ConfigType.BINARY){
				this.setDefault_bytes((byte[])value);
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof Boolean){
			if(type == ConfigType.BOOLEAN){
				this.setDefault_bool_val(((Boolean)value).booleanValue());
			}else{
				throw new Exception("Invalid object type");
			}
		}else if(value instanceof JSONObject){
			if(type == ConfigType.JSON){
				this.setDefault_str_val(((JSONObject)value).toString());
			}else{
				throw new Exception("Invalid object type");
			}
		}
	}
	public ConfigStruct(String path, byte[] bytes, String content_type,
			String title, String description) throws Exception {
		this(path,bytes,ConfigType.BINARY,title,description);
		this.setContent_type(content_type);
	}
	public ConfigStruct(String path, JSONObject jo, String title,
			String description) throws Exception {
		this(path,jo.toString(),ConfigType.JSON,title,description);
	}
	public String getPath() {
		return this.m_path;
	}
	public void setPath(String path){
		this.m_path = path;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDefault_str_val(String m_default_str_val) {
		if(this.type == ConfigType.BOOLEAN){
			if(m_default_str_val.equalsIgnoreCase("true") || m_default_str_val.equalsIgnoreCase("yes") || m_default_str_val.equalsIgnoreCase("y")){
				this.m_default_bool_val = true;
			}else{
				this.m_default_bool_val = false;
			}
		}else{
			this.m_default_str_val = m_default_str_val;
		}
	}
	public String getDefault_str_val() {
		return m_default_str_val;
	}
	public void setDefault_bool_val(boolean m_default_bool_val) {
		this.m_default_bool_val = m_default_bool_val;
	}
	public boolean isDefault_bool_val() {
		return m_default_bool_val;
	}
	public void setDefault_time_val(java.sql.Timestamp m_default_time_val) {
		this.m_default_time_val = m_default_time_val;
	}
	public java.sql.Timestamp getDefault_time_val() {
		return m_default_time_val;
	}
	public void setDefault_bytes(byte[] m_default_bytes) {
		this.m_default_bytes = m_default_bytes;
	}
	public byte[] getDefault_bytes() {
		return m_default_bytes;
	}
	public void setContent_type(String m_content_type) {
		this.m_content_type = m_content_type;
	}
	public String getContent_type() {
		return m_content_type;
	}
	public boolean getDefault_bool_val() {
		return this.m_default_bool_val;
	}

}