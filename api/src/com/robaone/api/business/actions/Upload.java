package com.robaone.api.business.actions;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.DataScriptor;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;

public class Upload extends BaseAction<JSONObject> {

	protected static final String FILENAME = "filename";

	public Upload(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	
	public void city_state_county_zip(JSONObject jo){
		new UploadFunctionCall(new DataScriptor(){
			String filename;
			@Override
			public void handleField(String name, String asString)
					throws Exception {
				if(name.equalsIgnoreCase(FILENAME)){
					filename = asString;
				}
			}

			@Override
			public void handleStream(String name, InputStream stream,String content_type)
					throws Exception {
				if(content_type.contains("text") && name.toLowerCase().endsWith(".csv")){
					AppDatabase.writeLog("Reading csv file stream");
				}else{
					fieldError(name,"Wrong file type");
				}
			}
			
		}).run(this, jo);
	}

	@Override
	public DSResponse<JSONObject> newDSResponse() {
		return new DSResponse<JSONObject>();
	}

}
