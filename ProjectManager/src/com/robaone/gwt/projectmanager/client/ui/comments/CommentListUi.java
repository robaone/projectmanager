package com.robaone.gwt.projectmanager.client.ui.comments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.UserData;

public class CommentListUi extends Composite {

	private static CommentListUiUiBinder uiBinder = GWT
			.create(CommentListUiUiBinder.class);

	interface CommentListUiUiBinder extends UiBinder<Widget, CommentListUi> {
	}

	private String m_goalid;

	public CommentListUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void load(String id) {
		this.m_goalid = id;
		ProjectManager.dataService.getLoginStatus(new AsyncCallback<DataServiceResponse<UserData>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<UserData> result) {
				try {
					showList(result);
				} catch (Exception e) {
					onFailure(e);
				}
			}
			
		});
	}

	protected void showList(DataServiceResponse<UserData> result) throws Exception {
		if(result.getStatus() == ProjectConstants.OK){
		this.newcomment.setWidget(new CommentEditUi(this.m_goalid,this,result.getData(0)));
		ProjectManager.dataService.getCommentsForGoal(this.m_goalid,new AsyncCallback<DataServiceResponse<Comment>>(){

			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}

			@Override
			public void onSuccess(DataServiceResponse<Comment> result) {
				try{
					if(result.getStatus() == ProjectConstants.OK){
						for(int i = 0; i < result.getDataCount();i++){
							CommentViewUi view = new CommentViewUi();
							view.load(result.getData(i), CommentListUi.this);
							SimplePanel p = new SimplePanel();
							p.setWidget(view);
							if(i < list.getWidgetCount()){
								list.insert(p, i);
								list.remove(i+1);
							}
							list.add(view);
							
						}
						for(int i = result.getDataCount();i < list.getWidgetCount();){
							list.remove(i);
						}
					}else{
						throw new Exception(result.getError());
					}
				}catch(Exception e){
					onFailure(e);
				}
			}
			
		});
		}else{
			showError(result.getError());
		}
	}

	protected void showError(String message) {
		Window.alert(message);
	}

	@UiField Label hide;
	@UiField VerticalPanel list;
	@UiField SimplePanel newcomment;
	
	@UiHandler("hide")
	public void hide(ClickEvent event){
		Widget w = this.getParent();
		if(w instanceof SimplePanel){
			CommentCountUi count = new CommentCountUi();
			((SimplePanel) w).setWidget(count);
			count.load(this.m_goalid);
		}
	}
}
