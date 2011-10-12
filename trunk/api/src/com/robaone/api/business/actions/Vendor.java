package com.robaone.api.business.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.SessionData;

public class Vendor extends Users {

	public Vendor(OutputStream o, SessionData d, HttpServletRequest r)
			throws ParserConfigurationException {
		super(o, d, r);
		this.filterRoles(AppDatabase.ROLE_VENDOR);
	}

}
