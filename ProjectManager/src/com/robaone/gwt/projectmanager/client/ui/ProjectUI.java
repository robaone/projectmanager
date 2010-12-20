package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableDateBox;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableInput;
import com.robaone.gwt.projectmanager.client.ui.editable.EditableTextBox;

public class ProjectUI extends Composite {
	public ProjectUI(final MainContent main) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		
		EditableTextBox project = new EditableTextBox("Project", "",null);
		verticalPanel.add(project);
		
		
		EditableInput targetdate = new EditableDateBox("Target Date:","",null);
		verticalPanel.add(targetdate);
	}

}
