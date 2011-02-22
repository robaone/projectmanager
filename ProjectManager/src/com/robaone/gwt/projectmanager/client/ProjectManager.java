package com.robaone.gwt.projectmanager.client;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.robaone.gwt.projectmanager.client.data.UserData;
import com.robaone.gwt.projectmanager.client.ui.ArticleFeedUI;
import com.robaone.gwt.projectmanager.client.ui.ContractorListingUI;
import com.robaone.gwt.projectmanager.client.ui.FeaturedUI;
import com.robaone.gwt.projectmanager.client.ui.GeneralError;
import com.robaone.gwt.projectmanager.client.ui.LoggedOutUI;
import com.robaone.gwt.projectmanager.client.ui.LoginInterface;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.ProfilePicture;
import com.robaone.gwt.projectmanager.client.ui.ProjectManagerLayout;
import com.robaone.gwt.projectmanager.client.ui.RegistrationUI;
import com.robaone.gwt.projectmanager.client.ui.SearchForm;
import com.robaone.gwt.projectmanager.client.ui.TasksList;
import com.robaone.gwt.projectmanager.server.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
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

	public static final String PROFILE_SECTION = "profile_section";
	public static final String SEARCH_BAR = "search_bar";
	public static final String TASKS_SECTION = "tasks_section";
	public static final String MAIN_CONTENT = "app_main_content";
	public static final String FEATURED_CONTRACTORS = "featured_contractors";
	public static final String RECENT_WORK = "recent_work";
	public static final String NEWS_TICKER = "news_ticker_container";
	public static final String LISTING_SECTION = "listing_app_container";

	/**
	 * This is the entry point method.
	 */
	public static BasicHistoryChangeHandler change_handler;
	public static MainContent m_maincontent;
	public void onModuleLoad() {
		try{
			String url = Document.get().getElementById("_appsettings").getAttribute("url");
			if(url != null && url.length() > 0){
				((ServiceDefTarget)dataService).setServiceEntryPoint(url);
			}
		}catch(Exception e){}
		change_handler = new BasicHistoryChangeHandler(this);
		History.addValueChangeHandler(change_handler);
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
				String token = History.getToken();
				ProjectManager.this.change_handler.handleChange(token);
			}
			
		});
		/**
		 * Add history change handler
		 */
		FeaturedUI featured = new FeaturedUI();
		ProjectManager.setSection(FEATURED_CONTRACTORS, featured);
		featured.setText("featured contractors");
		
		FeaturedUI work = new FeaturedUI();
		ProjectManager.setSection(RECENT_WORK, work);
		work.setText("recent work");
		
		if(RootPanel.get(NEWS_TICKER) != null){
			ArticleFeedUI feed = new ArticleFeedUI();
			ProjectManager.setSection(NEWS_TICKER, feed);
		}
		/**
		 * Add listing app
		 *
		if(RootPanel.get(ProjectManager.LISTING_SECTION) != null){
			ContractorListingUI listing = new ContractorListingUI();
			ProjectManager.setSection(LISTING_SECTION, listing);
		}
		*/
	}

	protected void showRegister() {
		final RegistrationUI ri = new RegistrationUI();
		setSection(MAIN_CONTENT, ri);
		ri.getCancel().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				try{
					String url = Document.get().getElementById("_appsettings").getAttribute("logon_cancel_url");
					Window.Location.assign(url);
				}catch(Exception e){}
			}

		});
		ri.getCreate_account().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ProjectManager.dataService.createAccount(ri.getEmail().getValue(),ri.getPassword1().getValue(),
						ri.getZip_code().getValue(),ri.getAccountType(),new AsyncCallback<DataServiceResponse<UserData>>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							DataServiceResponse<UserData> result) {
						try{
							if(result.getStatus() == 0){
								ProjectManager.writeLog("User ,"+result.getData(0).getUsername()+", is created.");
								UserData data = result.getData(0);
								ProjectManager.writeLog("User type is "+data.getAccountType());
								/*
								 * if the person is a professional.  Take them to the profile edit
								 * to finish their profile information.
								 */
								if(data.getAccountType().equals(ProjectConstants.USER_TYPE.HVACPROFESSIONAL)){
									
									ProjectManager.updateProfileSection(data);
									History.newItem("profile=edit",true);
								}else{
									History.newItem("", false);
									ProjectManager.showAllModules(data);
								}
							}else if(result.getStatus() == ProjectManager.FIELD_VERIFICATION_ERROR){
								String[] names = result.getFieldErrorNames();
								for(int i = 0; i < names.length;i++){
									ri.showError(names[i],result.getFieldError(names[i]));
								}
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

	public static void showLogin() {
		LoginInterface login = new LoginInterface(null);
		setSection(PROFILE_SECTION,login);
	}
	public static void writeLog(String message){
		ProjectManager.dataService.writeLog(message, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public static void showAllModules(UserData data) {

		
		MainContent main;
		if(ProjectManager.m_maincontent != null){
			main = ProjectManager.m_maincontent;
		}else{
			main = new MainContent();
			ProjectManager.m_maincontent = main;
		}
		
		ProjectManager.dataService.getLoginStatus(new AsyncCallback<DataServiceResponse<UserData>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(DataServiceResponse<UserData> result) {
				try{updateProfileSection(result.getData(0));}catch(Exception e){}
			}
		});
		if("true".equals(Document.get().getElementById("_appsettings").getAttribute("replace_search"))){
			if(RootPanel.get("searchform") != null){
				Document.get().getElementById("searchform").setInnerHTML("");
				Document.get().getElementById("searchform").setAttribute("action", "javascript:void(0)");
				SearchForm search = new SearchForm(main);
				RootPanel.get("searchform").add(search);
			}
		}
		
		TasksList tasks = new TasksList();
		setSection(TASKS_SECTION, tasks);
		
		setSection(MAIN_CONTENT, main);
		if(main.isAttached()){
			main.load();
		}
		
		tasks.setMainContent(main);
	}

	public static void updateProfileSection(
			UserData data) {
		ProjectManager.writeLog("Updating Profile Section");
		ProfilePicture profile = new ProfilePicture();
		profile.getUsername().setText(data.getUsername());
		if(data.getPictureUrl() != null){
			profile.getPicture().setUrl("/images/profiles/"+data.getPictureUrl());
		}
		ProjectManager.setSection(PROFILE_SECTION, profile);
		
		profile.getEditlink().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String url = Document.get().getElementById("_appsettings").getAttribute("dashboard_url");
				String is_dashboard = Document.get().getElementById("_appsettings").getAttribute("is_dashboard");
				if("true".equals(is_dashboard)){
					History.newItem("profile=edit", true);
				}else{
					Location.assign(url+"#profile=edit");
				}
			}
			
		});
	}

	public static void showGeneralError(Throwable caught) {
		GeneralError error = new GeneralError(caught);
		RootPanel p = RootPanel.get(MAIN_CONTENT);
		if(p != null){
			p.clear();
			p.add(error);
		}
	}

	public static void setSection(String section, Widget section_widget) {
		try{
			ProjectManager.writeLog("Setting section, "+section+" with Widget, "+section_widget.getClass().getName());
			
			RootPanel p = RootPanel.get(section);
			if(p != null){
				p.clear();
				p.add(section_widget);
				ProjectManager.writeLog(" - Section is added to page");
			}
		}catch(Exception e){}
	}
	
	public static Widget getSection(String section){
		try{
			RootPanel p = RootPanel.get(section);
			if(p != null){
				return p.getWidget(0);
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
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
		ProjectManager.m_maincontent = null;
		LoggedOutUI logout = new LoggedOutUI();
		if(RootPanel.get(ProjectManager.MAIN_CONTENT) != null){
			RootPanel.get(ProjectManager.MAIN_CONTENT).add(logout);
		}
		showLogin();
	}

	public static Widget getMainContent() {
		if(ProjectManager.m_maincontent == null){
			ProjectManager.m_maincontent = new MainContent();
		}
		return ProjectManager.m_maincontent;
	}

	public static void writeLog(Throwable caught) {
		ProjectManager.writeLog(caught.getMessage());
		for(int i = 0; i < caught.getStackTrace().length;i++){
			ProjectManager.writeLog(caught.getStackTrace()[i].getClassName()+": "+caught.getStackTrace()[i].getMethodName()+": "+caught.getStackTrace()[i].getLineNumber());
		}
	}
	
	public static void writeLog(Exception caught) {
		ProjectManager.writeLog(caught.getMessage());
		for(int i = 0; i < caught.getStackTrace().length;i++){
			ProjectManager.writeLog(caught.getStackTrace()[i].getClassName()+": "+caught.getStackTrace()[i].getMethodName()+": "+caught.getStackTrace()[i].getLineNumber());
		}
	}
}
