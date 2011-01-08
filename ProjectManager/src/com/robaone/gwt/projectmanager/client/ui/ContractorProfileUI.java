package com.robaone.gwt.projectmanager.client.ui;

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

public class ContractorProfileUI extends Composite {

	private static ContractorProfileUIUiBinder uiBinder = GWT
			.create(ContractorProfileUIUiBinder.class);

	interface ContractorProfileUIUiBinder extends
			UiBinder<Widget, ContractorProfileUI> {
	}

	public ContractorProfileUI(int id) {
		initWidget(uiBinder.createAndBindUi(this));
		
		ProjectManager.dataService.getContractor(id, new AsyncCallback<Contractor>(){

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				
			}

			@Override
			public void onSuccess(Contractor result) {
				logo.setUrl(result.getLogo_url());
				FlowPanel p = new FlowPanel();
				Label l = new Label(result.getName());
				HTML h = new HTML(result.getSummary());
				p.add(l);
				p.add(h);
				header.setHTML(p.toString());
				info.setHTML(result.getInfo());
			}
			
		});
	}

	@UiField Image logo;
	@UiField HTML header;
	@UiField HTML info;
	@UiField FlowPanel comment_flow;


}
