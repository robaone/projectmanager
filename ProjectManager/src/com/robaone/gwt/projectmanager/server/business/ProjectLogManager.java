package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.dbase.hierarchial.HDBSessionData;
import com.robaone.dbase.hierarchial.ProjectDatabase;
import com.robaone.dbase.hierarchial.types.ConfigType;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.interfaces.ProjectLogManagerInterface;

public class ProjectLogManager implements ProjectLogManagerInterface {

	private static final String PROJECTS = "projects";
	private DataServiceImpl parent;

	public ProjectLogManager(DataServiceImpl dataServiceImpl) {
		this.parent = dataServiceImpl;
	}

	@Override
	public void writeLog(String message) {
		ProjectDebug.write(ProjectDebug.SOURCE.GWT, message);
	}

	@Override
	public DataServiceResponse<Project> createProject(Project project)
	throws Exception {
		DataServiceResponse<Project> retval = new DataServiceResponse<Project>();
		if(project.getProjectName() == null || project.getProjectName().length() < 3){
			retval.addFieldError(Project.PROJECTNAME, "The project name needs to be longer than 3 characters");
			retval.setStatus(ProjectConstants.FIELD_VERIFICATION_ERROR);
		}
		if(retval.getStatus() != ProjectConstants.OK){
			return retval;
		}
		ConfigManager cfg;
		String path;
		String[] params = new String[2];
		params[0] = PROJECTS;
		do{
			params[1] = ""+this.newProjectId();
			path = ConfigManager.path(this,params);
			cfg = ConfigManager.findConfig(path);
		}while(cfg != null);
		project.setId(params[1]);
		HDBSessionData session = this.getHDBSessionData();
		cfg = new ConfigManager(path+"/"+Project.PROJECTNAME,project.getProjectName(),ConfigType.STRING,"Project Name","The project name",session);
		cfg = new ConfigManager(path+"/"+Project.DESCRIPTION,project.getDescription(),ConfigType.TEXT,"Project Description","A detailed description of the project",session);
		cfg = new ConfigManager(path+"/"+Project.DUEDATE,project.getDue_date(),"Due Date","The Date the project is due to be completed",session);
		cfg = new ConfigManager(path+"/"+Project.ESTIMATEDHOURS,project.getEst_hours(),"Estimated Hours","The number of hours it will take to complete the project",session);
		cfg = new ConfigManager(path+"/"+Project.IMPORTANT,project.isImportant(),"Important","This value is true if the project is important.  Important projects get priority over normal projects",session);
		JSONObject jo = new JSONObject();
		jo.put(Project.ASSIGNMENTS, project.getAssignments());
		cfg = new ConfigManager(path+"/"+Project.ASSIGNMENTS,jo,"Assignments","A list of people who are attached to a project",session);
		TagManager tagman = new TagManager(this.parent);
		String[] tagids = new String[project.getTags().length];
		for(int i = 0; i < project.getTags().length;i++){
			BigDecimal tagid = tagman.getTagIdForName(project.getTags()[i]);
			tagids[i] = tagid.toString();
		}
		JSONObject tags = new JSONObject();
		tags.put(Project.TAGS, tagids);
		cfg = new ConfigManager(path+"/"+Project.TAGS,tags,"Tag IDs","The list if ids for the tags on this project",session);
		retval.addData(project);
		retval.setStatus(0);
		//this.getFeed(); //For testing
		return retval;
	}
	public HDBSessionData getHDBSessionData(){
		HDBSessionData session = new HDBSessionData(this.parent.getSessionData().getUserData().getUsername(),this.parent.getSessionData().getCurrentHost());
		return session;
	}
	private int newProjectId() throws Exception {
		String[] params = new String[2];
		params[0] = PROJECTS;
		params[1] = "nextid";
		String path = ConfigManager.path(this, params);
		HDBSessionData session = new HDBSessionData(this.parent.getSessionData().getUserData().getUsername(),this.parent.getSessionData().getCurrentHost());
		ConfigManager cfg = ConfigManager.findConfig(path);
		int retval = 0;
		if(cfg == null){
			cfg = new ConfigManager(path,1,"The next project id","This value is the counter for the unique project ids.  It should not be edited manually unless necessary.",session);
		}else{
			retval = cfg.getInt();
			cfg.setValue(cfg.getInt()+1, session);
		}

		return retval;
	}

	@Override
	public DataServiceResponse<ProjectGoal> saveProjectGoal(ProjectGoal m_data)
	throws Exception {
		DataServiceResponse<ProjectGoal> retval = new DataServiceResponse<ProjectGoal>();
		String[] cp = {"projectgoals","counter"};
		int counter = ConfigManager.newCounter(this, cp, this.getHDBSessionData());
		m_data.setId(""+counter);
		retval.addData(m_data);
		String[] params = {"projectgoals",""+counter};
		String path = ConfigManager.path(this, params);
		HDBSessionData sessiondata = this.getHDBSessionData();
		ConfigManager name = new ConfigManager(path+"/"+ProjectGoal.NAME,m_data.getName(),ConfigType.STRING,"Name","The name of the goal",sessiondata);
		name.setValue(m_data.getName(), sessiondata);
		ConfigManager projectid = new ConfigManager(path+"/"+ProjectGoal.PROJECTID,m_data.getProjectId(),ConfigType.STRING,"Project id","This field is used to reference the projects",sessiondata);
		projectid.setValue(m_data.getProjectId(), sessiondata);

		ConfigManager status = null;
		try{
			status = new ConfigManager(path+"/"+ProjectGoal.STATUS,m_data.getStatus(),ConfigType.STRING,"Status","Special values include \"Not Started\", \"Canceled\" and \"Done\".",sessiondata);
		}catch(Exception e){}
		if(status != null){
			if(m_data.getStatus() != null)
				status.setValue(m_data.getStatus(), sessiondata);
			else
				status.setValueAsNull(sessiondata);
		}
		ConfigManager duedate = null;
		try{
			duedate = new ConfigManager(path+"/"+ProjectGoal.DUEDATE,m_data.getDueDate(),"Due Date","The date the goal should be reashed.",sessiondata);
		}catch(Exception e){}
		if(duedate != null){
			if(m_data.getDueDate() != null)
				duedate.setValue(new java.sql.Timestamp(m_data.getDueDate().getTime()), sessiondata);
			else
				duedate.setValueAsNull(sessiondata);
		}
		return retval;
	}

	@Override
	public DataServiceResponse<ProjectGoal> deleteProjectGoal(ProjectGoal m_data)
	throws Exception {
		DataServiceResponse<ProjectGoal> retval = new DataServiceResponse<ProjectGoal>();
		String[] params = {"projectgoals",m_data.getId()};
		ConfigManager cfg = new ConfigManager(ConfigManager.path(this, params));
		cfg.delete(this.getHDBSessionData());
		return retval;
	}

	@Override
	public DataServiceResponse<ProjectGoal> getGoalsForProject(Project proj)
	throws Exception {
		DataServiceResponse<ProjectGoal> retval = new DataServiceResponse<ProjectGoal>();
		java.sql.Connection con = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		ProjectDatabase db = new ProjectDatabase();
		try{
			con = db.getConnection();
			String[] params = {"projectgoals"};
			ConfigManager cfg = new ConfigManager(ConfigManager.path(this, params));
			String str = "select parent from CONFIG where parent in (select ID from CONFIG where parent = ?) and "+
			"name = ? and string_value = ?";
			try{cfg.getId();}catch(java.lang.NullPointerException ne){
				return retval;
			}
			ps = con.prepareStatement(str);
			ps.setBigDecimal(1, cfg.getId());
			ps.setString(2, ProjectGoal.PROJECTID);
			ps.setString(3, proj.getId());
			rs = ps.executeQuery();
			while(rs.next()){

				ConfigManager[] cfs = ConfigManager.findFolderContentbyId(rs.getBigDecimal(1));
				HashMap<String,ConfigManager> map = ConfigManager.getMap(cfs);

				ProjectGoal goal = new ProjectGoal();
				goal.setId(cfg.getId().toString());
				try{goal.setDueDate(map.get(ProjectGoal.DUEDATE).getDateTime());}catch(Exception e){}
				goal.setName(map.get(ProjectGoal.NAME).getString());
				try{goal.setStatus(map.get(ProjectGoal.STATUS).getString());}catch(Exception e){}
				goal.setProjectId(map.get(ProjectGoal.PROJECTID).getString());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{rs.close();}catch(Exception e){}
			try{ps.close();}catch(Exception e){}
			try{con.close();}catch(Exception e){}
		}
		return retval;
	}

	@Override
	public DataServiceResponse<FeedItem> getFeed() throws Exception {
		final DataServiceResponse<FeedItem> response = new DataServiceResponse<FeedItem>();
		String[] params = {PROJECTS};
		String path = ConfigManager.path(this, params);
		final ConfigManager[] projects = ConfigManager.findFolderContentbyPath(path);
		if(projects != null){
			for(int i = 0; i < projects.length;i++){
				ConfigManager[] projectvalues = ConfigManager.findFolderContentbyId(projects[i].getId());
				if(projectvalues != null && projectvalues.length > 0){
					HashMap<String,ConfigManager> map = ConfigManager.getMap(projectvalues);
					FeedItem item = new FeedItem();
					item.setReferenceid(projects[i].getId().toString());
					try{item.setDatetime(map.get(Project.PROJECTNAME).getCreatedDate());}catch(Exception e){}
					try{item.setIconurl("projectmanager/project.png");}catch(Exception e){}
					try{item.setSummary(map.get(Project.DESCRIPTION).getString());}catch(Exception e){}
					try{item.setTitle	(map.get(Project.PROJECTNAME).getString());}catch(Exception e){}
					response.addData(item);
				}
			}
		}
		return response;
	}

	@Override
	public DataServiceResponse<Project> getProject(String id) throws Exception {
		ConfigManager[] cfg = ConfigManager.findFolderContentbyId(new BigDecimal(id));
		@SuppressWarnings("unchecked")
		HashMap<String,ConfigManager> map = ConfigManager.getMap(cfg);
		if(cfg.length == 0){
			throw new Exception("There is no project for this project id");
		}
		Project project = new Project(); 
		project.setDescription(map.get(Project.DESCRIPTION).getString());
		project.setDue_date(map.get(Project.DUEDATE).getDateTime());
		project.setEst_hours(map.get(Project.ESTIMATEDHOURS).getDouble());
		project.setId(id);
		project.setImportant(map.get(Project.IMPORTANT).getBoolean());
		project.setProjectName(map.get(Project.PROJECTNAME).getString());
		project.setAssignments(getStringArray(map.get(Project.ASSIGNMENTS).getJSON().getJSONArray(Project.ASSIGNMENTS)));
		try{
			String[] tagids = getStringArray(map.get(Project.TAGS).getJSON().getJSONArray(Project.TAGS));
			Vector<String> tagnames = new Vector<String>();
			for(int i = 0; i < tagids.length;i++){
				TagManager tman = new TagManager(this.parent);
				String name = tman.getTagNameforId(tagids[i]);
				tagnames.add(name);
			}
			project.setTags(tagnames.toArray(new String[0]));
		}catch(Exception e){
			
		}
		DataServiceResponse<Project> retval = new DataServiceResponse<Project>();
		retval.addData(project);
		return retval;
	}

	private String[] getStringArray(JSONArray jsonArray) throws JSONException {
		String[] str = new String[jsonArray.length()];
			for(int i = 0 ; i < jsonArray.length();i++){
				str[i] = jsonArray.getString(i);
			}
		
		return str;
	}

	@Override
	public DataServiceResponse<Project> saveProject(Project project)
			throws Exception {
		DataServiceResponse<Project> retval = new DataServiceResponse<Project>();
		if(project.getProjectName() == null || project.getProjectName().length() < 3){
			retval.addFieldError(Project.PROJECTNAME, "The project name needs to be longer than 3 characters");
			retval.setStatus(ProjectConstants.FIELD_VERIFICATION_ERROR);
		}
		if(retval.getStatus() != ProjectConstants.OK){
			return retval;
		}
		ConfigManager cfg = ConfigManager.findConfig(new BigDecimal(project.getId()));
		String path = cfg.getAbsolutePath();
		HDBSessionData session = this.getHDBSessionData();
		cfg = new ConfigManager(path+"/"+Project.PROJECTNAME,project.getProjectName(),ConfigType.STRING,"Project Name","The project name",session);
		cfg = new ConfigManager(path+"/"+Project.DESCRIPTION,project.getDescription(),ConfigType.TEXT,"Project Description","A detailed description of the project",session);
		cfg = new ConfigManager(path+"/"+Project.DUEDATE,project.getDue_date(),"Due Date","The Date the project is due to be completed",session);
		cfg = new ConfigManager(path+"/"+Project.ESTIMATEDHOURS,project.getEst_hours(),"Estimated Hours","The number of hours it will take to complete the project",session);
		cfg = new ConfigManager(path+"/"+Project.IMPORTANT,project.isImportant(),"Important","This value is true if the project is important.  Important projects get priority over normal projects",session);
		JSONObject jo = new JSONObject();
		jo.put(Project.ASSIGNMENTS, project.getAssignments());
		cfg = new ConfigManager(path+"/"+Project.ASSIGNMENTS,jo,"Assignments","A list of people who are attached to a project",session);
		TagManager tagman = new TagManager(this.parent);
		String[] tagids = new String[project.getTags().length];
		for(int i = 0; i < project.getTags().length;i++){
			BigDecimal tagid = tagman.getTagIdForName(project.getTags()[i]);
			tagids[i] = tagid.toString();
		}
		JSONObject tags = new JSONObject();
		tags.put(Project.TAGS, tagids);
		cfg = new ConfigManager(path+"/"+Project.TAGS,tags,"Tag IDs","The list if ids for the tags on this project",session);
		retval.addData(project);
		retval.setStatus(0);
		return retval;
	}

	@Override
	public DataServiceResponse<Comment> saveCommentsForGoal(String id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataServiceResponse<Comment> saveCommentforGoal(Comment m_comment)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
