package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.Project;

public class ProjectUi2 extends Composite {

	private static ProjectUi2UiBinder uiBinder = GWT
			.create(ProjectUi2UiBinder.class);

	interface ProjectUi2UiBinder extends UiBinder<Widget, ProjectUi2> {
	}
	private MainContent main;

	public ProjectUi2(MainContent m) {
		initWidget(uiBinder.createAndBindUi(this));
		this.main = m;
	}
	@UiField InlineLabel project_name;
	@UiField InlineLabel description;
	@UiField InlineLabel due_date;
	@UiField Label priority;
	private Project m_project;

	public void load(Project proj) {
		this.m_project = proj;
		project_name.setText(proj.getProjectName());
		description.setText(proj.getDescription());
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		try{
			due_date.setText(df.format(proj.getDue_date()));
		}catch(Exception e){}
		if(proj.isImportant()){
			priority.setText("High Priority");
		}else{
			priority.setText("Normal Priority");
		}
	}
}
