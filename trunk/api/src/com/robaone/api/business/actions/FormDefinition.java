package com.robaone.api.business.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;

public class FormDefinition {
	OutputStream out;
	SessionData session;
	HttpSession httpsession;
	public FormDefinition(OutputStream out,SessionData session,HttpSession s){
		this.out = out;
		this.session = session;
		this.httpsession = s;
	}
	public void get(JSONObject data) throws Exception {
		String form_xml = data.getJSONArray("form").getString(0);
		String path = AppDatabase.getProperty("form.folder");
	    path = path + System.getProperty("file.separator")+form_xml+".xml";
	    FileInputStream fin = null;
	    try{
	    	AppDatabase.writeLog("00003: Opening: "+path);
	    	fin = new FileInputStream(new File(path));
	    	AppDatabase.copyStream(fin,out);
	    }catch(Exception e){
	    	throw e;
	    }finally{
	    	fin.close();
	    }
	}
}
