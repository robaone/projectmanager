package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.ui.tabs.SectionTabs;

public class MainContent extends Composite {
	private FlowPanel live_feed;
	private SectionTabs decoratedTabPanel;

	public MainContent() {
		
		decoratedTabPanel = new SectionTabs();
		initWidget(decoratedTabPanel);
		decoratedTabPanel.setWidth("500px");
		
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
}
