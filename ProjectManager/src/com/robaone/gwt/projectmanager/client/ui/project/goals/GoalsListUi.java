package com.robaone.gwt.projectmanager.client.ui.project.goals;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;

public class GoalsListUi extends Composite {

	private static GoalsListUiUiBinder uiBinder = GWT
			.create(GoalsListUiUiBinder.class);

	interface GoalsListUiUiBinder extends UiBinder<Widget, GoalsListUi> {
	}

	@UiField Button newgoal;
	@UiField VerticalPanel list;
	private Project m_project;
	public GoalsListUi(Project proj) {
		initWidget(uiBinder.createAndBindUi(this));
		this.m_project = proj;
		load(proj);
	}

	public void load(Project proj) {
		this.m_project = proj;
		ProjectManager.dataService.getGoalsForProject(proj,new AsyncCallback<DataServiceResponse<ProjectGoal>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<ProjectGoal> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						for(int i = 0; i < result.getDataCount();i++){
							GoalViewUi g = new GoalViewUi(result.getData(i));
							SimplePanel p = new SimplePanel();
							p.setWidget(g);
							list.add(p);
						}
					}else{
						throw new Exception(result.getError());
					}
				}catch(Exception e){
					onFailure(e);
				}
			}
			
		});
	}

	protected void showError(String message) {
		Window.alert(message);
	}

	@UiHandler("newgoal")
	public void handleNew(ClickEvent event){
		ProjectGoal g = new ProjectGoal();
		g.setProjectId(m_project.getId());
		GoalEditUi gedit = new GoalEditUi(g,m_project);
		SimplePanel p = new SimplePanel();
		p.setWidget(gedit);
		list.add(p);
	}
}
