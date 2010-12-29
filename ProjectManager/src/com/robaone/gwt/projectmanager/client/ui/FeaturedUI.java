package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FeaturedUI extends Composite implements HasText {

	private static FeaturedUIUiBinder uiBinder = GWT
			.create(FeaturedUIUiBinder.class);

	@UiField Label title;
	@UiField Label contractor1;
	@UiField Label contractor2;
	@UiField FlowPanel f1;
	@UiField FlowPanel f2;
	interface FeaturedUIUiBinder extends UiBinder<Widget, FeaturedUI> {
	}

	public FeaturedUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	

	public FeaturedUI(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}

	


	@Override
	public String getText() {
		return title.getText();
	}



	@Override
	public void setText(String text) {
		title.setText(text);
	}

	
}
