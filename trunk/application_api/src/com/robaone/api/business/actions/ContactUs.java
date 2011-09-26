package com.robaone.api.business.actions;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.DatabaseImpl;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.Message_queue_jdo;
import com.robaone.api.data.jdo.Message_queue_jdoManager;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public class ContactUs {
	private OutputStream out;
	private SessionData session;
	private HttpSession httpsession;
	public ContactUs(OutputStream out,SessionData session,HttpSession s){
		this.out = out;
		this.session = session;
		this.httpsession = s;
	}

	public void Submit(final JSONObject data) throws Exception{
		AppDatabase.writeLog("com.sohvac.business.actions.ContactUs.Submit(\""+data+"\")");
		try{
			DSResponse<Error> dsr = new DSResponse<Error>();
			String name = null,email = null,zip = null,comments = null;
			try{ name = data.getJSONArray("name").getString(0);}catch(Exception e){}
			try{ email = data.getJSONArray("email").getString(0);}catch(Exception e){}
			try{ zip = data.getJSONArray("zip").getString(0);}catch(Exception e){}
			try{ comments = data.getJSONArray("comments").getString(0);}catch(Exception e){}
			if(name == null || name.trim().length() == 0){
				dsr.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				dsr.getResponse().addError("name","You must enter a name.");
			}
			if(email == null || FieldValidator.isEmail(email) == false){
				dsr.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				dsr.getResponse().addError("email", "You must enter a valid e-mail address.");
			}
			if(zip != null && zip.trim().length() > 0 && FieldValidator.isZipCode(zip) == false){
				dsr.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				dsr.getResponse().addError("zip", "You must enter a valid zip code.");
			}
			if(comments == null || comments.trim().length() == 0){
				dsr.getResponse().setStatus(JSONResponse.FIELD_VALIDATION_ERROR);
				dsr.getResponse().addError("comments", "Please enter a comment");
			}
			if(dsr.getResponse().getStatus() == JSONResponse.OK){
				/**
				 * The fields are valid send the e-mail
				 */
				DatabaseImpl db = new DatabaseImpl();
				ConnectionBlock block = new ConnectionBlock(){

					@Override
					public void run() throws Exception {
						Message_queue_jdoManager man = new Message_queue_jdoManager(this.getConnection());
						Message_queue_jdo record = man.newMessage_queue();
						record.setTo(AppDatabase.getProperty("contact.email"));
						record.setSubject(AppDatabase.getProperty("contact.subject"));
						record.setFrom(AppDatabase.getProperty("contact.from"));
						record.setCreationdate(new java.sql.Timestamp(new java.util.Date().getTime()));
						record.setHtml(new Boolean(true));
						String xml;
						JSONObject jo = data;
						xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><contact>"+XML.toString(jo)+"</contact>";
						record.setBody(AppDatabase.transform(AppDatabase.getStylesheet("contactus"),xml));
						man.save(record);
					}
					
				};
				ConfigManager.runConnectionBlock(block, db.getConnectionManager());
			}
			JSONObject jo = new JSONObject(dsr);
			PrintWriter pr = new PrintWriter(out);
			pr.print(jo.toString());
			pr.flush();
			pr.close();
		}catch(Exception e){
			throw e;
		}
	}
}
