package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;

public class Bids extends BaseAction<JSONObject> {

	public Bids(OutputStream o, SessionData d, HttpServletRequest request)
			throws ParserConfigurationException {
		super(o, d, request);
	}
	public void list(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void get(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void put(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void create(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void delete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void retract(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void addcomment(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void accept(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void complete(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void cancel(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void schedulemeeting(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void reject(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
	public void requestinformation(JSONObject jo){
		try{
			//TODO: Implement
		}catch(Exception e){
			this.sendError(e);
		}
	}
}
