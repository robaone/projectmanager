package com.robaone.json;


import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ROJSONProcessor {
	private String m_url;
	public ROJSONProcessor(String url){
		this.m_url = url;
	}
	public JSONObject getJO(String table,String primarykey) throws Exception {
		final StringBuffer buffer =new StringBuffer();
		class dataprocessor implements ROJSONLineProcessor{
			String data = null;
			public void process(String line) throws Exception {
				if(data != null){
					data += "\n" + line;
					buffer.append("\n"+line);
				}else{
					data= line;
					buffer.append(line);
				}

			}

		}
		HashMap parameters = new HashMap();
		parameters.put("id", primarykey);
		parameters.put("table",table);
		parameters.put("method", "get");
		ROJSONSendPost.sendPost(this.m_url, parameters, new dataprocessor());
		System.out.println(buffer.toString());
		JSONObject jo = new JSONObject(buffer.toString());
		return jo;
	}
	public JSONObject getResultset(JSONObject query) throws Exception {
		final StringBuffer buffer =new StringBuffer();
		class dataprocessor implements ROJSONLineProcessor{
			String data = null;
			public void process(String line) throws Exception {
				if(data != null){
					data += "\n" + line;
					buffer.append("\n"+line);
				}else{
					data= line;
					buffer.append(line);
				}

			}

		}
		HashMap parameters = new HashMap();
		/*
		 * Create query
		 * table,fields,query,parameters
		 */
		parameters.put("query", query.toString());
		parameters.put("method", "fetch");
		ROJSONSendPost.sendPost(m_url, parameters, new dataprocessor());
		System.out.println(buffer.toString());
		JSONObject jo = new JSONObject(buffer.toString());

		return jo;
	}
	public JSONObject deleteJO(String table,String primarykey) throws Exception {
		final StringBuffer buffer =new StringBuffer();
		class dataprocessor implements ROJSONLineProcessor{
			String data = null;
			public void process(String line) throws Exception {
				if(data != null){
					data += "\n" + line;
					buffer.append("\n"+line);
				}else{
					data= line;
					buffer.append(line);
				}

			}

		}
		HashMap parameters = new HashMap();
		parameters.put("id", primarykey);
		parameters.put("table", table);
		parameters.put("method", "delete");
		ROJSONSendPost.sendPost(this.m_url, parameters, new dataprocessor());
		System.out.println(buffer.toString());
		JSONObject jo = new JSONObject(buffer.toString());
		return jo;
	}
	public JSONObject saveJO(String table,JSONObject jo) throws Exception {
		JSONObject retval = null;
		final StringBuffer buffer =new StringBuffer();
		class dataprocessor implements ROJSONLineProcessor{
			String data = null;
			public void process(String line) throws Exception {
				if(data != null){
					data += "\n" + line;
					buffer.append("\n"+line);
				}else{
					data= line;
					buffer.append(line);
				}

			}

		}
		HashMap parameters = new HashMap();
		parameters.put("do", jo.toString());
		parameters.put("table", table);
		parameters.put("method", "put");
		ROJSONSendPost.sendPost(this.m_url, parameters, new dataprocessor());
		System.out.println(buffer.toString());
		retval = new JSONObject(buffer.toString());
		return retval;
	}
	public static String getStringField(JSONObject record,String field){
		try{
			if(record.isNull(field)){
				return null;
			}else{
				return record.getString(field);
			}
		}catch(JSONException e){
			return null;
		}
	}
	public static Integer getIntegerField(JSONObject record,String field){
		try{
			if(record.isNull(field)){
				return null;
			}else{
				return new Integer(record.getInt(field));
			}
		}catch(JSONException e){
			return null;
		}
	}
	public static Double getDoubleField(JSONObject record,String field){
		try{
			if(record.isNull(field)){
				return null;
			}else{
				return new Double(record.getDouble(field));
			}
		}catch(JSONException e){
			return null;
		}
	}
	public static Long getLongField(JSONObject record,String field){
		try{
			if(record.isNull(field)){
				return null;
			}else{
				return new Long(record.getLong(field));
			}
		}catch(JSONException e){
			return null;
		}
	}
	public static Boolean getBooleanField(JSONObject record,String field){
		try{
			if(record.isNull(field)){
				return null;
			}else{
				return new Boolean(record.getBoolean(field));
			}
		}catch(JSONException e){
			return null;
		}
	}
}
