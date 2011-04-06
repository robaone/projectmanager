package com.robaone.gwt.projectmanager.client.ui.project.goals;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;

public class GoalEditUi extends Composite {

	private static GoalEditUiUiBinder uiBinder = GWT
	.create(GoalEditUiUiBinder.class);

	interface GoalEditUiUiBinder extends UiBinder<Widget, GoalEditUi> {
	}

	@UiField TextBox name;
	@UiField SimplePanel dateboxpanel;
	@UiField TextBox status;
	@UiField Button save;
	@UiField Button delete;
	DateBox duedate;
	private ProjectGoal m_data;
	public GoalEditUi(ProjectGoal data,Project project) {
		initWidget(uiBinder.createAndBindUi(this));
		duedate = new DateBox();
		duedate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
		dateboxpanel.setWidget(duedate);
		this.m_data = data;
		if(this.m_data == null){
			this.m_data = new ProjectGoal();
			this.m_data.setProjectId(project.getId());
		}
		name.setText(data.getName());
		duedate.setValue(data.getDueDate());
		status.setText(data.getStatus());
	}

	@UiHandler("save")
	public void handleSave(ClickEvent event){
		m_data.setName(name.getText());
		m_data.setDueDate(duedate.getValue());
		m_data.setStatus(status.getText());

		ProjectManager.dataService.saveProjectGoal(m_data,new AsyncCallback<DataServiceResponse<ProjectGoal>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<ProjectGoal> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						ProjectGoal goal = result.getData(0);
						m_data = goal;
						ProjectGoalState.view(GoalEditUi.this);
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

	@UiHandler("delete")
	public void handleDelete(ClickEvent event){
		if(Window.confirm("Are you sure?")){
			ProjectManager.dataService.deleteProjectGoal(m_data,new AsyncCallback<DataServiceResponse<ProjectGoal>>(){

				@Override
				public void onFailure(Throwable caught) {
					showError(caught.getMessage());
				}

				@Override
				public void onSuccess(DataServiceResponse<ProjectGoal> result) {
					try{
						if(result.getStatus() == ProjectConstants.OK){
							ProjectGoalState.remove(GoalEditUi.this);
						}else{
							new Exception(result.getError());
						}
					}catch(Exception e){
						onFailure(e);
					}
				}

			});
		}
	}

	public ProjectGoal getData() {
		return this.m_data;
	}
}
