package com.robaone.gwt.projectmanager.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.data.AnswerType;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.Question;
import com.robaone.gwt.projectmanager.client.data.UserData;

public class HVACQuestionsUI extends Composite{

	private static HVACQuestionsUIUiBinder uiBinder = GWT
	.create(HVACQuestionsUIUiBinder.class);
	private UserData m_user;
	@UiField FlexTable questions;
	interface HVACQuestionsUIUiBinder extends UiBinder<Widget, HVACQuestionsUI> {
	}

	public HVACQuestionsUI(UserData data) {
		initWidget(uiBinder.createAndBindUi(this));
		this.m_user = data;
		this.showQuestions();
	}

	private void showQuestions() {
		if(this.m_user.getAccountType().equals(ProjectConstants.USER_TYPE.HVACPROFESSIONAL)){
			if(this.m_user.getContractor() == null){
				/*
				 * This is a new contractor company.
				 */
				ProjectManager.dataService.createContractor(this.m_user,new AsyncCallback<UserData>(){

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(UserData result) {
						m_user = result;
						Contractor con = result.getContractor();
						showQuestions(con);
					}

				});
			}else{
				/*
				 * Show the existing company's questions.
				 */
				Contractor con = m_user.getContractor();
				showQuestions(con);
			}
		}
	}

	protected void showQuestions(Contractor con) {
		for(int i = 0; i < con.getQuestions().length;i++){
			Question q = con.getQuestions()[i];
			Label l = new Label(q.getQuestion());
			questions.setWidget(i, 0, l);
			Widget w = null;
			if(q.getType().equals(Question.TYPE.TEXT)){
				TextBox t = new TextBox();
				if(q.getMaxlength() != null){
					t.setMaxLength(q.getMaxlength());
				}
				if(q.getWidth() != null){
					t.setWidth(q.getWidth());
				}
				w = t;
				if(q.getAnswer() != null && q.getAnswer().length > 0){
					t.setValue(q.getAnswer()[0]);
				}
			}else if(q.getType().equals(Question.TYPE.CHECK)){
				AnswerType[] answertypes = q.getAnswerTypes();
				FlowPanel fp = new FlowPanel();
				for(int ii = 0; ii < answertypes.length;ii++){
					CheckBox c = new CheckBox();
					c.setHTML(answertypes[ii].getDescription());
					if(q.getAnswer() != null){
						for(int j = 0; j < q.getAnswer().length;j++){
							if(answertypes[ii].getValue() != null && answertypes[ii].getValue().equals(q.getAnswer()[j])){
								c.setValue(true);
							}
						}
					}
					fp.add(c);
					c.setFormValue(answertypes[ii].getValue());
				}
				w = fp;
			}else if(q.getType().equals(Question.TYPE.LIST)){
				AnswerType[] answertypes = q.getAnswerTypes();
				ListBox list = new ListBox();
				for(int ii = 0; ii < answertypes.length;ii++){
					list.addItem(answertypes[ii].getDescription(), answertypes[ii].getValue());
					if(q.getAnswer() != null){
						for(int j = 0; j < q.getAnswer().length;j++){
							if(answertypes[ii].getValue() != null && answertypes[ii].getValue().equals(q.getAnswer()[j])){
								list.setItemSelected(ii, true);
							}
						}
					}
				}
				w = list;
			}else if(q.getType().equals(Question.TYPE.RADIO)){
				AnswerType[] answertypes = q.getAnswerTypes();
				FlowPanel fp = new FlowPanel();
				for(int ii = 0; ii < answertypes.length;ii++){
					RadioButton r = new RadioButton("q"+q.getId());
					r.setFormValue(answertypes[ii].getValue());
					r.setHTML(answertypes[ii].getDescription());
					if(q.getAnswer() != null){
						for(int j = 0; j < q.getAnswer().length;j++){
							if(answertypes[ii].getValue() != null && answertypes[ii].getValue().equals(q.getAnswer()[j])){
								r.setValue(true);
							}
						}
					}
					fp.add(r);
				}
				w = fp;
			}
			questions.setWidget(i, 1, w);
		}
	}

	public UserData getAnswers() {
		for(int i = 0; i < questions.getRowCount();i++){
			Widget w = questions.getWidget(i, 1);
			Vector<String> answers = new Vector<String>();
			if(w instanceof TextBox){
				TextBox t = (TextBox)w;
				String[] answer = new String[1];
				answer[0] = t.getValue();
				this.m_user.getContractor().getQuestions()[i].setAnswer(answer);
			}else if(w instanceof ListBox){
				ListBox list = (ListBox)w;
				for(int j = 0;j < list.getItemCount();j++){
					if(list.isItemSelected(j)){
						answers.add(list.getValue(j));
					}
				}
				this.m_user.getContractor().getQuestions()[i].setAnswer(answers.toArray(new String[0]));
			}else if(w instanceof FlowPanel){
				for(int j = 0; j < ((FlowPanel)w).getWidgetCount();j++){
					Widget w2 = ((FlowPanel)w).getWidget(j);
					if(w2 instanceof CheckBox){
						CheckBox c = (CheckBox)w2;
						if(c.getValue()){
							answers.add(c.getFormValue());
						}
					}else if(w2 instanceof RadioButton){
						RadioButton r = (RadioButton)w2;
						if(r.getValue()){
							answers.add(r.getFormValue());
						}
					}
				}
				this.m_user.getContractor().getQuestions()[i].setAnswer(answers.toArray(new String[0]));
			}
		}
		return this.m_user;
	}

}
