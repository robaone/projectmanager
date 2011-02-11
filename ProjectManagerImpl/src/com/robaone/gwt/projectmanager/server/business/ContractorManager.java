package com.robaone.gwt.projectmanager.server.business;

import com.robaone.data.Database;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.data.AnswerType;
import com.robaone.gwt.projectmanager.client.data.Category;
import com.robaone.gwt.projectmanager.client.data.Contractor;
import com.robaone.gwt.projectmanager.client.data.ContractorData;
import com.robaone.gwt.projectmanager.client.data.ContractorListing;
import com.robaone.gwt.projectmanager.client.data.Question;
import com.robaone.gwt.projectmanager.client.data.Question.TYPE;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.interfaces.ContractorManagerInterface;
import com.sohvac.data.Dbo_sohvac_listing_jdo;
import com.sohvac.data.Dbo_sohvac_listing_jdoManager;
import com.sohvac.data.Dbo_sohvac_service_jdo;
import com.sohvac.data.Dbo_sohvac_service_jdoManager;

public class ContractorManager implements ContractorManagerInterface {
	@SuppressWarnings("unused")
	private DataServiceImpl parent;
	public ContractorManager(DataServiceImpl dataServiceImpl) {
		parent = dataServiceImpl;

	}
	@Override
	public DataServiceResponse<ContractorData> getContractors(String zipcode,int category) {
		DataServiceResponse<ContractorData> retval = new DataServiceResponse<ContractorData>();
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_sohvac_listing_jdoManager man = new Dbo_sohvac_listing_jdoManager(con);
			String str = "select a.sohvac_listingid from sohvac_listing a,\n"+
			"(select distinct c.sohvac_listingid from sohvac_zips a,sohva"+
			"c_servicearea b,sohvac_listingservicearea c where a.PostalCo"+
			"de=?\n"+
			"and b.sohvac_county = a.county and b.sohvac_serviceareaid = "+
			"c.sohvac_serviceareaid) b,\n"+
			"(select distinct sohvac_listingid from sohvac_listingservice"+
			" where sohvac_serviceid in (?)) c\n"+
			"where a.sohvac_listingid = b.sohvac_listingid\n"+
			"and a.sohvac_listingid = c.sohvac_listingid\n"+
			"order by a.sohvac_businessname;";
			ps = con.prepareStatement(str);
			ps.setString(1, zipcode);
			ps.setInt(2, category);
			rs = ps.executeQuery();
			while(rs.next()){
				int cat_id = rs.getInt(1);
				Dbo_sohvac_listing_jdo record = man.getDbo_sohvac_listing(new Integer(cat_id));
				ContractorData c = new ContractorData();
				c.setId(record.getSohvac_listingid());
				c.setLocation(record.getSohvac_city());
				c.setName(record.getSohvac_businessname());
				retval.addData(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		return retval;
	}
	@Override
	public DataServiceResponse<Category> getContractorCategories(
			String zipcode) throws Exception {
		DataServiceResponse<Category> retval = new DataServiceResponse<Category>();
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			con = Database.getConnection(Database.SOHVAC);
			String str = "select p1.Sohvac_ServiceID, p1.sohvac_name from Sohvac_Service p1,\n"+
			"(select distinct a.sohvac_serviceid from\n"+
			"(select a.sohvac_listingid from sohvac_listing a,\n"+
			"(select distinct c.sohvac_listingid from sohvac_zips a,sohva"+
			"c_servicearea b,sohvac_listingservicearea c where a.PostalCo"+
			"de=?\n"+
			"and b.sohvac_county = a.county and b.sohvac_serviceareaid = "+
			"c.sohvac_serviceareaid) b,\n"+
			"(select distinct sohvac_listingid from sohvac_listingservice"+
			") c\n"+
			"where a.sohvac_listingid = b.sohvac_listingid\n"+
			"and a.sohvac_listingid = c.sohvac_listingid) b,\n"+
			"sohvac_listingservice a\n"+
			"where a.Sohvac_ListingID = b.Sohvac_ListingID) p2\n"+
			"where p1.Sohvac_ServiceID = p2.Sohvac_ServiceID\n"+
			"order by p1.sohvac_main";
			ps = con.prepareStatement(str);
			ps.setString(1, zipcode);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				Category c = new Category();
				c.setId(id);
				c.setName(name);
				retval.addData(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		return retval;
	}
	@Override
	public Category getCategory(int cat) throws Exception {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			con = Database.getConnection(Database.SOHVAC);
			Dbo_sohvac_service_jdoManager man = new Dbo_sohvac_service_jdoManager(con);
			Dbo_sohvac_service_jdo record = man.getDbo_sohvac_service(new Integer(cat));
			Category retval = new Category();
			retval.setId(record.getSohvac_serviceid());
			retval.setName(record.getSohvac_name());
			return retval;
		}catch(Exception e){
		  e.printStackTrace();
		  throw new Exception(e);
		}finally{
		  try{rs.close();}catch(Exception e){}
		  try{ps.close();}catch(Exception e){}
		  try{con.close();}catch(Exception e){}
		}
	}
	@Override
	public Contractor getContractor(int id) throws Exception {
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{

		}catch(Exception e){
		  e.printStackTrace();
		}finally{
		  try{rs.close();}catch(Exception e){}
		  try{ps.close();}catch(Exception e){}
		  try{con.close();}catch(Exception e){}
		}
		Contractor retval = new Contractor();
		retval.setId(id);
		retval.setName("Contractor "+id);
		retval.setSummary("This is a summary.");
		retval.setInfo("This is the contractor information saved in HTML.");
		retval.setLogo_url("http://www.new-england-contractor.com/poole/george_poole_logo.gif");
		
		return retval;
	}
	@Override
	public Contractor createContractor() throws Exception {
		Contractor retval = new Contractor();
		retval.setId(20);
		
		return retval;
	}
	@Override
	public ContractorListing getContractorListing(int id) throws Exception {
		ContractorListing listing = new ContractorListing();
		listing.setTitle("Listing Title");
		listing.setContent("Content");
		return listing;
	}

}
