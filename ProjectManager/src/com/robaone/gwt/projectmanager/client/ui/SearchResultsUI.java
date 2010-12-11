package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchResultsUI extends Composite {
	Label search = new Label();
	public SearchResultsUI(){
		VerticalPanel vp = new VerticalPanel();
		Label l = new Label("SearchResults");
		vp.add(l);
		vp.add(search);
		this.initWidget(vp);
	}

	public void updateSearch(String searchStr) {
		search.setText(searchStr);
	}
}
