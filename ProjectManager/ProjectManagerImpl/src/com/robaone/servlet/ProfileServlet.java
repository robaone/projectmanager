package com.robaone.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import com.robaone.gwt.projectmanager.client.ProjectConstants;
import com.robaone.gwt.projectmanager.server.ProjectDebug;
import com.robaone.gwt.projectmanager.server.SessionData;
import com.robaone.gwt.projectmanager.server.ProjectDebug.SOURCE;
import com.robaone.gwt.projectmanager.server.business.ProjectLogManager;
import com.robaone.util.INIFileReader;

/**
 * Servlet implementation class ProfileServlet
 */
public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static INIFileReader ini;

	private SessionData m_data;

	/**
	 * Default constructor. 
	 */
	public ProfileServlet() {
		// TODO Auto-generated constructor stub
	}

	public static String getParameter(String param) throws Exception{
		try{
			if(ini == null){
				ini = new INIFileReader(System.getProperty("ini.file"));
			}
			String retval = ini.getValue(param);
			return retval;
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProjectDebug.write(SOURCE.LOCAL, "*** Saving image ***");
		// Check that we have a file upload request
		response.setContentType("text/html");
		try{
			this.m_data = (SessionData) request.getSession().getAttribute(ProjectConstants.SESSIONDATA);
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				// Create a factory for disk-based file items
				DiskFileItemFactory factory = ProfileServlet.newDiskFileItemFactory(getServletContext(), new File("c:\\usr\\temp"));


				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(Long.parseLong("2147483648"));
				// Parse the request
				List /* FileItem */ items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					if (item.isFormField()) {
						processFormField(item);
					} else {
						processUploadedFile(item);
					}
				}

				response.getOutputStream().print(0);
			}
		}catch(Exception e){
			response.getOutputStream().print(1+":"+e.getMessage());
			e.printStackTrace();
		}
	}

	private void processUploadedFile(FileItem item) throws Exception {
		ProjectDebug.write(SOURCE.LOCAL, " - Processing "+item.getName());
		ProjectDebug.write(SOURCE.LOCAL, " -  file is of type: "+item.getContentType());
		if(item.getName() == null || item.getName().length() == 0 || item.getSize() == 0){
			throw new Exception("No file was uploaded");
		}
		String old = null;
		try{old = this.m_data.getUserData().getPictureUrl();}catch(Exception e){}
		File uploadedFile = new File(this.getFileName(item));
		item.write(uploadedFile);
		this.m_data.getUserData().setPictureUrl(uploadedFile.getName());
		ProjectDebug.write(SOURCE.LOCAL, " - saved profile image as "+uploadedFile.getName());
		try{
			if(old != null){
				File old_file = new File(ProfileServlet.getParameter("image.folder")+old);
				old_file.delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String getFileName(FileItem item) throws Exception {
		String root = ProfileServlet.getParameter("image.folder");
		String suffix = "";
		if("image/gif".equals(item.getContentType())){
			suffix = "gif";
		}else if("image/jpeg".equals(item.getContentType())){
			suffix = "jpg";
		}else if("image/png".equalsIgnoreCase(item.getContentType())){
			suffix = "png";
		}else if("image/tiff".equalsIgnoreCase(item.getContentType())){
			suffix = "tif";
		}else if("image/x-ms-bm".equalsIgnoreCase(item.getContentType()) ||
				"image/x-bmp".equalsIgnoreCase(item.getContentType())){
			suffix = "bmp";
		}
		return root+"img_"+new java.util.Date().getTime()+"."+suffix;
	}

	private void processFormField(FileItem item) {
		ProjectDebug.write(SOURCE.LOCAL, " - Processing "+item.getName());
	}
	public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context,
			File repository) {
		FileCleaningTracker fileCleaningTracker
		= FileCleanerCleanup.getFileCleaningTracker(context);
		DiskFileItemFactory factory
		= new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
				repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		return factory;
	}
}
