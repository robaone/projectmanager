package com.robaone.gwt.projectmanager.client.ui;

import java.util.HashMap;


import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.ui.alerts.AlertListUI;
import com.robaone.gwt.projectmanager.client.ui.feed.Feed;
import com.robaone.gwt.projectmanager.client.ui.feed.FeedItemUi;
import com.robaone.gwt.projectmanager.client.ui.notices.NoticeListUI;
import com.robaone.gwt.projectmanager.client.ui.project.ProjectListUI;
import com.robaone.gwt.projectmanager.client.ui.project.ProjectUi2;
import com.robaone.gwt.projectmanager.client.ui.search.SearchResultsUI;
import com.robaone.gwt.projectmanager.client.ui.tabs.SectionTabs;
import com.robaone.gwt.projectmanager.client.ui.tasks.TasksList.TASK;

public class MainContent extends Composite {
	private FlowPanel live_feed;
	private SectionTabs decoratedTabPanel;
	private FlowPanel root = new FlowPanel();
	private HashMap<String,Widget> tabs = new HashMap<String,Widget>();
	private boolean loaded = false;
	public MainContent() {
		InlineHTML h = new InlineHTML("<h1>Project Dashboard</h1>");
		decoratedTabPanel = new SectionTabs();
		root.add(h);
		root.add(decoratedTabPanel);
		initWidget(root);
		decoratedTabPanel.setWidth("100%");
		live_feed = new FlowPanel();
		Label feed = new Label("Live Feed");
		decoratedTabPanel.addTab(live_feed, feed,"task=FEED");
	}

	public void load() {
		if(this.loaded){
			return;
		}
		/*
		 * Do request and callback
		 */
		ProjectManager.dataService.getFeed(new AsyncCallback<DataServiceResponse<FeedItem>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<FeedItem> result) {
				final Feed feed = new Feed();
				try {
					feed.load(result.getDataArray(new FeedItem[0]));
				} catch (Exception e) {
					onFailure(e);
				}
				MainContent.this.getLive_feed().add(feed);
				MainContent.this.getDecoratedTabPanel().selectTab(0);
				MainContent.this.loaded = true;

				Timer t = new Timer(){

					@Override
					public void run() {
						updateFeed(feed);
					}


				};
				//t.schedule(15000);
			}

		});
		//onSuccess()
	}

	public void updateFeed(final Feed feed) {
		if(feed.isAttached()){
			ProjectManager.dataService.getFeed(new AsyncCallback<DataServiceResponse<FeedItem>>(){

				@Override
				public void onFailure(Throwable caught) {
					showError(caught.getMessage());
				}

				@Override
				public void onSuccess(
						DataServiceResponse<FeedItem> result) {
					try {
						feed.load(result.getDataArray(new FeedItem[0]));
					} catch (Exception e) {
						onFailure(e);
					}
				}

			});
		}

	}
	protected void showError(String message) {
		Window.alert(message);
	}

	public FlowPanel getLive_feed() {
		return live_feed;
	}
	public SectionTabs getDecoratedTabPanel() {
		return decoratedTabPanel;
	}

	public void showSection(TASK action) {
		String h_token = History.getToken();
		for(int i = 0; i < this.getDecoratedTabPanel().getTabCount();i++){
			Widget w = this.getDecoratedTabPanel().getTab(i);
			if(action.equals(TASK.ALERT)){
				if(w instanceof AlertListUI){
					this.getDecoratedTabPanel().selectTab(i);
					return;
				}
			}else if(action.equals(TASK.JOB)){
				if(w instanceof JobListUI){
					this.getDecoratedTabPanel().selectTab(i);
					return;
				}
			}else if(action.equals(TASK.NOTICE)){
				if(w instanceof NoticeListUI){
					this.getDecoratedTabPanel().selectTab(i);
					return;
				}
			}else if(action.equals(TASK.PROJECT)){
				if(h_token.endsWith("="+TASK.PROJECT.toString())){
					if(w instanceof ProjectListUI){
						this.getDecoratedTabPanel().selectTab(i);
						return;
					}
				}else if(w instanceof ProjectUi2){
					ProjectUi2 p = (ProjectUi2)w;
					String project_id = MainContent.getParameter("id",h_token);
					if(p.getData().getId().equals(project_id)){
						this.getDecoratedTabPanel().selectTab(i);
						return;
					}
				}
			}else if(action.equals(TASK.SEARCH)){
				if(w instanceof SearchResultsUI){
					this.getDecoratedTabPanel().selectTab(i);
					return;
				}
			}
		}
		Widget w = null;
		int tabindex = -1;
		if(action.equals(TASK.ALERT)){
			w = new AlertListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Alerts",true,"task="+TASK.ALERT);
		}else if(action.equals(TASK.JOB)){
			w = new JobListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Jobs",true,"task="+TASK.JOB);
		}else if(action.equals(TASK.NOTICE)){
			w = new NoticeListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Notices",true,"task="+TASK.NOTICE);
		}else if(action.equals(TASK.PROJECT)){
			if(h_token.endsWith("="+TASK.PROJECT.toString())){
				w = new ProjectListUI(this);
				tabindex = this.getDecoratedTabPanel().addTab(w, "Projects",true,"task="+TASK.PROJECT);
			}else{
				String id = MainContent.getParameter("id", h_token);
				ProjectManager.dataService.getProject(id,new AsyncCallback<DataServiceResponse<Project>>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(DataServiceResponse<Project> result) {
						try{
							if(result.getStatus() == ProjectConstants.OK){
								ProjectUi2 pui = new ProjectUi2(MainContent.this);
								pui.load(result.getData(0));
								int index = getDecoratedTabPanel().addTab(pui, result.getData(0).getProjectName(),true,"task="+TASK.PROJECT+"&ID="+result.getData(0).getId());
								if(index > -1)
									getDecoratedTabPanel().selectTab(index);
							}else{
								throw new Exception(result.getError());
							}
						}catch(Exception e){
							onFailure(e);
						}
					}

				});
			}
		}else if(action.equals(TASK.SEARCH)){
			w = new SearchResultsUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Search Results",true,"task="+TASK.SEARCH);
		}
		if(tabindex > -1){
			this.getDecoratedTabPanel().selectTab(tabindex);
		}
	}

	private static String getParameter(String string, String h_token) {
		try{
			String retval = "";
			String[] p1 = h_token.split("&");
			for(int i = 0; i < p1.length;i++){
				String[] p2 = p1[i].split("=");
				if(p2[0].equalsIgnoreCase(string)){
					retval = p2[1];
					break;
				}
			}
			return retval;
		}catch(Exception e){
			return null;
		}
	}

	public void updateSearchResults(String searchStr) {
		SearchResultsUI results = null;
		for(int i = 0; i < this.getDecoratedTabPanel().getTabCount();i++){
			Widget w = this.getDecoratedTabPanel().getTab(i);
			if(w instanceof SearchResultsUI){
				results = (SearchResultsUI)w;
				break;
			}
		}
		if(results != null){
			results.updateSearch(searchStr);
		}
	}
}
