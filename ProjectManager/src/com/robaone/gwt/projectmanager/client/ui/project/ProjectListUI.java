package com.robaone.gwt.projectmanager.client.ui.project;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.feed.Feed;

public class ProjectListUI extends Composite {
	private Button btnNewProject;
	private Label lblAll;
	private Label lblHighPriority;
	private Label lblNormalPriority;
	private Label lblLowPriority;
	private Label lblCompleted;
	private Label lblCancelled;
	Label selected = null;
	private enum FILTER { ALL , HIGH, NORMAL , LOW , COMPLETED , CANCELLED };
	public ProjectListUI(final MainContent main){
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("project_hpanel");
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellVerticalAlignment(horizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblAll = new Label("All");
		lblAll.addStyleName("priority_label");
		horizontalPanel.add(lblAll);
		horizontalPanel.setCellVerticalAlignment(lblAll, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblHighPriority = new Label("High Priority");
		lblHighPriority.addStyleName("priority_label");
		horizontalPanel.add(lblHighPriority);
		horizontalPanel.setCellVerticalAlignment(lblHighPriority, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblNormalPriority = new Label("Normal Priority");
		lblNormalPriority.addStyleName("priority_label");
		horizontalPanel.add(lblNormalPriority);
		horizontalPanel.setCellVerticalAlignment(lblNormalPriority, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblLowPriority = new Label("Low Priority");
		lblLowPriority.addStyleName("priority_label");
		horizontalPanel.add(lblLowPriority);
		horizontalPanel.setCellVerticalAlignment(lblLowPriority, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblCompleted = new Label("Completed");
		lblCompleted.addStyleName("priority_label");
		horizontalPanel.add(lblCompleted);
		horizontalPanel.setCellVerticalAlignment(lblCompleted, HasVerticalAlignment.ALIGN_MIDDLE);
		
		lblCancelled = new Label("Cancelled");
		lblCancelled.setStyleName("priority_label");
		horizontalPanel.add(lblCancelled);
		horizontalPanel.setCellVerticalAlignment(lblCancelled, HasVerticalAlignment.ALIGN_MIDDLE);
		
		btnNewProject = new Button("New Project");
		horizontalPanel.add(btnNewProject);
		
		Feed feed = new Feed();
		verticalPanel.add(feed);
		
		getBtnNewProject().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				NewProjectUI2 newproject = new NewProjectUI2(main);
				int index = main.getDecoratedTabPanel().addTab(newproject, "New Project",true,null);
				main.getDecoratedTabPanel().selectTab(index);
			}
			
		});
		
		class filterClickHandler implements ClickHandler{

			private FILTER filter;

			public filterClickHandler(FILTER all) {
				this.filter = all;
			}

			@Override
			public void onClick(ClickEvent event) {
				ProjectListUI.this.selectFilter(filter);
			}
			
		}
		this.getLblAll().addClickHandler(new filterClickHandler(FILTER.ALL));
		this.getLblCancelled().addClickHandler(new filterClickHandler(FILTER.CANCELLED));
		this.getLblCompleted().addClickHandler(new filterClickHandler(FILTER.COMPLETED));
		this.getLblHighPriority().addClickHandler(new filterClickHandler(FILTER.HIGH));
		this.getLblLowPriority().addClickHandler(new filterClickHandler(FILTER.LOW));
		this.getLblNormalPriority().addClickHandler(new filterClickHandler(FILTER.NORMAL));
		
		this.selectFilter(FILTER.ALL);
	}
	public Button getBtnNewProject() {
		return btnNewProject;
	}
	
	private void selectFilter(FILTER filter){
		try{
			selected.removeStyleName("priority_label_selected");
			selected.addStyleName("priority_label");
		}catch(Exception e){}
		switch (filter){
		case ALL:
			selected = this.getLblAll();
			break;
		case HIGH:
			selected = this.getLblHighPriority();
			break;
		case NORMAL:
			selected = this.getLblNormalPriority();
			break;
		case LOW:
			selected = this.getLblLowPriority();
			break;
		case COMPLETED:
			selected = this.getLblCompleted();
			break;
		case CANCELLED:
			selected = this.getLblCancelled();
			break;
		}
		selected.removeStyleName("priority_label");
		selected.addStyleName("priority_label_selected");
	}
	protected Label getLblAll() {
		return lblAll;
	}
	protected Label getLblHighPriority() {
		return lblHighPriority;
	}
	protected Label getLblNormalPriority() {
		return lblNormalPriority;
	}
	protected Label getLblLowPriority() {
		return lblLowPriority;
	}
	protected Label getLblCompleted() {
		return lblCompleted;
	}
	protected Label getLblCancelled() {
		return lblCancelled;
	}
}
