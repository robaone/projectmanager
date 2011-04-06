package com.robaone.gwt.projectmanager.client.ui.project.goals;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProjectGoalState {

	public static void view(GoalEditUi goalEditUi) {
		GoalViewUi gview = new GoalViewUi(goalEditUi.getData());
		if(goalEditUi.getParent() instanceof SimplePanel){
			SimplePanel p = (SimplePanel)goalEditUi.getParent();
			p.setWidget(gview);
		}else if(goalEditUi.getParent() instanceof FlowPanel){
			FlowPanel p = (FlowPanel)goalEditUi.getParent();
			p.clear();
			p.add(gview);
		}
	}

	public static void remove(GoalEditUi goalEditUi) {
		Widget p = goalEditUi.getParent().getParent();
		if(p instanceof VerticalPanel){
			((VerticalPanel)p).remove(goalEditUi.getParent());
		}
	}

	public static void edit(GoalViewUi goalViewUi) {
		GoalEditUi gedit = new GoalEditUi(goalViewUi.m_data,null);
		Widget w = goalViewUi.getParent();
		if(w instanceof SimplePanel){
			((SimplePanel)w).setWidget(gedit);
		}else if(w instanceof FlowPanel){
			((FlowPanel)w).clear();
			((FlowPanel)w).add(gedit);
		}
	}

}
