package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.robaone.gwt.projectmanager.client.ui.inputitem.InputItemHandler;
import com.robaone.gwt.projectmanager.client.ui.inputitem.InputItemUi;
import com.robaone.gwt.projectmanager.client.ui.orderedlist.ListItem;
import com.robaone.gwt.projectmanager.client.ui.orderedlist.OrderedList;
import com.robaone.gwt.projectmanager.shared.SharedFieldVerifier;

public class NewProjectUI2 extends Composite {

	private static NewProjectUI2UiBinder uiBinder = GWT
			.create(NewProjectUI2UiBinder.class);

	interface NewProjectUI2UiBinder extends UiBinder<Widget, NewProjectUI2> {
	}
	interface NewProjectStyle extends CssResource {
		String tagit();
		String tagit_choice();
	}
	
	@UiField NewProjectStyle style;
	public NewProjectUI2() {
		initWidget(uiBinder.createAndBindUi(this));
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		due_date.setFormat(new DateBox.DefaultFormat(df));
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
}
