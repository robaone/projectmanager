package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class GeneralError extends Composite {

	public GeneralError(Throwable caught) {
		Label error = new Label(caught.getMessage());
		this.initWidget(error);
	}

}
