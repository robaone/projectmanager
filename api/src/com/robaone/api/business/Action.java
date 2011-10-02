package com.robaone.api.business;

import org.json.JSONObject;

public interface Action {
	public void list(JSONObject jo);
	public void get(JSONObject jo);
	public void put(JSONObject jo);
	public void delete(JSONObject jo);
	public void create(JSONObject jo);
}
