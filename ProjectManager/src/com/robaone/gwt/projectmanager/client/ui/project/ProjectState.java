package com.robaone.gwt.projectmanager.client.ui.project;

import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Project;

public class ProjectState {

	public static void goEdit(ProjectUi2 project) {
		int index = project.getMainContent().getDecoratedTabPanel().getTabIndex(project);
		EditProjectUi proj = new EditProjectUi(project.getMainContent());
		proj.load(project.getData());
		proj.setTitle("Edit Project");
		project.getMainContent().getDecoratedTabPanel().setWidget(index,proj);
		project.getMainContent().getDecoratedTabPanel().selectTab(index);
	}

	public static void goView(NewProjectUI2 project,Project data) {
		int index = project.getMainContent().getDecoratedTabPanel().getTabIndex(project);
		ProjectUi2 proj = new ProjectUi2(project.getMainContent());
		proj.load(data);
		project.getMainContent().getDecoratedTabPanel().setWidget(index, proj);
		project.getMainContent().getDecoratedTabPanel().selectTab(index);
	}

	public static void goView(EditProjectUi project, Project data) {
		int index = project.getMainContent().getDecoratedTabPanel().getTabIndex(project);
		ProjectUi2 proj = new ProjectUi2(project.getMainContent());
		proj.load(data);
		project.getMainContent().getDecoratedTabPanel().setWidget(index, proj);
		project.getMainContent().getDecoratedTabPanel().selectTab(index);
	}

}
