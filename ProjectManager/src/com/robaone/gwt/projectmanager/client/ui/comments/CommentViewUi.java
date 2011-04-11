package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Comment;

public class CommentViewUi extends Composite {

	private static CommentViewUiUiBinder uiBinder = GWT
			.create(CommentViewUiUiBinder.class);

	interface CommentViewUiUiBinder extends UiBinder<Widget, CommentViewUi> {
	}

	@UiField Image profilepic;
	@UiField InlineLabel name;
	@UiField InlineLabel workdate;
	@UiField HTML comment;
	@UiField Button edit;
	@UiField Button delete;
	Comment m_comment;
	CommentListUi m_parent;
	public CommentViewUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void load(Comment data,CommentListUi list) {
		profilepic.setUrl(data.getUserData().getPictureUrl());
		String name_str = data.getUserData().getFirstname() == null ? data.getUserData().getUsername() : (data.getUserData().getFirstname() + " " + data.getUserData().getLastname());
		name.setText(name_str);
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		try{workdate.setText(df.format(data.getWorkDate()));}catch(Exception e){}
		comment.setText(data.getComment());
		this.m_comment = data;
		this.m_parent = list;
	}

	@UiHandler("edit")
	public void edit(ClickEvent event){
		Widget w = this.getParent();
		if(w instanceof SimplePanel){
			CommentEditUi editui = new CommentEditUi(this.m_comment,this.m_parent);
			((SimplePanel) w).setWidget(editui);
		}
	}
	@UiHandler("delete")
	public void delete(ClickEvent event){
		ProjectManager.dataService.deleteComment(this.m_comment,new AsyncCallback<DataServiceResponse<Comment>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<Comment> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						m_parent.load(m_comment.getGoalId());
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
