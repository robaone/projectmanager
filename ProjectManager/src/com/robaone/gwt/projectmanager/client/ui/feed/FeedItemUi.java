package com.robaone.gwt.projectmanager.client.ui.feed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.FeedItem;

public class FeedItemUi extends Composite {

	private static FeedItemUiUiBinder uiBinder = GWT
			.create(FeedItemUiUiBinder.class);

	interface FeedItemUiUiBinder extends UiBinder<Widget, FeedItemUi> {
	}

	public FeedItemUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField Anchor title;
	@UiField Label summary;
	@UiField InlineLabel datetime;
	@UiField Image image;
	private FeedItem m_data;
	
	@UiHandler("title")
	public void handleTitleClick(ClickEvent event){
		History.newItem("task=PROJECT&id="+this.m_data.getReferenceid());
	}
	public void load(FeedItem item){
		title.setText(item.getTitle());
		summary.setText(item.getSummary());
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
		try{datetime.setText(df.format(item.getDatetime()));}catch(Exception e){}
		image.setUrl(item.getIconurl());
		this.m_data  = item;
	}
	public String getId(){
		return this.m_data.getReferenceid();
	}
}
