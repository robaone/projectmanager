package com.robaone.gwt.projectmanager.client.ui.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.inputitem.InputItemHandler;
import com.robaone.gwt.projectmanager.client.ui.inputitem.InputItemUi;
import com.robaone.gwt.projectmanager.client.ui.orderedlist.ListItem;
import com.robaone.gwt.projectmanager.client.ui.orderedlist.OrderedList;
import com.robaone.gwt.projectmanager.shared.SharedFieldVerifier;

public class EditProjectUi extends Composite {

	private static EditProjectUiUiBinder uiBinder = GWT
	.create(EditProjectUiUiBinder.class);

	interface EditProjectUiUiBinder extends UiBinder<Widget, EditProjectUi> {
	}
	interface NewProjectStyle extends CssResource {
		String tagit();
		String tagit_choice();
	}

	@UiField NewProjectStyle style;
	@UiField InlineLabel title;
	MainContent main;
	public EditProjectUi(MainContent m) {
		initWidget(uiBinder.createAndBindUi(this));
		main = m;
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		due_date.setFormat(new DateBox.DefaultFormat(df));
		error.setVisible(false);
		project_name_error.setVisible(false);
		date_holder.setWidget(due_date);
		tag_holder.setWidget(tags);
		assignment_holder.setWidget(assignments);
		ListItem entry = new ListItem();
		entry.setStyleName(style.tagit_choice());
		entry.add(tag_entry);
		tags.add(entry);
		entry = new ListItem();
		entry.setStyleName(style.tagit_choice());
		entry.add(assignment_entry);
		assignments.add(entry);
		assignments.setStyleName(style.tagit());
		tags.setStyleName(style.tagit());
		assignment_entry.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode() == '\r'){
					addAssignment();
				}
			}

		});

		tag_entry.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode() == '\r'){
					addTag();
				}

			}

		});
	}
	public void setTitle(String str){
		this.title.setText(str);
	}
	protected void addTag() {
		if(SharedFieldVerifier.isValidName(tag_entry.getText())){
			final ListItem newitem = new ListItem();
			newitem.setStyleName(style.tagit_choice());
			class DeleteHandler implements InputItemHandler {

				@Override
				public void delete(InputItemUi inputItemUi) {
					int index = tags.getWidgetIndex(newitem);
					try{
						tags.remove(index);
					}catch(Exception e){
						e.printStackTrace();
					}
				}

			}
			InputItemUi item = new InputItemUi(new DeleteHandler());
			item.setText(tag_entry.getText().trim());
			newitem.add(item);
			tags.insert(newitem, tags.getWidgetCount()-1);
			tag_entry.setText("");
		}
	}
	protected void addAssignment() {
		if(SharedFieldVerifier.isValidName(assignment_entry.getText())){
			final ListItem newitem = new ListItem();
			newitem.setStyleName(style.tagit_choice());
			class DeleteHandler implements InputItemHandler{

				@Override
				public void delete(InputItemUi inputItemUi) {
					int index = assignments.getWidgetIndex(newitem);
					try{
						assignments.remove(index);
					}catch(Exception e){
						e.printStackTrace();
					}
				}

			}
			InputItemUi item = new InputItemUi(new DeleteHandler());
			item.setText(assignment_entry.getText().trim());
			newitem.add(item);
			assignments.insert(newitem, assignments.getWidgetCount()-1);
			assignment_entry.setText("");
		}
	}
	TextBox assignment_entry = new TextBox();
	TextBox tag_entry = new TextBox();
	DateBox due_date = new DateBox();
	OrderedList assignments = new OrderedList();
	OrderedList tags = new OrderedList();
	@UiField TextBox project_name;
	@UiField TextArea description;
	@UiField SimplePanel date_holder;
	@UiField TextBox est_hours;
	@UiField CheckBox important;
	@UiField Button create_button;
	@UiField SimplePanel tag_holder;
	@UiField SimplePanel assignment_holder;
	@UiField Label error;
	@UiField InlineLabel project_name_error;
	@UiField TextBox status;
	private boolean isnew = true;
	private Project m_data;

	@UiHandler("create_button")
	public void handleSaveProject(ClickEvent event){
		Project project = this.m_data;
		project.setProjectName(this.project_name.getText());
		project.setDescription(this.description.getText());
		project.setDue_date(this.due_date.getValue());
		project.setStatus(this.status.getText());
		if(SharedFieldVerifier.isNumber(this.est_hours.getText())){
			Double d = new Double(this.est_hours.getText());
			project.setEst_hours(d.doubleValue());
		}
		project.setTags(this.getTags());
		project.setAssignments(this.getAssignments());
		project.setImportant(this.important.getValue().booleanValue());
		ProjectManager.dataService.saveProject(project,new AsyncCallback<DataServiceResponse<Project>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<Project> result) {
				try{
					if(result.getStatus() == 0){
						Project proj = result.getData(0);
						int index = main.getDecoratedTabPanel().getTabIndex(EditProjectUi.this);
						if(isnew){
							main.getDecoratedTabPanel().removeTab(index);
							ProjectUi2 project = new ProjectUi2(main);
							index = main.getDecoratedTabPanel().addTab(project, project_name.getValue(),true,null);
							main.getDecoratedTabPanel().selectTab(index);
							project.load(proj);
						}else{
							ProjectState.goView(EditProjectUi.this,result.getData(0));
						}
					}else if(result.getStatus() == ProjectConstants.FIELD_VERIFICATION_ERROR){
						showErrorsforFields(result);
					}else{
						showError(result.getError());
					}
				}catch(Exception e){
					showError(e.getMessage());
				}
			}

		});
	}
	protected void showErrorsforFields(DataServiceResponse<Project> result) {
		String error_msg = null;
		error_msg = result.getFieldError(Project.PROJECTNAME);
		if(error_msg == null) project_name_error.setVisible(false);
		else {project_name_error.setText(error_msg); project_name_error.setVisible(true);}
	}
	protected void showError(String message) {
		error.setText(message);
		error.setVisible(true);
	}
	private String[] getAssignments() {
		java.util.Vector<String> retval = new java.util.Vector<String>();
		for(int i = 0; i < assignments.getWidgetCount();i++){
			Widget w = assignments.getWidget(i);
			if(w instanceof ListItem){
				Widget w2 = ((ListItem)w).getWidget(0);
				if(w2 instanceof InputItemUi){
					InputItemUi input = (InputItemUi)w2;
					retval.add(input.getText());
				}
			}
		}
		return retval.toArray(new String[0]);
	}
	private String[] getTags() {
		java.util.Vector<String> retval = new java.util.Vector<String>();
		for(int i = 0; i < tags.getWidgetCount();i++){
			Widget w = tags.getWidget(i);
			if(w instanceof ListItem){
				Widget w2 = ((ListItem)w).getWidget(0);
				if(w2 instanceof InputItemUi){
					InputItemUi input = (InputItemUi)w2;
					retval.add(input.getText());
				}
			}
		}
		return retval.toArray(new String[0]);
	}
	public void load(Project project) {
		this.isnew = false;
		this.project_name.setText(project.getProjectName());
		this.due_date.setValue(project.getDue_date());
		this.description.setText(project.getDescription());
		this.m_data = project;
		this.est_hours.setText(project.getEst_hours()+"");
		this.important.setValue(project.isImportant());
		this.status.setText(project.getStatus());
	}
	public MainContent getMainContent() {
		return this.main;
	}
}
