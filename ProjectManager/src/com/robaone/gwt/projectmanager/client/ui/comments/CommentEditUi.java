package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Comment;

public class CommentEditUi extends Composite {

	private static CommentEditUiUiBinder uiBinder = GWT
			.create(CommentEditUiUiBinder.class);

	interface CommentEditUiUiBinder extends UiBinder<Widget, CommentEditUi> {
	}

	private Comment m_comment;
	private CommentListUi m_parent;
	public CommentEditUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CommentEditUi(String id,CommentListUi list) {
		initWidget(uiBinder.createAndBindUi(this));
		m_comment = new Comment();
		m_parent = list;
		m_comment.setGoalId(id);
	}

	@UiField Button post;
	
	@UiHandler("post")
	public void post(ClickEvent event){
		ProjectManager.dataService.saveCommentforGoal(m_comment,new AsyncCallback<DataServiceResponse<Comment>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<Comment> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						m_parent.load(result.getData(0).getGoalId());
						m_parent.newcomment.setWidget(new CommentEditUi(result.getData(0).getGoalId(),m_parent));
					}else{
						throw new Exception(result.getError());
					}
				}catch(Exception e){
					onFailure(e);
				}
			}
			
		});
	}

	protected void showError(String message) {
		// TODO Auto-generated method stub
		
	}
}
