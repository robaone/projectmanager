package com.robaone.json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class ROSQLToJSONManager {
	public static final String DATABASE = "db";
	public static final String TABLE = "table";
	public static final String FIELDS = "fields";
	public static final String QUERY = "query";
	public static final String PARAMETERS = "params";
	private int m_limit;

	private String m_database;
	private String m_table;
	private String m_fields;
	private String m_query;
	private String m_parameters;
	private Connection m_con;

	public ROSQLToJSONManager(Map parameterMap,int limit,Connection con) {
		this.m_limit = limit;
		this.m_con = con;
		this.m_database = this.getParameterString(parameterMap,DATABASE);
		this.m_table = this.getParameterString(parameterMap,TABLE);
		this.m_fields = this.getParameterString(parameterMap,FIELDS);
		this.m_query = this.getParameterString(parameterMap,QUERY);
		this.m_parameters = this.getParameterString(parameterMap, PARAMETERS);
	}

	public String getResult() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String retval = null;
		try{
			this.checkTable(this.m_database);
			con = this.m_con;
			String sql = this.buildQuery();
			ps = con.prepareStatement(sql);
			System.out.println(sql);
			JSONArray jarray = new JSONArray(this.m_parameters);
			for(int i = 0; i < jarray.length();i++){
				Object value = jarray.get(i);
				int index = i+1;
				ps.setObject(index, value);
				System.out.println("param: "+index+ " = "+value);
			}
			rs = ps.executeQuery();
			JSONArray result = this.convert(rs);
			JSONObject o = new JSONObject();
			o.put("resultset", result);
			retval = o.toString();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		return retval;
	}

	private void checkTable(String mDatabase) throws Exception{
		
	}

	private JSONArray convert(ResultSet rs) throws SQLException {
		JSONArray retval = new JSONArray();
		int index = 0;
		while(rs.next() && (m_limit == -1 || index < m_limit)){
			Map map = new HashMap();
			ResultSetMetaData rsmeta = rs.getMetaData();
			for(int i = 0; i < rsmeta.getColumnCount();i++){
				String fieldname = rsmeta.getColumnLabel(i+1);
				Object value = rs.getObject(fieldname);
				if(value instanceof java.math.BigDecimal){
					value = new Double(value.toString());
				}
				map.put(fieldname, value);
			}
			retval.put(map);
			index++;
		}
		return retval;
	}

	private String buildQuery() {
		String retval = "select ";
		if(this.m_query != null && this.m_query.trim().length() > 0 && !this.m_query.trim().toLowerCase().startsWith("order by")
				&& !this.m_query.trim().toLowerCase().startsWith("group by")){
			retval += this.m_fields + " from " + this.m_table + " where " + this.m_query;
		}else{
			retval += this.m_fields + " from " + this.m_table + " " + (this.m_query == null ? "" : this.m_query);
		}
		return retval;
	}

	private String getParameterString(Map parameterMap,String key){
		String retval = null;
		Object o = parameterMap.get(key);
		if(o instanceof String){
			return (String)o;
		}else if(o instanceof String[]){
			String[] array = (String[])o;
			if(array.length > 0){
				return array[0];
			}
		}
		return retval;
	}

	public static HashMap buildMap(String db, String table2,
			String fields2, String query2, String parameters2) {
		HashMap retval = new HashMap();
		retval.put(DATABASE, db);
		retval.put(TABLE, table2);
		retval.put(FIELDS, fields2);
		retval.put(QUERY,query2);
		retval.put(PARAMETERS, parameters2);
		return retval;
	}
}
