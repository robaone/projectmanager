package com.robaone.page_service.business;

import java.util.HashMap;
import java.util.Map;

public class ParameterUtils {

	public static Map<String, String> parse(String datastr) {
		String[] tokens1 = datastr.split("&");
		HashMap<String,String> map = new HashMap<String,String>();
		for(String str1 : tokens1){
			String[] tokens2 = str1.split("=");
			try{
				map.put(tokens2[0], tokens2[1]);
			}catch(Exception e){}
		}
		return map;
	}

}
