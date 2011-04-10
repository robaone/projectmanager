package com.robaone.gwt.projectmanager.client.ui.feed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.robaone.gwt.projectmanager.client.data.FeedItem;

public class Feed extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private FlexTable list_table = new FlexTable();
	private Label more_items = new Label();
	public FeedItem[] items;
	public Feed(){
		list_table.setStyleName("width_100_pct");
		list_table.getFlexCellFormatter().setStyleName(0, 1, "style");
		list_table.getFlexCellFormatter().setStyleName(0, 2, "style");
		more_items.setStyleName("more_items");
		more_items.setText("More Items ...");
		flwpnl0.add(list_table);
		
		flwpnl0.add(more_items);
		this.initWidget(flwpnl0);
	}
	public void load(final FeedItem[] items) throws Exception {
		//list_table.clear();
		for(int i = 0 ; i < items.length;i++){
			if(list_table.getRowCount() < i){
				//insert the row
				FeedItemUi item = new FeedItemUi();
				list_table.setWidget(i, 0, item);
				item.load(items[i]);
			}else{
				//add the row
				FeedItemUi item = new FeedItemUi();
				list_table.setWidget(i, 0, item);
				item.load(items[i]);
			}
		}
		for(int i = items.length;i < list_table.getRowCount();){
			list_table.removeRow(i);
		}
		this.items = items;
		
	}
	public FlexTable getList_table(){
		return this.list_table;
	}
	public Label getMore_items(){
		return this.more_items;
	}
}