package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.ui.TasksList.TASK;
import com.robaone.gwt.projectmanager.client.ui.tabs.SectionTabs;

public class MainContent extends Composite {
	private FlowPanel live_feed;
	private SectionTabs decoratedTabPanel;

	public MainContent() {

		decoratedTabPanel = new SectionTabs();
		initWidget(decoratedTabPanel);
		
		live_feed = new FlowPanel();
		decoratedTabPanel.addTab(live_feed, "Live Feed");

	}

	public void load() {
		Feed feed = new Feed();
		/*
		 * Do request and callback
		 */
		//onSuccess()
		FeedItem[] items = new FeedItem[1];
		items[0] = new FeedItem();
		items[0].setTitle("Mock Title");
		items[0].setDatetime("datetime");
		items[0].setSummary("summary");
		try {
			feed.load(items);
		} catch (Exception e) {

		}
		this.getLive_feed().add(feed);
		/*
		for(int i = 0 ; i < 20;i++){
			this.getDecoratedTabPanel().addTab(new Feed(),"Tab "+i);
		}
		 */
		this.getDecoratedTabPanel().selectTab(0);
	}

	public FlowPanel getLive_feed() {
		return live_feed;
	}
	public SectionTabs getDecoratedTabPanel() {
		return decoratedTabPanel;
	}

	public void showSection(TASK action) {
		Integer[] keys = this.getDecoratedTabPanel().getTabContents().keySet().toArray(new Integer[0]);
		for(int i = 0; i < keys.length;i++){
			Widget w = this.getDecoratedTabPanel().getTabContents().get(keys[i]);
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
				if(w instanceof ProjectListUI){
					this.getDecoratedTabPanel().selectTab(i);
					return;
				}
			}
		}
		Widget w = null;
		int tabindex = -1;
		if(action.equals(TASK.ALERT)){
			w = new AlertListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Alerts",true);
		}else if(action.equals(TASK.JOB)){
			w = new JobListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Jobs",true);
		}else if(action.equals(TASK.NOTICE)){
			w = new NoticeListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Notices",true);
		}else if(action.equals(TASK.PROJECT)){
			w = new ProjectListUI();
			tabindex = this.getDecoratedTabPanel().addTab(w, "Projects",true);
		}
		if(tabindex > -1){
			this.getDecoratedTabPanel().selectTab(tabindex);
		}
	}
}
