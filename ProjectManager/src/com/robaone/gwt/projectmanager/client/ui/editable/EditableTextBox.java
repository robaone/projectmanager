package com.robaone.gwt.projectmanager.client.ui.editable;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditableTextBox extends EditableInput {
	public EditableTextBox(String label,String content, EditableSaveHandler handler){
		super(label,content,handler);
	}
	@Override
	protected void updateContents(Widget editable) {
		if(editable instanceof TextBox){
			this.setText(((TextBox)editable).getValue());
		}
	}
	@Override
	protected Widget getEditableWidget(String string){
		TextBox t =  new TextBox();
		t.setText(string);
		return t;
	}
}
