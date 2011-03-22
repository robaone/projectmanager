package com.robaone.gwt.projectmanager.client.ui.inputitem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class InputItemUi extends Composite implements HasText{

	private static InputItemUiUiBinder uiBinder = GWT
			.create(InputItemUiUiBinder.class);

	interface InputItemUiUiBinder extends UiBinder<Widget, InputItemUi> {
	}

	private InputItemHandler m_handler;

	public InputItemUi(InputItemHandler handler) {
		initWidget(uiBinder.createAndBindUi(this));
		this.m_handler = handler;
	}

	@UiField InlineLabel name;
	@UiField InlineLabel close;
	@Override
	public String getText() {
		return this.name.getText();
	}
	@Override
	public void setText(String text) {
		this.name.setText(text);
	}
	
	@UiHandler("close")
	public void handleClose(ClickEvent event){
		m_handler.delete(this);
	}
}
