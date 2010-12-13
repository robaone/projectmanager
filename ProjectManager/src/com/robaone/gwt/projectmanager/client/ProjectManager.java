package com.robaone.gwt.projectmanager.client;

import com.robaone.gwt.projectmanager.client.ui.GeneralError;
import com.robaone.gwt.projectmanager.client.ui.LoggedOutUI;
import com.robaone.gwt.projectmanager.client.ui.LoginInterface;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.ProfilePicture;
import com.robaone.gwt.projectmanager.client.ui.ProjectManagerLayout;
import com.robaone.gwt.projectmanager.client.ui.SearchForm;
import com.robaone.gwt.projectmanager.client.ui.TasksList;
import com.robaone.gwt.projectmanager.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProjectManager extends ProjectConstants implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	public static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	public static final DataServiceAsync dataService = GWT
			.create(DataService.class);

	private static final String PROFILE_SECTION = "profile_section";
	private static final String SEARCH_BAR = "search_bar";
	private static final String TASKS_SECTION = "tasks_section";
	private static final String MAIN_CONTENT = "main_content";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		try{
			String url = Document.get().getElementById("appsettings").getAttribute("url");
			((ServiceDefTarget)dataService).setServiceEntryPoint(url);
		}catch(Exception e){}
		/**
		 * Initialize the stuff
		 */
		dataService.getLoginStatus(new AsyncCallback<DataServiceResponse<UserData>>(){

			@Override
			public void onFailure(Throwable caught) {
				ProjectManager.showGeneralError(caught);
			}

			@Override
			public void onSuccess(DataServiceResponse<UserData> result) {
				if(result.getStatus() == OK){
					showAllModules(result.getData(0));
				}else if(result.getStatus() == NOT_LOGGED_IN){
					showLogin();
				}
			}
			
		});
	}

	public static void showLogin() {
		DialogBox logindialog = new DialogBox();
		logindialog.setAnimationEnabled(true);
		logindialog.setGlassEnabled(true);
		logindialog.setText("Login");
		LoginInterface login = new LoginInterface(logindialog);
		logindialog.setWidget(login);
		logindialog.center();
	}

	public static void showAllModules(UserData data) {

		MainContent main = new MainContent();
		
		ProfilePicture profile = new ProfilePicture();
		setSection(PROFILE_SECTION,profile);
		
		SearchForm search = new SearchForm(main);
		setSection(SEARCH_BAR, search);
		
		TasksList tasks = new TasksList();
		setSection(TASKS_SECTION, tasks);
		
		setSection(MAIN_CONTENT, main);
		if(main.isAttached()){
			main.load();
		}
		
		tasks.setMainContent(main);
	}

	public static void showGeneralError(Throwable caught) {
		GeneralError error = new GeneralError(caught);
		RootPanel p = RootPanel.get(MAIN_CONTENT);
		if(p != null){
			p.clear();
			p.add(error);
		}
	}

	private static void setSection(String section, Widget section_widget) {
		try{
			RootPanel p = RootPanel.get(section);
			if(p != null){
				p.clear();
				p.add(section_widget);
			}
		}catch(Exception e){}
	}

	public static void showLogout() {
		String[] sections = {ProjectManager.MAIN_CONTENT,ProjectManager.PROFILE_SECTION,
				ProjectManager.SEARCH_BAR,ProjectManager.TASKS_SECTION};
		for(int i = 0 ;  i < sections.length;i++){
			try{
				RootPanel p = RootPanel.get(sections[i]);
				if(p != null){
					p.clear();
				}
			}catch(Exception e){}
		}
		LoggedOutUI logout = new LoggedOutUI();
		if(RootPanel.get(ProjectManager.MAIN_CONTENT) != null){
			RootPanel.get(ProjectManager.MAIN_CONTENT).add(logout);
		}
	}
}
