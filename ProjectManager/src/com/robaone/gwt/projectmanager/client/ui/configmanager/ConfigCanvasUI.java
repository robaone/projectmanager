package com.robaone.gwt.projectmanager.client.ui.configmanager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigCanvasUI extends Composite {

	private static ConfigCanvasUIUiBinder uiBinder = GWT
			.create(ConfigCanvasUIUiBinder.class);

	interface ConfigCanvasUIUiBinder extends UiBinder<Widget, ConfigCanvasUI> {
	}

	public ConfigCanvasUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	SplitLayoutPanel panel;

}
