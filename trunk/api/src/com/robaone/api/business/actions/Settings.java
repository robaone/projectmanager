package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConfigStruct;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.dbase.HDBSessionData;

public class Settings extends BaseAction<ConfigManager> {
	private SettingsJDO m_settings = new SettingsJDO();
	public class SettingsJDO extends ConfigManager {

		@Override
		public ConfigManager newInstance() {
			return new SettingsJDO();
		}

		@Override
		protected HDBConnectionManager getConnectionManager() {
			return new DatabaseImpl().getConnectionManager();
		}

		@Override
		protected String getTableName() {
			return "sohvac";
		}
		
	}
	public Settings(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}

	public void list(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String path = this.findXPathString("//path");
				if(!FieldValidator.exists(path)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("path","You must enter a valid path");
				}else{
					ConfigManager[] items = m_settings.findFolderContentbyPath(path);
					for(ConfigManager m : items){
						getResponse().addData(m);
					}
				}
			}
			
		}.run(this, jo);
	}
	
	public void get(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				final String id = this.findXPathString("//id");
				final String path = this.findXPathString("//path");
				int state = 0;
				if(!FieldValidator.isNumber(id)){
					/**
					 * No id
					 */
					if(!FieldValidator.exists(path)){
						/**
						 * No path
						 */
						getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
						getResponse().addError("id","You must enter a valid id");
					}else{
						/**
						 * Path exists
						 */
						state = 1;
					}
				}else{
					switch(state){
					case 0:
						ConfigManager m = m_settings.findConfig(new BigDecimal(id));
						getResponse().addData(m);
						break;
					case 1:
						ConfigManager m2 = m_settings.findConfig(path);
						getResponse().addData(m2);
						break;
					}
						
				}
			}
			
		}.run(this, jo);
	}
	public void put(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				String id = this.findXPathString("//id");
				String value = this.findXPathString("//value");
				if(!FieldValidator.isNumber(id)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("id", "You must enter a valid id");
				}
				if(!FieldValidator.exists(value)){
					getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
					getResponse().addError("value", "You must enter a value");
				}
				if(getResponse().getStatus() == JSONResponse.OK){
					ConfigManager item = m_settings.findConfig(new BigDecimal(id));
					if(item != null){
						item.setValue(value, new HDBSessionData(getSessionData().getUser().getUsername(),getSessionData().getRemoteHost()));
						getResponse().addData(item);
					}else{
						getResponse().setStatus(JSONResponse.GENERAL_ERROR);
						getResponse().setError("Could not find record");
					}
				}
			}
			
		}.run(this, jo);
	}
	public void create(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				String path = this.findXPathString("//path");
				String title = this.findXPathString("//title");
				String description = this.findXPathString("//description");
				String type = this.findXPathString("//type");
				String value = this.findXPathString("//value");
				ConfigStruct cfg = new ConfigStruct();
				cfg.setPath(path);
				cfg.setDefault_str_val(value);
				cfg.setTitle(title);
				cfg.setDescription(description);
				cfg.setType(Integer.parseInt(type));
				ConfigManager m = m_settings.set(cfg, new HDBSessionData(getSessionData().getUser().getUsername(),getSessionData().getRemoteHost()));
				getResponse().addData(m);
			}
			
		}.run(this, jo);
	}
	public void delete(JSONObject jo){
		new FunctionCall(){

			@Override
			protected void run(JSONObject jo) throws Exception {
				String id = this.findXPathString("//id");
				ConfigManager m = m_settings.findConfig(new BigDecimal(id));
				if(m != null){
					m.delete(new HDBSessionData(getSessionData().getUser().getUsername(),getSessionData().getRemoteHost()));
				}else{
					getResponse().setStatus(JSONResponse.GENERAL_ERROR);
					getResponse().setError("Could not find record");
				}
			}
			
		}.run(this, jo);
	}

	@Override
	public DSResponse<ConfigManager> newDSResponse() {
		return new DSResponse<ConfigManager>();
	}
}
