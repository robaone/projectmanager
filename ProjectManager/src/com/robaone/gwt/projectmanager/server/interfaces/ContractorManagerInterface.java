package com.robaone.gwt.projectmanager.server.interfaces;

import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;

public interface ContractorManagerInterface {

	DataServiceResponse<ContractorData> getContractors(String zipcode,int category) throws Exception;

	DataServiceResponse<Category> getContractorCategories(String zipcode) throws Exception;

	Category getCategory(int cat) throws Exception;

	Contractor getContractor(int id) throws Exception;

}
