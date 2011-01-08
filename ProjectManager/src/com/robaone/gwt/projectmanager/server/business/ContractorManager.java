package com.robaone.gwt.projectmanager.server.business;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.interfaces.ContractorManagerInterface;

public class ContractorManager implements ContractorManagerInterface {
	@SuppressWarnings("unused")
	private DataServiceImpl parent;
	public ContractorManager(DataServiceImpl dataServiceImpl) {
		parent = dataServiceImpl;
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
		return retval;
	}

}
