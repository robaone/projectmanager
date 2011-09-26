package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import com.robaone.api.business.BaseAction;
import com.robaone.api.data.SessionData;
import com.robaone.api.data.jdo.User_jdo;

public class Users extends BaseAction<User_jdo> {

	public Users(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
	}

}
