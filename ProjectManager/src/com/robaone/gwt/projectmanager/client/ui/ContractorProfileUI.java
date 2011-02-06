package com.robaone.gwt.projectmanager.client.ui;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorListing;

public class ContractorProfileUI extends Composite {

	private static ContractorProfileUIUiBinder uiBinder = GWT
			.create(ContractorProfileUIUiBinder.class);

	interface ContractorProfileUIUiBinder extends
			UiBinder<Widget, ContractorProfileUI> {
	}

	public ContractorProfileUI() {
		ProjectManager.writeLog("Creating ContractorProfileUI()");
		initWidget(uiBinder.createAndBindUi(this));
		/*
		
		*/
	}

	@UiField Image logo;
	@UiField HTML header;
	@UiField HTML info;
	@UiField FlowPanel comment_flow;

	public void load(int id){
		ProjectManager.writeLog("calling ContractorProfileUI.load("+id+")");
		ProjectManager.dataService.getContractorListing(id, new AsyncCallback<ContractorListing>(){

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				ProjectManager.writeLog(caught);
			}

			@Override
			public void onSuccess(ContractorListing result) {
				ProjectManager.writeLog("Building contractor page");
				
				FlowPanel p = new FlowPanel();
				Label l = new Label(result.getTitle());
				p.add(l);
				header.setHTML(p.toString());
				info.setHTML(result.getContent());
			}
			
		});
	}

}
