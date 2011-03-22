package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NewProjectUI2 extends Composite {

	private static NewProjectUI2UiBinder uiBinder = GWT
			.create(NewProjectUI2UiBinder.class);

	interface NewProjectUI2UiBinder extends UiBinder<Widget, NewProjectUI2> {
	}

	public NewProjectUI2() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
