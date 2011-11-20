package com.robaone.page_service.business;

import com.robaone.page_service.data.SessionData;

public class Authorization {
	public static boolean requiresLogin(String page,SessionData sessiondata){
		boolean retval = false;
		String[] pages = {"dashboard"};
		for(String p : pages){
			if(p.equalsIgnoreCase(page)){
				if(sessiondata == null || sessiondata.getUser() == null){
					return true;
				}
			}
		}
		return retval;
	}
}
