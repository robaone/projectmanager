package com.robaone.gwt.projectmanager.client;

import com.robaone.gwt.projectmanager.client.ui.ArticleFeedUI;
import com.robaone.gwt.projectmanager.client.ui.FeaturedUI;
import com.robaone.gwt.projectmanager.client.ui.GeneralError;
import com.robaone.gwt.projectmanager.client.ui.LoggedOutUI;
import com.robaone.gwt.projectmanager.client.ui.LoginInterface;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.ProfilePicture;
import com.robaone.gwt.projectmanager.client.ui.ProjectManagerLayout;
import com.robaone.gwt.projectmanager.client.ui.RegistrationInterface;
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

	/**
	 * This is the entry point method.
	 */
	private BasicHistoryChangeHandler change_handler;
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
		this.setSection(FEATURED_CONTRACTORS, featured);
		featured.setText("featured contractors");
		
		FeaturedUI work = new FeaturedUI();
		this.setSection(RECENT_WORK, work);
		work.setText("recent work");
		
		if(RootPanel.get(NEWS_TICKER) != null){
			ArticleFeedUI feed = new ArticleFeedUI();
			this.setSection(NEWS_TICKER, feed);
		}
	}

	protected void showRegister() {
		final RegistrationInterface ri = new RegistrationInterface();
		this.setSection(MAIN_CONTENT, ri);
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
						ri.getZip_code().getValue(),new AsyncCallback<DataServiceResponse<UserData>>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							DataServiceResponse<UserData> result) {
						try{
							if(result.getStatus() == 0){
								UserData data = result.getData(0);
								ProjectManager.showAllModules(data);
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

	public static void showAllModules(UserData data) {

		MainContent main = new MainContent();
		
		ProfilePicture profile = new ProfilePicture();
		setSection(PROFILE_SECTION,profile);
		
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
		LoggedOutUI logout = new LoggedOutUI();
		if(RootPanel.get(ProjectManager.MAIN_CONTENT) != null){
			RootPanel.get(ProjectManager.MAIN_CONTENT).add(logout);
		}
		showLogin();
	}
}
