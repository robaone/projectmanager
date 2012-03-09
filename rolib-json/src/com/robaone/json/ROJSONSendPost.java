package com.robaone.json;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class ROJSONSendPost {
	public static void sendPost(String urlstr,Map parameters,ROJSONLineProcessor lp) throws Exception {
		try{ // Construct data 
			String data = "";
			for(int i = 0; i < parameters.keySet().size();i++){
				String key = (String)parameters.keySet().toArray()[i];
				if(i > 0) data += "&";
				if(parameters.get(key) == null)
					data += key + "=";
				else
					data += key + "=" + URLEncoder.encode((String)parameters.get(key), "UTF-8");
			}
			// Send data 
			URL url = new URL(urlstr); 
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write(data); wr.flush(); 
			// Get the response 
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			String line; 
			
			while ((line = rd.readLine()) != null) { 
				lp.process(line); 
			} 
			wr.close(); 
			rd.close(); 
		} catch (Exception e) { 
			throw e;
		} 
	}

}

