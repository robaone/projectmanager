package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.robaone.gwt.projectmanager.client.data.FeedItem;

public class MainContent extends Composite {
	private FlowPanel live_feed;
	private DecoratedTabPanel decoratedTabPanel;

	public MainContent() {
		
		decoratedTabPanel = new DecoratedTabPanel();
		initWidget(decoratedTabPanel);
		decoratedTabPanel.setSize("100%", "100%");
		
		live_feed = new FlowPanel();
		decoratedTabPanel.add(live_feed, "Live Feed");
		
		decoratedTabPanel.selectTab(0);
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
		try {
			feed.load(items);
		} catch (Exception e) {
			
		}
		this.getLive_feed().add(feed);
		
	}

	public FlowPanel getLive_feed() {
		return live_feed;
	}
	public DecoratedTabPanel getDecoratedTabPanel() {
		return decoratedTabPanel;
	}
}
