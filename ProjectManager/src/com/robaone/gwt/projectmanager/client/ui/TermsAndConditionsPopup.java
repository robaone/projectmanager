package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TermsAndConditionsPopup extends PopupPanel {

	public TermsAndConditionsPopup() {
		super(true);
		VerticalPanel vp = new VerticalPanel();
		String url = Document.get().getElementById("_appsettings").getAttribute("tc_url");
		Frame frame = new Frame(url);
		frame.setSize("400px", "400px");
		vp.add(frame);
		Anchor close = new Anchor("Close");
		vp.add(close);
		vp.setCellHorizontalAlignment(close, HasHorizontalAlignment.ALIGN_CENTER);
		this.setWidget(vp);
		close.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				TermsAndConditionsPopup.this.hide();
			}
			
		});
	}

}
