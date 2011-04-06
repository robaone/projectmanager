package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CommentEditUi extends Composite {

	private static CommentEditUiUiBinder uiBinder = GWT
			.create(CommentEditUiUiBinder.class);

	interface CommentEditUiUiBinder extends UiBinder<Widget, CommentEditUi> {
	}

	public CommentEditUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Button post;
}
