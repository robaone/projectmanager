package com.robaone.page_service.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.AppDatabase;

import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;
import jgravatar.GravatarRating;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String source = request.getParameter("src");
		String img = request.getParameter("img");
		if("gravatar".equals(source)){
			/**
			 * Stream the gravatar image
			 */
			Gravatar g = new Gravatar();
			String size = request.getParameter("size");
			try{
				int sze = Integer.parseInt(size);
				g.setSize(sze);
			}catch(Exception e){

			}
			g.setRating(GravatarRating.GENERAL_AUDIENCES);
			g.setDefaultImage(GravatarDefaultImage.GRAVATAR_ICON);
			String email = request.getParameter("email");
			if(FieldValidator.isEmail(email)){
				response.setContentType("image/jpeg");
				g.download(email, response.getOutputStream());
			}
		}else if(FieldValidator.exists(img)){
			String file = AppDatabase.getProperty("images.folder")+System.getProperty("file.separator")+img;
			File image_file = new File(file);
			if(image_file.canRead() && image_file.isFile()){
				if(file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".jpeg")){
					response.setContentType("image/jpeg");
				}else if(file.toLowerCase().endsWith(".gif")){
					response.setContentType("image/gif");
				}else if(file.toLowerCase().endsWith(".png")){
					response.setContentType("image/png");
				}
				FileInputStream fin = new FileInputStream(image_file);
				IOUtils.copy(fin, response.getOutputStream());
				fin.close();
			}
		}
	}

}
