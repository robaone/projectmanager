package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.data.Comment;

public class CommentViewUi extends Composite {

	private static CommentViewUiUiBinder uiBinder = GWT
			.create(CommentViewUiUiBinder.class);

	interface CommentViewUiUiBinder extends UiBinder<Widget, CommentViewUi> {
	}

	public CommentViewUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void load(Comment data) {
		// TODO Auto-generated method stub
		
	}

}
