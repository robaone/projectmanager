package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class TasksList extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private Image alert_icon = new Image();
	private InlineLabel alerts = new InlineLabel();
	private Image notice_icon = new Image();
	private InlineLabel notices = new InlineLabel();
	private Image job_icon = new Image();
	private InlineLabel jobs = new InlineLabel();
	private Image project_icon = new Image();
	private InlineLabel projects = new InlineLabel();
	private MainContent m_main_content;
	public static enum TASK { ALERT, NOTICE , JOB, PROJECT, };
	public TasksList(){
		FlexTable flxtbl1 = new FlexTable();
		FlowPanel flwpnl2 = new FlowPanel();
		FlowPanel flwpnl5 = new FlowPanel();
		FlowPanel flwpnl8 = new FlowPanel();
		FlowPanel flwpnl11 = new FlowPanel();
		FlowPanel flwpnl13 = new FlowPanel();
		InlineLabel nlnlbl15 = new InlineLabel();
		InlineLabel nlnlbl16 = new InlineLabel();
		flxtbl1.getElement().setAttribute("style","width: 100%;");
		flxtbl1.getElement().setAttribute("cellspacing","0");
		flwpnl2.setStyleName("task_col1");
		alert_icon.getElement().setAttribute("alt","alert");
		alert_icon.getElement().setAttribute("src",GWT.getModuleBaseURL()+"alert.png");
		alert_icon.getElement().setAttribute("style","width: 33px; height: 33px");
		flwpnl5.setStyleName("task_col1");
		notice_icon.getElement().setAttribute("alt","notice");
		notice_icon.getElement().setAttribute("src",GWT.getModuleBaseURL()+"notice.png");
		notice_icon.getElement().setAttribute("style","width: 33px; height: 33px");
		flwpnl8.setStyleName("task_col1");
		job_icon.getElement().setAttribute("alt","job");
		job_icon.getElement().setAttribute("src",GWT.getModuleBaseURL()+"job.png");
		job_icon.getElement().setAttribute("style","width: 33px; height: 34px");
		flwpnl11.setStyleName("task_col1");
		project_icon.getElement().setAttribute("alt","project");
		project_icon.getElement().setAttribute("src",GWT.getModuleBaseURL()+"project.png");
		project_icon.getElement().setAttribute("style","width: 33px; height: 33px");
		alerts.setText("Alerts (0)");
		notices.setText("Notices (0)");
		jobs.setText("Jobs (0)");
		projects.setText("Projects (");
		nlnlbl15.setText("0");
		nlnlbl16.setText(")");
		flwpnl2.add(alert_icon);
		flwpnl5.add(notice_icon);
		flwpnl8.add(job_icon);
		flwpnl11.add(project_icon);
		flwpnl13.add(projects);
		flwpnl13.add(nlnlbl15);
		flwpnl13.add(nlnlbl16);
		flwpnl0.add(flxtbl1);
		flxtbl1.setWidget(0,0,flwpnl2);
		flxtbl1.setWidget(0,1,alerts);
		flxtbl1.setWidget(1,0,flwpnl5);
		flxtbl1.setWidget(1,1,notices);
		flxtbl1.setWidget(2,0,flwpnl8);
		flxtbl1.setWidget(2,1,jobs);
		flxtbl1.setWidget(3,0,flwpnl11);
		flxtbl1.setWidget(3,1,flwpnl13);
		this.initWidget(flwpnl0);
		
		class Action implements ClickHandler {
			private TASK action;
			public Action(TASK task) {
				action = task;
			}

			@Override
			public void onClick(ClickEvent event) {
				showTask(action);
			}
			
		}
		this.job_icon.addClickHandler(new Action(TASK.JOB));
		this.jobs.addClickHandler(new Action(TASK.JOB));
		this.alert_icon.addClickHandler(new Action(TASK.ALERT));
		this.alerts.addClickHandler(new Action(TASK.ALERT));
		this.notice_icon.addClickHandler(new Action(TASK.NOTICE));
		this.notices.addClickHandler(new Action(TASK.NOTICE));
		this.project_icon.addClickHandler(new Action(TASK.PROJECT));
		this.projects.addClickHandler(new Action(TASK.PROJECT));
	}
	public void showTask(TASK action) {
		System.out.println("Show task "+action);
		this.m_main_content.showSection(action);
	}
	public void load() throws Exception {
	}
	public Image getAlert_icon(){
		return this.alert_icon;
	}
	public InlineLabel getAlerts(){
		return this.alerts;
	}
	public Image getNotice_icon(){
		return this.notice_icon;
	}
	public InlineLabel getNotices(){
		return this.notices;
	}
	public Image getJob_icon(){
		return this.job_icon;
	}
	public InlineLabel getJobs(){
		return this.jobs;
	}
	public Image getProject_icon(){
		return this.project_icon;
	}
	public InlineLabel getProjects(){
		return this.projects;
	}
	public void setMainContent(MainContent main) {
		this.m_main_content = main;
	}
}