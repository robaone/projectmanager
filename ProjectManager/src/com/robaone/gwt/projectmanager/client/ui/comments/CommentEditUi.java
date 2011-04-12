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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.UserData;

public class CommentEditUi extends Composite {

	private static CommentEditUiUiBinder uiBinder = GWT
			.create(CommentEditUiUiBinder.class);

	interface CommentEditUiUiBinder extends UiBinder<Widget, CommentEditUi> {
	}

	private Comment m_comment;
	private CommentListUi m_parent;
	private DateBox m_workdate;
	public CommentEditUi() {
		initWidget(uiBinder.createAndBindUi(this));
		initialize();
	}

	private void initialize() {
		DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
		m_workdate = new DateBox();
		m_workdate.setFormat(new DateBox.DefaultFormat(df));
		workdatepanel.setWidget(m_workdate);
	}

	public CommentEditUi(String id,CommentListUi list,UserData user) {
		initWidget(uiBinder.createAndBindUi(this));
		m_comment = new Comment();
		m_comment.setUserData(user);
		m_parent = list;
		m_comment.setGoalId(id);
		profilepic.setUrl(user.getPictureUrl());
		String name_str = user.getFirstname() == null ? user.getUsername() : (user.getFirstname() + " " + user.getLastname());
		name.setText(name_str);
		initialize();
	}
	public CommentEditUi(Comment comment,CommentListUi list){
		initWidget(uiBinder.createAndBindUi(this));
		m_comment = comment;
		m_parent = list;
		initialize();
		this.load(comment);
	}

	@UiField Image profilepic;
	@UiField InlineLabel name;
	@UiField TextBox hours;
	@UiField SimplePanel workdatepanel;
	@UiField TextArea comment;
	private void load(Comment commnt) {
		this.m_comment = commnt;
		String name_str = commnt.getUserData().getFirstname() == null ? commnt.getUserData().getUsername() : (commnt.getUserData().getFirstname()+ " "+commnt.getUserData().getLastname());
		name.setText(name_str);
		profilepic.setUrl(commnt.getUserData().getPictureUrl());
		hours.setText(commnt.getHours() == null ? "" : commnt.getHours().toString());
		comment.setText(commnt.getComment());
	}

	@UiField Button post;
	
	@UiHandler("post")
	public void post(ClickEvent event){
		m_comment.setComment(comment.getText());
		try{
			m_comment.setHours(new Double(this.hours.getText()));
		}catch(Exception e){
			m_comment.setHours(null);
		}
		m_comment.setWorkDate(this.m_workdate.getValue());
		final boolean isnew = m_comment.getId() == null ? true: false;
		ProjectManager.dataService.saveCommentforGoal(m_comment,new AsyncCallback<DataServiceResponse<Comment>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<Comment> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						if(isnew){
							m_parent.load(result.getData(0).getGoalId());
							m_parent.newcomment.setWidget(new CommentEditUi(result.getData(0).getGoalId(),m_parent,result.getData(0).getUserData()));
						}else{
							Widget w = CommentEditUi.this.getParent();
							if(w instanceof SimplePanel){
								CommentViewUi view = new CommentViewUi();
								view.load(result.getData(0),m_parent);
								((SimplePanel) w).setWidget(view);
							}
						}
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
