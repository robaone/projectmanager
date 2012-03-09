package com.robaone.json;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONByteArray {
	byte[] m_bytes;
	public JSONByteArray(JSONArray array) throws JSONException{
		m_bytes = new byte[array.length()];
		for(int i = 0; i < array.length();i++){
			m_bytes[i] = (byte)array.getInt(i);
		}
	}
	public byte[] getBytes(){
		return this.m_bytes;
	}
	public String toString(){
		try {
			return new String(this.m_bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return this.toString();
		}
	}
}