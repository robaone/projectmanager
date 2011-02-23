package com.robaone.gwt.projectmanager.client;

abstract public class ProjectConstants {
	public static final int NOT_LOGGED_IN = 1;
	public static final int OK = 0;
	public static final int FIELD_VERIFICATION_ERROR = 2;
	public static final int GENERAL_ERROR = 3;
	public static final String SESSIONDATA = "sessiondata";
	public static enum USER_TYPE {HVACPROFESSIONAL,CUSTOMER,SUPERUSER,ADMINISTRATOR,CSR};
	public static final String USER_PATH = "administration/users";
}
