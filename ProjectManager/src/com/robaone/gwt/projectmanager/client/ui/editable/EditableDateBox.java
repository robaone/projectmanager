package com.robaone.gwt.projectmanager.client.ui.editable;

import java.util.Date;

import com.google.appengine.api.images.Image.Format;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class EditableDateBox extends EditableInput {
	private String pattern = "MM/dd/yyyy";
	public EditableDateBox(String label,String content, EditableSaveHandler handler){
		super(label,content,handler);
	}

	@Override
	protected Widget getEditableWidget(String string) {
		DateBox retval = new DateBox();
		retval.setFormat(new DateBox.DefaultFormat
				(DateTimeFormat.getFormat(pattern)));
		try{
			/*
			 * Convert the string to a date
			 * the string format should be MM/dd/yyyy
			 */
			DateTimeFormat df = DateTimeFormat.getFormat(pattern);
			if(string != null && string.length() > 0){
				retval.setValue(df.parse(string));
			}
		}catch(Exception e){
			showError(e.getMessage());
		}
		return retval;
	}

	@Override
	protected void updateContents(Widget editable) {
		if(editable instanceof DateBox){
			Date value = ((DateBox)editable).getValue();
			DateTimeFormat df = DateTimeFormat.getFormat(pattern);
			String str = df.format(value);
			this.setText(str);
		}
	}
	public void setDatePattern(String pttrn){
		this.pattern = pttrn;
	}
}
