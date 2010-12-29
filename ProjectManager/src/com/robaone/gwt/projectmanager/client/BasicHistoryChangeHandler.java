package com.robaone.gwt.projectmanager.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class BasicHistoryChangeHandler implements ValueChangeHandler<String> {

	private ProjectManager parent;

	public BasicHistoryChangeHandler(ProjectManager projectManager) {
		this.parent = projectManager;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		System.out.println("New history value = "+event.getValue());
	}

}
