package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class ProjectUI extends Composite {
	private Label projectName;
	private boolean project_hover = false;
	private boolean target_date_hover = false;
	private Label dateview;
	public ProjectUI(final MainContent main) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
		Label lblProject = new Label("Project: ");
		horizontalPanel.add(lblProject);
		
		projectName = new Label("New label");
		horizontalPanel.add(projectName);
		
		final TextBox editname = new TextBox();
		
		final Button btnEdit = new Button("Edit");
		final Button btnCancel = new Button("Cancel");
		final Button btnSave = new Button("Save");
		btnEdit.addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(MouseOverEvent event) {
				project_hover = true;
			}
			
		});
		btnEdit.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				project_hover = false;
				btnEdit.setVisible(false);
			}
			
		});
		btnEdit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(projectName);
				horizontalPanel.insert(editname, index);
				editname.setValue(projectName.getText());
				horizontalPanel.remove(projectName);
				btnEdit.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(editname);
				horizontalPanel.insert(projectName, index);
				horizontalPanel.remove(editname);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				projectName.setText(editname.getValue());
				try{
					Widget w = main.getDecoratedTabPanel().getTabWidget(main.getDecoratedTabPanel().getTabIndex(ProjectUI.this));
					if(w instanceof Label){
						((Label)w).setText(projectName.getText());
					}
				}catch(Exception e){
					
				}
			}
		});
		btnCancel.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(editname);
				horizontalPanel.insert(projectName, index);
				horizontalPanel.remove(editname);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
			}
			
		});
		horizontalPanel.add(btnEdit);
		btnEdit.setVisible(false);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
		horizontalPanel.add(btnSave);
		
		horizontalPanel.add(btnCancel);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_1);
		
		Label lblTargetDate = new Label("Target date:");
		horizontalPanel_1.add(lblTargetDate);
		
		dateview = new Label("New label");
		horizontalPanel_1.add(dateview);
		final DateBox edit_due_date = new DateBox();
		
		Button btnEdit_1 = new Button("Edit");
		horizontalPanel_1.add(btnEdit_1);
		
		Button btnSave_1 = new Button("Save");
		horizontalPanel_1.add(btnSave_1);
		
		Button btnCancel_1 = new Button("Cancel");
		horizontalPanel_1.add(btnCancel_1);
		
		btnEdit_1.addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(MouseOverEvent event) {
				target_date_hover = true;
			}
			
		});
		btnEdit_1.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				target_date_hover = false;
				btnEdit.setVisible(false);
			}
			
		});
		btnEdit_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(dateview);
				horizontalPanel.insert(edit_due_date, index);
				
				//edit_due_date.setValue(dateview.getText());
				horizontalPanel.remove(projectName);
				btnEdit.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(editname);
				horizontalPanel.insert(projectName, index);
				horizontalPanel.remove(editname);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				projectName.setText(editname.getValue());
				try{
					Widget w = main.getDecoratedTabPanel().getTabWidget(main.getDecoratedTabPanel().getTabIndex(ProjectUI.this));
					if(w instanceof Label){
						((Label)w).setText(projectName.getText());
					}
				}catch(Exception e){
					
				}
			}
		});
		btnCancel.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int index = horizontalPanel.getWidgetIndex(editname);
				horizontalPanel.insert(projectName, index);
				horizontalPanel.remove(editname);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
			}
			
		});
		getProjectName().addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(MouseOverEvent event) {
				btnEdit.setVisible(true);
			}
			
		});
		
		getProjectName().addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				Timer t = new Timer(){

					@Override
					public void run() {
						if(project_hover == false)
							btnEdit.setVisible(false);
					}
					
				};
				t.schedule(1000);
			}
			
		});
	}

	public Label getProjectName() {
		return projectName;
	}
	public Label getDateview() {
		return dateview;
	}
}
