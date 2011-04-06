package com.robaone.gwt.projectmanager.client.ui.tasks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TaskUi extends Composite {

	private static TaskUiUiBinder uiBinder = GWT.create(TaskUiUiBinder.class);

	interface TaskUiUiBinder extends UiBinder<Widget, TaskUi> {
	}

	public TaskUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
