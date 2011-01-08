package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.ContractorData;

public class ContractorListingInfoUI extends Composite implements HasText {

	private static ContractorListingInfoUIUiBinder uiBinder = GWT
			.create(ContractorListingInfoUIUiBinder.class);

	interface ContractorListingInfoUIUiBinder extends
			UiBinder<Widget, ContractorListingInfoUI> {
	}

	public ContractorListingInfoUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label name;
	@UiField Label location;
	@UiField FlexTable fields;

	public ContractorListingInfoUI(ContractorData data) {
		initWidget(uiBinder.createAndBindUi(this));
		name.setText(data.getName());
		location.setText(data.getLocation());
	}
	public Label getName(){
		return this.name;
	}
	
	public void setText(String text) {
		name.setText(text);
	}

	public String getText() {
		return name.getText();
	}

}
