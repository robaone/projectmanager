package com.robaone.gwt.framework.client.json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class JSONGWTRequest {
	private String action;
	private HashMap<String, String[]> data = new HashMap<String,String[]>();
	public JSONGWTRequest(){}
	public void setAction(String action){
		this.action = action;
	}
	public String getAction(){
		return this.action;
	}
	public void setData(HashMap<String,String[]> data){
		this.data = data;
	}
	public HashMap<String,String[]> getData(){
		return this.data;
	}
	public String getParameterString() throws UnsupportedEncodingException{
		String retval = "";
		retval += "action="+URL.encode(this.getAction());
		if(data != null){
			Iterator<String> it = this.data.keySet().iterator();
			JSONObject jo = new JSONObject();
			while(it.hasNext()){
				String key = it.next();
				String[] values = this.data.get(key);
				JSONArray ja = new JSONArray();
				for(int i = 0; i < values.length;i++){
					ja.set(ja.size(), new JSONString(values[i]));
				}
				jo.put(key, ja);
			}
			retval += "&data="+URL.encode(jo.toString());
		}
		return retval;
	}
	public void addData(String string, String formDefinitionURL) {
		String[] values = this.data.get(string);
		if(values == null){
			values = new String[0];
		}
		Vector<String> v = new Vector<String>();
		for(int i = 0; i < values.length;i++){
			v.add(values[i]);
		}
		v.add(formDefinitionURL);
		this.data.put(string, v.toArray(new String[0]));
	}
}
