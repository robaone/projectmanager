package com.robaone.gwt.projectmanager.server.business;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.HDBSessionData;
import com.robaone.dbase.hierarchial.types.ConfigType;
import com.robaone.gwt.projectmanager.client.DataServiceResponse;
import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.client.data.Project;
import com.robaone.gwt.projectmanager.server.DataServiceImpl;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.interfaces.ProjectLogManagerInterface;

public class ProjectLogManager implements ProjectLogManagerInterface {

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
		params[0] = "projects";
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
		jo.put("assignments", project.getAssignments());
		cfg = new ConfigManager(path+"/"+Project.ASSIGNMENTS,jo,"Assignments","A list of people who are attached to a project",session);
		TagManager tagman = new TagManager(this.parent);
		for(int i = 0; i < project.getTags().length;i++){
			BigDecimal tagid = tagman.getTagIdForName(project.getTags()[i]);
			params = new String[0];
			cfg = new ConfigManager(path+"/"+Project.TAGS+"/"+tagid,true,"Tag Id","The tag id corresponds to the tag name located at "+ConfigManager.path(tagman, params),session);
		}
		retval.addData(project);
		retval.setStatus(0);
		return retval;
	}
	public HDBSessionData getHDBSessionData(){
		HDBSessionData session = new HDBSessionData(this.parent.getSessionData().getUserData().getUsername(),this.parent.getSessionData().getCurrentHost());
		return session;
	}
	private int newProjectId() throws Exception {
		String[] params = new String[2];
		params[0] = "projects";
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

}
