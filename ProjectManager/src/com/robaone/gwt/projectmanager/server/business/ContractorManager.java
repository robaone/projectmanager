package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.AnswerType;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.Question;
import com.robaone.gwt.projectmanager.client.data.Question.TYPE;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.interfaces.ContractorManagerInterface;

public class ContractorManager implements ContractorManagerInterface {
	@SuppressWarnings("unused")
	private DataServiceImpl parent;
	private Question[] questions;
	public ContractorManager(DataServiceImpl dataServiceImpl) {
		parent = dataServiceImpl;
		questions = new Question[7];
		String[] answer = {"value2"};
		for(int i = 0; i < questions.length-1;i++){
			questions[i] = new Question();
			questions[i].setQuestion("Question "+i);
			questions[i].setType(TYPE.TEXT);
			questions[i].setWidth("80%");
			questions[i].setMaxlength(10);
		}
		questions[4] = new Question();
		questions[4].setQuestion("Check boxes");
		questions[4].setType(TYPE.CHECK);
		AnswerType[] types = new AnswerType[3];
		for(int i = 0; i < 3; i++){
			types[i] = new AnswerType();
			types[i].setDescription("Description "+i);
			types[i].setValue("value"+i);
		}
		questions[4].setAnswerTypes(types);
		questions[4].setAnswer(answer);
		questions[5] = new Question();
		questions[5].setQuestion("List");
		questions[5].setType(TYPE.LIST);
		questions[5].setAnswerTypes(types);
		questions[5].setAnswer(answer);
		questions[6] = new Question();
		questions[6].setQuestion("Radio Buttons");
		questions[6].setType(TYPE.RADIO);
		questions[6].setAnswerTypes(types);
		questions[6].setAnswer(answer);
	}
	@Override
	public DataServiceResponse<ContractorData> getContractors(String zipcode,int category) {
		DataServiceResponse<ContractorData> retval = new DataServiceResponse<ContractorData>();
		for(int i = 0; i < 3;i++){
			ContractorData c = new ContractorData();
			c.setCategory("Category "+i);
			c.setName("Contractor "+i);
			c.setId(i);
			retval.addData(c);
		}
		return retval;
	}
	@Override
	public DataServiceResponse<Category> getContractorCategories(
			String zipcode) throws Exception {
		DataServiceResponse<Category> retval = new DataServiceResponse<Category>();
		for(int i = 0; i < 7;i++){
			Category c = new Category();
			c.setName("Category "+i);
			c.setCount(3);
			c.setId(i);
			retval.addData(c);
		}
		return retval;
	}
	@Override
	public Category getCategory(int cat) throws Exception {
		Category retval = new Category();
		retval.setId(cat);
		retval.setName("Category "+cat);
		retval.setCount(3);
		return retval;
	}
	@Override
	public Contractor getContractor(int id) throws Exception {
		Contractor retval = new Contractor();
		retval.setId(id);
		retval.setName("Contractor "+id);
		retval.setSummary("This is a summary.");
		retval.setInfo("This is the contractor information saved in HTML.");
		retval.setLogo_url("http://www.new-england-contractor.com/poole/george_poole_logo.gif");
		retval.setQuestions(this.questions);
		return retval;
	}
	@Override
	public Contractor createContractor() throws Exception {
		Contractor retval = new Contractor();
		retval.setId(20);
		retval.setQuestions(this.questions);
		return retval;
	}

}
