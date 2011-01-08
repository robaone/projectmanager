package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.ContractorData;

public class ContractorsbyCategoryUI extends Composite {
	private FlowPanel root = new FlowPanel();
	private FlexTable table = new FlexTable();
	private VerticalPanel list = new VerticalPanel();
	public ContractorsbyCategoryUI(final String zipcode) {
		this.initWidget(root);
		HTML header = new HTML("<h2>By Category</h2>");
		root.add(header);
		root.add(table);
		table.setWidth("100%");
		ProjectManager.dataService.getContractorCategories(zipcode,new AsyncCallback<DataServiceResponse<Category>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(DataServiceResponse<Category> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						// Show the categories.
						for(int i = 0; i < result.getDataCount();i++){
							final Category category = result.getData(i);
							int row = (i - (i % 3))/3;
							int col = i % 3;
							Anchor categorylabel = new Anchor(category.getName());
							categorylabel.setHref("javascript:void(0)");
							table.setWidget(row, col, categorylabel);
							categorylabel.addClickHandler(new ClickHandler(){

								@Override
								public void onClick(ClickEvent event) {
									/*
									 * Get the list and display it
									 */
									History.newItem("category="+category.getId(), true);
								}
								
							});
						}
					}else if(result.getStatus() == ProjectConstants.FIELD_VERIFICATION_ERROR){
						// Show zip code entry again.
					}else{
						throw new Exception(result.getError());
					}
				}catch(Exception e){
					onFailure(new Throwable(e));
				}
			}
			
		});
	}
	private void showError(String errormessage){
		
	}
}
