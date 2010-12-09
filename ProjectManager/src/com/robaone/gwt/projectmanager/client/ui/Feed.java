package com.robaone.gwt.projectmanager.client.ui;

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
		for(int i = 0 ; i < items.length;i++){
			Image icon = new Image();
			Anchor link = new Anchor();
			Label summary = new Label();
			InlineLabel datetime = new InlineLabel();
			FlowPanel flwpnl2 = new FlowPanel();
			FlowPanel flwpnl4 = new FlowPanel();
			icon.getElement().setAttribute("alt","comment");
			icon.getElement().setAttribute("src",GWT.getModuleBaseURL()+"comment.png");
			icon.getElement().setAttribute("style","width: 33px; height: 33px");
			link.setHref("javascript:void(0)");
			summary.getElement().setAttribute("style","font-size:9pt");
			link.setHTML(items[i].getTitle());
			summary.setText(items[i].getSummary());
			datetime.setText(items[i].getDatetime());
			flwpnl2.add(icon);
			flwpnl4.add(link);
			flwpnl4.add(summary);
			flwpnl4.getElement().setAttribute("style","vertical-align:top;font-family:Arial");
			list_table.setWidget(i,0,flwpnl2);
			list_table.getCellFormatter().setStyleName(i, 0, "task_col1");
			list_table.setWidget(i,1,flwpnl4);
			list_table.getCellFormatter().getElement(i, 0).setAttribute("style", "vertical-align:top;font-family:Arial");
			list_table.setWidget(i,2,datetime);
			list_table.getCellFormatter().getElement(i, 2).setAttribute("style","vertical-align:top;font-family:Arial;font-size:7pt;width:80px");
			final int index = i;
			link.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem(items[index].getReferenceid(), true);
				}
				
			});
		}
	}
	public FlexTable getList_table(){
		return this.list_table;
	}
	public Label getMore_items(){
		return this.more_items;
	}
}