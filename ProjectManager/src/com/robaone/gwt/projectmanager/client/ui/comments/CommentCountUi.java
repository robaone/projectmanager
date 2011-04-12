package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectManager;

public class CommentCountUi extends Composite {

	private static CommentCountUiUiBinder uiBinder = GWT
			.create(CommentCountUiUiBinder.class);

	interface CommentCountUiUiBinder extends UiBinder<Widget, CommentCountUi> {
	}

	public CommentCountUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label commentcount;
	private String m_goalid;
	public void load(String id) {
		this.m_goalid = id;
		ProjectManager.dataService.getCommentCountforGoal(id,new AsyncCallback<Integer>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(Integer result) {
				commentcount.setText(result+" comments");
			}
			
		});
	}

	protected void showError(String message) {
		commentcount.setText(message);
	}

	@UiHandler("commentcount")
	public void handleClick(ClickEvent event){
		Widget w = this.getParent();
		if(w instanceof SimplePanel){
			CommentListUi list = new CommentListUi();
			((SimplePanel) w).setWidget(list);
			list.load(m_goalid);
		}
	}
}
