package com.robaone.gwt.projectmanager.client.ui.editable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

abstract public class EditableInput extends Composite implements HasText {

	private static EditableInputUiBinder uiBinder = GWT
	.create(EditableInputUiBinder.class);

	interface EditableInputUiBinder extends UiBinder<HorizontalPanel, EditableInput> {
	}

	public EditableInput() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label label;
	@UiField Label text;
	@UiField Button saveButton;
	@UiField Button editButton;
	@UiField Button cancelButton;
	@UiField Label error;
	protected boolean hover;
	protected EditableSaveHandler save_handler;
	
	enum MODE { EDIT ,VIEW };
	public EditableInput(String lbl, String txt,EditableSaveHandler handler) {
		initWidget(uiBinder.createAndBindUi(this));
		label.setText(lbl);
		text.setText(txt);
		setMode(MODE.VIEW);
		hover = false;
		save_handler = handler;
	}

	private void setMode(MODE view) {
		switch(view){
		case EDIT:
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
			error.setVisible(false);
			Widget w = this.getEditableWidget(text.getText());
			HorizontalPanel hp = (HorizontalPanel)this.getWidget();
			int index = hp.getWidgetIndex(text);
			hp.insert(w, index);
			text.removeFromParent();
			break;
		case VIEW:
			editButton.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
			error.setVisible(false);
			break;
		}
	}
	abstract protected void updateContents(Widget editable);

	abstract protected Widget getEditableWidget(String string);
	@UiHandler("editButton")
	void handleEditClick(ClickEvent e) {
		setMode(MODE.EDIT);
	}

	@UiHandler("saveButton")
	void handleSaveClick(ClickEvent e){
		try {
			hideError();
			if(save_handler == null){
				this.updateLabel();
			}else{
				save_handler.save(this.getText(),this);
			}
		} catch (Exception e1) {
			showError(e1.getMessage());
		}
		
	}
	private void hideError() {
		error.setText("");
		error.setVisible(false);
	}

	protected void showError(String message) {
		error.setText(message);
		error.setVisible(true);
	}

	protected void updateLabel(){
		HorizontalPanel hp1 = (HorizontalPanel)this.getWidget();
		Widget ew = hp1.getWidget(1);
		this.updateContents(ew);
		hp1.remove(1);
		hp1.insert(text, 1);
		setMode(MODE.VIEW);
	}
	@UiHandler("cancelButton")
	void handleCancelClick(ClickEvent e){
		hideError();
		HorizontalPanel hp1 = (HorizontalPanel)this.getWidget();
		hp1.remove(1);
		hp1.insert(text, 1);
		setMode(MODE.VIEW);
	}

	@UiHandler("label")
	void handleMouseOver(MouseOverEvent e){
		if(!saveButton.isVisible()){
			editButton.setVisible(true);
		}
	}
	@UiHandler("text")
	void handleTextMouseOver(MouseOverEvent e){
		handleMouseOver(e);
	}
	@UiHandler("editButton")
	void handleEditMouseOver(MouseOverEvent e){
		hover = true;
	}
	@UiHandler("editButton")
	void handleEditMouseOut(MouseOutEvent e){
		hover = false;
		editButton.setVisible(false);
	}
	@UiHandler("label")
	void handleMouseOut(MouseOutEvent e){
		if(!saveButton.isVisible()){
			Timer t = new Timer(){

				@Override
				public void run() {
					if(hover == false){
						editButton.setVisible(false);
					}
				}

			};
			t.schedule(500);
		}
	}
	@UiHandler("text")
	void handleTextMouseOut(MouseOutEvent e){
		handleMouseOut(e);
	}
	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public void setText(String txt) {
		text.setText(txt);
	}
	public void setLabel(String lbl){
		label.setText(lbl);
	}
	public String getLabel(){
		return label.getText();
	}
}
