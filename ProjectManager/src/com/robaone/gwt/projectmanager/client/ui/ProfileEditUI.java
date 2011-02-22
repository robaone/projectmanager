package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.UserData;

public class ProfileEditUI extends Composite implements HasText {

	private static ProfileEditUIUiBinder uiBinder = GWT
	.create(ProfileEditUIUiBinder.class);

	interface ProfileEditUIUiBinder extends UiBinder<Widget, ProfileEditUI> {
	}
	@UiField Image profile_image;
	@UiField TextBox first_name;
	@UiField TextBox last_name;
	@UiField TextBox phone_number;
	@UiField TextBox zipcode;
	@UiField FlowPanel more_fields;
	@UiField Button update;
	@UiField FormPanel picture_upload;
	@UiField Button upload;
	private UserData user;
	public ProfileEditUI(UserData user) {
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
		this.profile_image.setSize("58px", "59px");
		if(user.getPictureUrl() != null && user.getPictureUrl().length() > 0){
			this.profile_image.setUrl("/images/profiles/"+user.getPictureUrl());
		}else{
			this.profile_image.setUrl(GWT.getModuleBaseURL()+"profilepicture.png");
		}
		first_name.setText(user.getFirstname());
		last_name.setText(user.getLastname());
		phone_number.setText(user.getPhonenumber());
		zipcode.setText(user.getZip());
		try{
			if(user.getAccountType().equals(ProjectConstants.USER_TYPE.HVACPROFESSIONAL)){
				HVACQuestionsUI questions = new HVACQuestionsUI(this.user);
				more_fields.add(questions);
			}
		}catch(Exception e){}
		String url = Document.get().getElementById("_appsettings").getAttribute("upload_url");
		this.picture_upload.setAction(url);
		this.picture_upload.setMethod(FormPanel.METHOD_POST);
		this.picture_upload.setEncoding(FormPanel.ENCODING_MULTIPART);
	}
	@UiHandler("upload")
	public void upload(ClickEvent e){
		this.picture_upload.submit();
	}
	@UiHandler("picture_upload")
	public void uploadPicture(SubmitEvent event){
		ProjectManager.writeLog("uploading picture");
	}
	@UiHandler("picture_upload")
	public void uploadPictureCompleted(SubmitCompleteEvent event){
		ProjectManager.writeLog("uploading picture completed");
		String str = event.getResults();
		ProjectManager.writeLog("upload result = "+str);
		if("0".equals(str)){
			/*
			 * The file was uploaded successfully
			 */
			ProjectManager.dataService.getLoginStatus(new AsyncCallback<DataServiceResponse<UserData>>(){

				@Override
				public void onFailure(Throwable caught) {
					showError(caught.getMessage());
				}

				@Override
				public void onSuccess(DataServiceResponse<UserData> result) {
					try{
						if(result.getStatus() == ProjectConstants.OK){
							UserData data = result.getData(0);
							profile_image.setUrl("/images/profiles/"+data.getPictureUrl());
						}else{
							throw new Exception(result.getError());
						}
					}catch(Exception e){
						onFailure(new Throwable(e));
					}
				}
				
			});
		}
	}
	protected void showError(String message) {
		// TODO Auto-generated method stub
		
	}
	@UiHandler("update")
	public void updateProfile(ClickEvent e){
		/*
		 * Update the user information.
		 * 
		 * 
		 */
		this.user.setFirstname(this.first_name.getValue());
		this.user.setLastname(this.last_name.getValue());
		this.user.setZip(this.zipcode.getValue());
		this.user.setPhonenumber(this.phone_number.getValue());
		/*
		 * Update the other fields.
		 */
		if(more_fields.getWidgetCount() > 0){
			Widget w = more_fields.getWidget(0);
			if(w instanceof HVACQuestionsUI){
				HVACQuestionsUI h = (HVACQuestionsUI)w;
				this.user = h.getAnswers();
			}
		}

		ProjectManager.dataService.updateProfile(this.user,new AsyncCallback<DataServiceResponse<UserData>>(){

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(DataServiceResponse<UserData> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						/*
						 * The data has been updated.
						 */
						History.newItem("task=FEED",true);
					}else if(result.getStatus() == ProjectConstants.FIELD_VERIFICATION_ERROR){
						/*
						 * Handle field verification error
						 */
					}else if(result.getStatus() == ProjectConstants.NOT_LOGGED_IN){
						/*
						 * Handle login
						 */
					}else if(result.getStatus() == ProjectConstants.GENERAL_ERROR){
						throw new Exception(result.getError());
					}
				}catch(Exception e){
					onFailure(new Throwable(e));
				}
			}

		});
	}

	public ProfileEditUI(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
