package com.robaone.gwt.form.client.ui;

import com.google.gwt.event.dom.client.KeyUpHandler;

public interface FormField {

	String getName();
	public void setName(String str);
	String[] getValues();
	void setError(boolean b);
	void addKeyUpHandler(KeyUpHandler handler);
}
