package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class NewProjectUI extends Composite {

	public NewProjectUI(final MainContent main) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		FormPanel formPanel = new FormPanel();
		verticalPanel.add(formPanel);
		
		Grid grid = new Grid(2, 2);
		formPanel.setWidget(grid);
		grid.setSize("100%", "100%");
		
		Label lblProjectName = new Label("Project Name:");
		grid.setWidget(0, 0, lblProjectName);
		
		final TextBox project_name = new TextBox();
		grid.setWidget(0, 1, project_name);
		
		Button btnSave = new Button("Save");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int index = main.getDecoratedTabPanel().getTabIndex(NewProjectUI.this);
				main.getDecoratedTabPanel().removeTab(index);
				ProjectUI project = new ProjectUI(main);
				index = main.getDecoratedTabPanel().addTab(project, project_name.getValue(),true,null);
				main.getDecoratedTabPanel().selectTab(index);
			}
		});
		grid.setWidget(1, 1, btnSave);
	}

}
