package com.robaone.gwt.projectmanager.client.ui.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;

public class ProfilePicture extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private Image picture = new Image();
	private InlineLabel username = new InlineLabel();
	private Anchor editlink = new Anchor();
	private Anchor logoff = new Anchor("logoff");
	public ProfilePicture(){
		FlowPanel flwpnl1 = new FlowPanel();
		InlineHTML nlnhtml3 = new InlineHTML();
		flwpnl1.setStyleName("profile_picture");
		logoff.setStyleName("logoff_link");
		picture.setSize("58px", "59px");
		picture.getElement().setAttribute("alt","profie picture");
		picture.getElement().setAttribute("src",GWT.getModuleBaseURL()+"profilepicture.png");
		picture.getElement().setAttribute("style","width: 58px; height: 59px");
		//nlnhtml3.setHTML("<br></br>");
		username.setText("username");
		editlink.setHTML("edit");
		editlink.setHref("javascript:void(0)");
		flwpnl1.add(picture);
		flwpnl1.add(nlnhtml3);
		flwpnl1.add(username);
		flwpnl1.add(editlink);
		flwpnl1.add(logoff);
		flwpnl0.add(flwpnl1);
		this.initWidget(flwpnl0);
		logoff.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ProjectManager.dataService.handleLogoff(new AsyncCallback<DataServiceResponse>(){

					@Override
					public void onFailure(Throwable caught) {
						ProjectManager.showGeneralError(caught);
					}

					@Override
					public void onSuccess(DataServiceResponse result) {
						try{
							if(result.getStatus() == ProjectConstants.OK){
								ProjectManager.showLogout();
							}else{
								onFailure(new Throwable(result.getError()));
							}
						}catch(Exception e){
							onFailure(new Throwable(e));
						}
					}
					
				});
			}
			
		});
	}
	public void load() throws Exception {
	}
	public Image getPicture(){
		return this.picture;
	}
	public InlineLabel getUsername(){
		return this.username;
	}
	public Anchor getEditlink(){
		return this.editlink;
	}
}