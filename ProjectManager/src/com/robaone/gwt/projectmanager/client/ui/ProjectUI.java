package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableDateBox;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableInput;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableSuggestBox;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableTextBox;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableSaveHandler;

public class ProjectUI extends Composite {
	public ProjectUI(final MainContent main) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		
		EditableTextBox project = new EditableTextBox("Project", "",null);
		verticalPanel.add(project);
		
		
		EditableInput targetdate = new EditableDateBox("Target Date:","",null);
		verticalPanel.add(targetdate);
		
		EditableSuggestBox status = new EditableSuggestBox("Status:", "", (EditableSaveHandler) null);
		verticalPanel.add(status);
	}

}
