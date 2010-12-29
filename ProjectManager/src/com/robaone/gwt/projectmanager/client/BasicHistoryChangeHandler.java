package com.robaone.gwt.projectmanager.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ui.TasksList;

public class BasicHistoryChangeHandler implements ValueChangeHandler<String> {

	private ProjectManager parent;

	public BasicHistoryChangeHandler(ProjectManager projectManager) {
		this.parent = projectManager;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		System.out.println("New history value = "+event.getValue());
		this.handleChange(event.getValue());
	}

	public void handleChange(String token) {
		System.out.println("History token = "+token);
		if(token.equals("register")){
			parent.showRegister();
		}else if(token.startsWith("task=")){
			Widget w = ProjectManager.getSection(ProjectManager.TASKS_SECTION);
			if(w instanceof TasksList){
				TasksList t = (TasksList)w;
				String[] tokens = token.split("=");
				if(tokens.length > 1){
					if(tokens[1].equals(TasksList.TASK.ALERT.toString())){
						t.showTask(TasksList.TASK.ALERT);
					}else if(tokens[1].equals(TasksList.TASK.JOB.toString())){
						t.showTask(TasksList.TASK.JOB);
					}else if(tokens[1].equals(TasksList.TASK.NOTICE.toString())){
						t.showTask(TasksList.TASK.NOTICE);
					}else if(tokens[1].equals(TasksList.TASK.PROJECT.toString())){
						t.showTask(TasksList.TASK.PROJECT);
					}else if(tokens[1].equals(TasksList.TASK.SEARCH.toString())){
						t.showTask(TasksList.TASK.SEARCH);
					} 
				}
			}
		}
	}

}
