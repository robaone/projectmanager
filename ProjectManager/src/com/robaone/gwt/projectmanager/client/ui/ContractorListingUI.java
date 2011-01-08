package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.robaone.gwt.projectmanager.client.ProjectManager;

public class ContractorListingUI extends Composite implements HasText {
	private ContractorsbyCategoryUI m_categories = null;
	private static ContractorListingUIUiBinder uiBinder = GWT
			.create(ContractorListingUIUiBinder.class);

	interface ContractorListingUIUiBinder extends
			UiBinder<HTMLPanel, ContractorListingUI> {
	}

	public ContractorListingUI() {
		initWidget(uiBinder.createAndBindUi(this));
		zip_entry.setVisible(false);
		button.setText("Submit");
		initialize();
	}

	@UiField
	Button button;
	@UiField HTMLPanel zip_entry;
	@UiField TextBox zipcode;
	@UiField FlowPanel listing;
	@UiField VerticalPanel list;

	public ContractorListingUI(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		zip_entry.setVisible(false);
		button.setText(firstName);
		initialize();
	}
	private void initialize() {
		ProjectManager.dataService.getCurrentZipCode(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				if(result != null && result.length() == 5){
					showListing(result);
				}else{
					zip_entry.setVisible(true);
				}
			}
			
		});
	}


	@UiHandler("button")
	void onClick(ClickEvent e) {
		ProjectManager.dataService.setZipcode(zipcode.getValue(),new AsyncCallback<Boolean>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
				showListing(zipcode.getValue());
			}
			
		});
		
	}

	protected void showError(String message) {
		// TODO Auto-generated method stub
		
	}

	private void showListing(String zipcode) {
		listing.remove(zip_entry);
		if(this.m_categories == null){
			this.m_categories = new ContractorsbyCategoryUI(zipcode);
		}
		if(this.m_categories.isAttached() == false){
			listing.add(this.m_categories);
		}
		
		ProjectManager.change_handler.handleChange(History.getToken());
	}
	
	public FlowPanel getListing(){
		return this.listing;
	}
	
	public VerticalPanel getList(){
		return this.list;
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
