package com.robaone.gwt.projectmanager.client.ui.project.goals;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;

public class GoalViewUi extends Composite {

	private static GoalViewUiUiBinder uiBinder = GWT
			.create(GoalViewUiUiBinder.class);

	interface GoalViewUiUiBinder extends UiBinder<Widget, GoalViewUi> {
	}
	@UiField InlineLabel name;
	@UiField InlineLabel datebox;
	@UiField InlineLabel status;
	@UiField Button edit;
	ProjectGoal m_data;
	public GoalViewUi(ProjectGoal data) {
		initWidget(uiBinder.createAndBindUi(this));
		m_data = data;
		name.setText(data.getName());
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		try{datebox.setText(df.format(data.getDueDate()));}catch(Exception e){}
		status.setText(data.getStatus());
	}

	@UiHandler("edit")
	public void handleEdit(ClickEvent event){
		ProjectGoalState.edit(this);
	}
}
