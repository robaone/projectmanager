package com.robaone.gwt.projectmanager.client.ui.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.project.goals.GoalsListUi;
import com.robaone.gwt.projectmanager.client.ui.tasks.TasksList;

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
	@UiField Button edit;
	@UiField SimplePanel tasks;
	private Project m_project;
	private String m_projectid;

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
		GoalsListUi glist = new GoalsListUi(proj);
		tasks.setWidget(glist);
	}
	
	@UiHandler("edit")
	public void handleEdit(ClickEvent event){
		ProjectState.goEdit(this);
	}

	public MainContent getMainContent() {
		return main;
		
	}

	public String getProjectId() {
		return this.m_projectid;
	}

	public Project getData() {
		return this.m_project;
	}
}
