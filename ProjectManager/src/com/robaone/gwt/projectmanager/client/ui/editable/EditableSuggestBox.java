package com.robaone.gwt.projectmanager.client.ui.editable;

import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

public class EditableSuggestBox extends EditableInput {
	private SuggestBox m_suggestbox = new SuggestBox();
	
	public EditableSuggestBox(String label,String txt,EditableSaveHandler handler){
		super(label,txt,handler);
	}
	
	@Override
	protected Widget getEditableWidget(String string) {
		m_suggestbox.setValue(string);
		return m_suggestbox;
	}

	@Override
	protected void updateContents(Widget editable) {
		this.text.setText(m_suggestbox.getValue());
	}

}
