package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Question implements IsSerializable {
	private String question;
	public static enum TYPE {TEXT,RADIO,LIST,CHECK};
	private TYPE type;
	private String[] answer;
	private Integer maxlength;
	private AnswerType[] answertypes;
	private int id;
	private String width;
	public Question(){
		
	}

	public String getQuestion() {
		return this.question;
	}
	public void setQuestion(String q){
		this.question = q;
	}

	public TYPE getType() {
		return type;
	}
	public void setType(TYPE type){
		this.type = type;
	}

	public String[] getAnswer() {
		return this.answer;
	}

	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	public Integer getMaxlength() {
		return this.maxlength;
	}
	
	public void setMaxlength(int maxlength){
		this.maxlength = new Integer(maxlength);
	}

	public AnswerType[] getAnswerTypes() {
		return this.answertypes;
	}
	
	public void setAnswerTypes(AnswerType[] types ){
		this.answertypes = types;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public String getWidth() {
		return this.width;
	}
	
	public void setWidth(String width){
		this.width = width;
	}
}
