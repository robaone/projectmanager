package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;

public class Contractors extends Users {

	public Contractors(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
		filterRoles(AppDatabase.ROLE_SERVICEPROVIDER);
	}

}
