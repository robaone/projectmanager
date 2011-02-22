package com.robaone.gwt.projectmanager.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.UserData;
import com.robaone.gwt.projectmanager.client.ui.ContractorListingInfoUI;
import com.robaone.gwt.projectmanager.client.ui.ContractorListingUI;
import com.robaone.gwt.projectmanager.client.ui.ContractorProfileUI;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.ProfileEditUI;
import com.robaone.gwt.projectmanager.client.ui.TasksList;

public class BasicHistoryChangeHandler implements ValueChangeHandler<String> {

	private ProjectManager parent;

	public BasicHistoryChangeHandler(ProjectManager projectManager) {
		this.parent = projectManager;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		ProjectManager.writeLog("New history value = "+event.getValue());
		this.handleChange(event.getValue());
	}

	public void handleChange(final String token) {
		ProjectManager.writeLog("HandleChange('"+token+"')");
		ProjectManager.dataService.getLoginStatus(new AsyncCallback<DataServiceResponse<UserData>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(DataServiceResponse<UserData> result) {
				if(result.getStatus() == ProjectConstants.OK){
					if(ProjectManager.m_maincontent == null){
						ProjectManager.writeLog("creating MainContent object");
						ProjectManager.m_maincontent = new MainContent();
					}
					ProjectManager.m_maincontent.setVisible(true);
					if(token.equals("")){
						try{
							ProjectManager.m_maincontent.getDecoratedTabPanel().selectTab(0);
						}catch(Exception e){}
						
					}else if(token.startsWith("task=")){
						ProjectManager.setSection(ProjectManager.MAIN_CONTENT, ProjectManager.getMainContent());
						Widget w = ProjectManager.getSection(ProjectManager.TASKS_SECTION);
						if(w instanceof TasksList){
							TasksList t = (TasksList)w;
							String[] tokens = token.split("=");
							if(tokens.length > 1){
								if(tokens[1].equals(TasksList.TASK.ALERT.toString())){
									t.showTask(TasksList.TASK.ALERT);
								}else if(tokens[1].equals(TasksList.TASK.JOB.toString())){
									t.showTask(TasksList.TASK.JOB);
								}else if(tokens[1].equals(TasksList.TASK.NOTICE.toString())){
									t.showTask(TasksList.TASK.NOTICE);
								}else if(tokens[1].equals(TasksList.TASK.PROJECT.toString())){
									t.showTask(TasksList.TASK.PROJECT);
								}else if(tokens[1].equals(TasksList.TASK.SEARCH.toString())){
									t.showTask(TasksList.TASK.SEARCH);
								}else if(tokens[1].equals("FEED")){
									ProjectManager.m_maincontent.getDecoratedTabPanel().selectTab(0);
								}
							}
						}
					}else if(token.startsWith("profile=")){
						ProfileEditUI pe = new ProfileEditUI(result.getData(0));
						ProjectManager.setSection(ProjectManager.MAIN_CONTENT, pe);
					}
				}else if(token.equals("register")){
					parent.showRegister();
				}else{
					try{
						ProjectManager.m_maincontent.setVisible(false);
					}catch(Exception e){}
				}
				if(token.startsWith("category=")){
					showListingCategories();
					ProjectManager.dataService.getCurrentZipCode(new AsyncCallback<String>(){

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(String zipcode) {
							
							if(zipcode != null){
								showContractorsinCategory(token,zipcode);
							}
						}

					});

				}
				if(token.startsWith("contractor=")){
					showContractor(token.split("=")[1]);
				}
				
				if(token.equals("") && RootPanel.get(ProjectManager.LISTING_SECTION) != null){
					showListingCategories();
				}

			}

		});

	}
	private void showListingCategories(){
		Widget w = ProjectManager.getSection(ProjectManager.LISTING_SECTION);
		ContractorListingUI listing;
		if(w instanceof ContractorListingUI){
			listing = (ContractorListingUI)w;
		}else{
			listing = new ContractorListingUI();
			ProjectManager.setSection(ProjectManager.LISTING_SECTION, listing);
		}
	}
	protected void showContractor(String id) {
		ProjectManager.writeLog("Showing contractor "+id);
		ContractorProfileUI contractor = new ContractorProfileUI();
		ProjectManager.setSection(ProjectManager.LISTING_SECTION, contractor);
		try{contractor.load(Integer.parseInt(id));}catch(Exception e){ProjectManager.writeLog("Error: "+e.getMessage());}
	}

	protected void showContractorsinCategory(String token,String zipcode) {
		final String[] split = token.split("=");
		if(split.length < 2) return;
		final int cat = Integer.parseInt(split[1]);
		ProjectManager.dataService.getContractorsbyCategory(zipcode, cat, new AsyncCallback<DataServiceResponse<ContractorData>>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.print("Error: "+caught.getMessage());
			}

			@Override
			public void onSuccess(
					final DataServiceResponse<ContractorData> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						Widget w = ProjectManager.getSection(ProjectManager.LISTING_SECTION);
						final ContractorListingUI listing;
						if(w == null){
							listing = new ContractorListingUI();
							ProjectManager.setSection(ProjectManager.LISTING_SECTION,listing);
						}else{
							listing = (ContractorListingUI)w;
							
						}
						listing.getList().clear();
						if(result.getDataCount() > 0){
							ProjectManager.dataService.getCategory(cat,new AsyncCallback<Category>(){

								@Override
								public void onFailure(
										Throwable caught) {
									caught.printStackTrace();
								}

								@Override
								public void onSuccess(
										Category category) {
									HTML title = new HTML("<h3>"+category.getName()+" Contractors</h3>");
									listing.getList().add(title);
									for(int i = 0; i < result.getDataCount();i++){
										ContractorListingInfoUI info = new ContractorListingInfoUI(result.getData(i));
										listing.getList().add(info);
										final ContractorData contractor = result.getData(i);
										final int id = contractor.getId();
										info.getName().addClickHandler(new ClickHandler(){

											@Override
											public void onClick(
													ClickEvent event) {
												History.newItem("contractor="+id, true);
											}

										});
									}
								}

							});
						}
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
