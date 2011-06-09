package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConfigStruct;
import com.robaone.dbase.hierarchial.HDBSessionData;
import com.robaone.dbase.hierarchial.types.ConfigType;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.data.Comment;
import com.robaone.gwt.projectmanager.client.data.FeedItem;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.client.data.ProjectGoal;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.data.DBData;
import com.robaone.gwt.projectmanager.server.interfaces.ProjectLogManagerInterface;
import com.robaone.gwt.projectmanager.server.interfaces.UserManagerInterface;

public class ProjectLogManager implements ProjectLogManagerInterface {

	private static final String PROJECTS = "projects";
	private static final String PROJECTGOALS = "projectgoals";
	private static final String COMMENTS = "comments";
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
			cfg = new DBData().findConfig(path);
		}while(cfg != null);
		HDBSessionData session = this.getHDBSessionData();
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.PROJECTNAME,project.getProjectName(),ConfigType.STRING,"Project Name","The project name"),session);
		project.setId(cfg.getFolderID().toString());
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.DESCRIPTION,project.getDescription(),ConfigType.TEXT,"Project Description","A detailed description of the project"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.DUEDATE,project.getDue_date(),ConfigType.TEXT,"Due Date","The Date the project is due to be completed"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.ESTIMATEDHOURS,project.getEst_hours(),ConfigType.DOUBLE,"Estimated Hours","The number of hours it will take to complete the project"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.IMPORTANT,project.isImportant(),ConfigType.BOOLEAN,"Important","This value is true if the project is important.  Important projects get priority over normal projects"),session);
		JSONObject jo = new JSONObject();
		jo.put(Project.ASSIGNMENTS, project.getAssignments());
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.ASSIGNMENTS,jo,ConfigType.JSON,"Assignments","A list of people who are attached to a project"),session);
		TagManager tagman = new TagManager(this.parent);
		String[] tagids = new String[project.getTags().length];
		for(int i = 0; i < project.getTags().length;i++){
			BigDecimal tagid = tagman.getTagIdForName(project.getTags()[i]);
			tagids[i] = tagid.toString();
		}
		JSONObject tags = new JSONObject();
		tags.put(Project.TAGS, tagids);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.TAGS,tags,ConfigType.JSON,"Tag IDs","The list if ids for the tags on this project"),session);
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
		ConfigManager cfg = new DBData().findConfig(path);
		int retval = 0;
		if(cfg == null){
			cfg = new DBData().set(new ConfigStruct(path,1,"The next project id","This value is the counter for the unique project ids.  It should not be edited manually unless necessary."),session);
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
		ConfigManager project_cfg = new DBData().findConfig(new BigDecimal(m_data.getProjectId()));
		if(project_cfg == null){
			retval.setStatus(ProjectConstants.GENERAL_ERROR);
			retval.setError("Associated project could not be found");
		}
		String[] cp = {PROJECTGOALS,"counter"};
		String path = null;
		if(m_data.getId() == null){
			int counter = new DBData().newCounter(this, cp, this.getHDBSessionData());
			path = project_cfg.getAbsolutePath();
			path += "/"+PROJECTGOALS+"/"+counter;
		}else{
			ConfigManager cfg = new DBData().findConfig(new BigDecimal(m_data.getId()));
			path = cfg.getAbsolutePath();
		}
		HDBSessionData sessiondata = this.getHDBSessionData();
		ConfigManager name = new DBData().setdefault(new ConfigStruct(path+"/"+ProjectGoal.NAME,m_data.getName(),ConfigType.STRING,"Name","The name of the goal"),sessiondata);
		name.setValue(m_data.getName(), sessiondata);
		ConfigManager projectid = new DBData().setdefault(new ConfigStruct(path+"/"+ProjectGoal.PROJECTID,m_data.getProjectId(),ConfigType.STRING,"Project id","This field is used to reference the projects"),sessiondata);
		projectid.setValue(m_data.getProjectId(), sessiondata);
		m_data.setId(name.getFolderID().toString());
		retval.addData(m_data);

		ConfigManager status = null;
		try{
			status = new DBData().setdefault(new ConfigStruct(path+"/"+ProjectGoal.STATUS,m_data.getStatus(),ConfigType.STRING,"Status","Special values include \"Not Started\", \"Canceled\" and \"Done\"."),sessiondata);
		}catch(Exception e){}
		if(status != null){
			if(m_data.getStatus() != null)
				status.setValue(m_data.getStatus(), sessiondata);
			else
				status.setValueAsNull(sessiondata);
		}
		ConfigManager duedate = null;
		try{
			duedate = new DBData().setdefault(new ConfigStruct(path+"/"+ProjectGoal.DUEDATE,m_data.getDueDate(),ConfigType.DATETIME,"Due Date","The date the goal should be reashed."),sessiondata);
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
		ConfigManager goal_cfg = new DBData().findConfig(new BigDecimal(m_data.getId()));
		goal_cfg.delete(this.getHDBSessionData());
		return retval;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataServiceResponse<ProjectGoal> getGoalsForProject(Project proj)
	throws Exception {
		DataServiceResponse<ProjectGoal> retval = new DataServiceResponse<ProjectGoal>();
		ConfigManager project_cfg = new DBData().findConfig(new BigDecimal(proj.getId()));
		if(project_cfg != null){
			String path = project_cfg.getAbsolutePath() + "/" + PROJECTGOALS;
			ConfigManager[] goals = new DBData().findFolderContentbyPath(path);
			for(int i = 0; i < goals.length;i++){
				ConfigManager[] cfs = new DBData().findFolderContentbyId(goals[i].getId());
				HashMap<String,ConfigManager> map = ConfigManager.getMap(cfs);
				ProjectGoal goal = new ProjectGoal();
				goal.setId(goals[i].getId().toString());
				try{goal.setDueDate(map.get(ProjectGoal.DUEDATE).getDateTime());}catch(Exception e){}
				goal.setName(map.get(ProjectGoal.NAME).getString());
				try{goal.setStatus(map.get(ProjectGoal.STATUS).getString());}catch(Exception e){}
				goal.setProjectId(map.get(ProjectGoal.PROJECTID).getString());
				retval.addData(goal);
			}
		}			
		return retval;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataServiceResponse<FeedItem> getFeed() throws Exception {
		final DataServiceResponse<FeedItem> response = new DataServiceResponse<FeedItem>();
		String[] params = {PROJECTS};
		String path = ConfigManager.path(this, params);
		final ConfigManager[] projects = new DBData().findFolderContentbyPath(path);
		if(projects != null){
			for(int i = 0; i < projects.length;i++){
				ConfigManager[] projectvalues = new DBData().findFolderContentbyId(projects[i].getId());
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
		ConfigManager[] cfg = new DBData().findFolderContentbyId(new BigDecimal(id));
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
		try{project.setStatus(map.get(Project.STATUS).getString());}catch(Exception e){}
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
		ConfigManager cfg = new DBData().findConfig(new BigDecimal(project.getId()));
		String path = cfg.getAbsolutePath();
		HDBSessionData session = this.getHDBSessionData();
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.PROJECTNAME,project.getProjectName(),ConfigType.STRING,"Project Name","The project name"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.DESCRIPTION,project.getDescription(),ConfigType.TEXT,"Project Description","A detailed description of the project"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.DUEDATE,project.getDue_date(),ConfigType.DATETIME,"Due Date","The Date the project is due to be completed"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.ESTIMATEDHOURS,project.getEst_hours(),ConfigType.DOUBLE,"Estimated Hours","The number of hours it will take to complete the project"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.IMPORTANT,project.isImportant(),ConfigType.BOOLEAN,"Important","This value is true if the project is important.  Important projects get priority over normal projects"),session);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.STATUS,project.getStatus(),ConfigType.STRING,"Status","The status of the project.  Important entries are 'Not Started','On Hold','In Progress','Done' and 'Cancelled'."),session);
		JSONObject jo = new JSONObject();
		jo.put(Project.ASSIGNMENTS, project.getAssignments());
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.ASSIGNMENTS,jo,ConfigType.JSON,"Assignments","A list of people who are attached to a project"),session);
		TagManager tagman = new TagManager(this.parent);
		String[] tagids = new String[project.getTags().length];
		for(int i = 0; i < project.getTags().length;i++){
			BigDecimal tagid = tagman.getTagIdForName(project.getTags()[i]);
			tagids[i] = tagid.toString();
		}
		JSONObject tags = new JSONObject();
		tags.put(Project.TAGS, tagids);
		cfg = new DBData().set(new ConfigStruct(path+"/"+Project.TAGS,tags,ConfigType.JSON,"Tag IDs","The list if ids for the tags on this project"),session);
		retval.addData(project);
		retval.setStatus(0);
		return retval;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataServiceResponse<Comment> getCommentsForGoal(String id)
			throws Exception {
		DataServiceResponse<Comment> retval = new DataServiceResponse<Comment>();
		ConfigManager goal_cfg = new DBData().findConfig(new BigDecimal(id));
		if(goal_cfg != null){
			String path = goal_cfg.getAbsolutePath()+"/"+COMMENTS;
			ConfigManager[] comment_cfg = new DBData().findFolderContentbyPath(path);
			for(int i = 0; i < comment_cfg.length;i++){
				ConfigManager[] cmmt = new DBData().findFolderContentbyId(comment_cfg[i].getId());
				HashMap<String,ConfigManager> map = ConfigManager.getMap(cmmt);
				Comment a_comment = new Comment();
				a_comment.setId(comment_cfg[i].getId().toString());
				a_comment.setComment(map.get(Comment.COMMENT).getString());
				a_comment.setGoalId(map.get(Comment.GOALID).getString());
				try{a_comment.setHours(map.get(Comment.HOURS).getDouble());}catch(Exception e){}
				a_comment.setModifiedDate(map.get(Comment.COMMENT).getModifiedDate());
				try{a_comment.setWorkDate(map.get(Comment.WORKDATE).getDateTime());}catch(Exception e){}
				UserManager uman = new UserManager(this.parent);
				a_comment.setUserData(((UserManagerInterface)uman).getUserData(map.get(Comment.COMMENT).getCreatedBy()));
				retval.addData(a_comment);
			}
			/*
			HDBSessionData session = this.getHDBSessionData();
			String[] params = {COMMENTS,"counter"};
			int counter = ConfigManager.newCounter(this, params, session);
			path += "/"+counter;
			*/
		}
		return retval;
	}

	@Override
	public DataServiceResponse<Comment> saveCommentforGoal(Comment m_comment)
			throws Exception {
		DataServiceResponse<Comment> retval = new DataServiceResponse<Comment>();
		ConfigManager goal = new DBData().findConfig(new BigDecimal(m_comment.getGoalId()));
		if(goal == null){
			throw new Exception("The associated goal could not be found");
		}
		String path = goal.getAbsolutePath() + "/"+COMMENTS;
		HDBSessionData session = this.getHDBSessionData();
		if(m_comment.getId() == null){
			String[] params = {COMMENTS,"counter"};
			int counter = new DBData().newCounter(this, params, session);
			path += "/"+counter;
		}else{
			ConfigManager cfg = new DBData().findConfig(new BigDecimal(m_comment.getId()));
			path = cfg.getAbsolutePath();
		}
		ConfigManager cfg = new DBData().set(new ConfigStruct(path+"/"+Comment.COMMENT,m_comment.getComment(),ConfigType.TEXT,"Comment","The comment text"),session);
		java.util.Date modified = cfg.getModifiedDate();
		cfg = new DBData().set(new ConfigStruct(path+"/"+Comment.GOALID,m_comment.getGoalId(),ConfigType.STRING,"The goalid","This references back to the associate goal."),session);
		m_comment.setId(cfg.getFolderID().toString());
		if(m_comment.getHours() != null){
		cfg = new DBData().set(new ConfigStruct(path+"/"+Comment.HOURS,m_comment.getHours(),"Hours","Hours of work"),session);
		}else{
			cfg = new DBData().findConfig(path+"/"+Comment.HOURS);
			if(cfg != null){
				cfg.setValueAsNull(session);
			}
		}
		if(m_comment.getWorkDate() != null){
		cfg = new DBData().set(new ConfigStruct(path+"/"+Comment.WORKDATE,m_comment.getWorkDate(),ConfigType.DATETIME,"Work Date","The day the work was done"),session);
		}else{
			cfg = new DBData().findConfig(path+"/"+Comment.WORKDATE);
			if(cfg != null){
				cfg.setValueAsNull(session);
			}
		}
		m_comment.setModifiedDate(modified);
		
		
		retval.addData(m_comment);
		return retval;
	}

	@Override
	public DataServiceResponse<Comment> deleteComment(Comment m_comment)
			throws Exception {
		DataServiceResponse<Comment> retval = new DataServiceResponse<Comment>();
		ConfigManager cfg = new DBData().findConfig(new BigDecimal(m_comment.getId()));
		cfg.delete(this.getHDBSessionData());
		retval.addData(m_comment);
		return retval;
	}

	@Override
	public int getCommentCountforGoal(String id) throws Exception {
		ConfigManager g_cfg = new DBData().findConfig(new BigDecimal(id));
		if(g_cfg != null){
			String path = g_cfg.getAbsolutePath();
			path += "/"+COMMENTS;
			ConfigManager[] comments = new DBData().findFolderContentbyPath(path);
			return comments.length;
		}
		return 0;
	}

}
