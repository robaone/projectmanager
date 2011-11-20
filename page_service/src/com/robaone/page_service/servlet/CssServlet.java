package com.robaone.page_service.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.AppDatabase;

/**
 * Servlet implementation class CssServlet
 */
public class CssServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CssServlet() {
        super();
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
			HttpServletResponse response) throws IOException {
		String css = request.getParameter("css");
		if(FieldValidator.exists(css)){
			String file = AppDatabase.getProperty("css.folder")+System.getProperty("file.separator")+css;
			File image_file = new File(file);
			if(image_file.canRead() && image_file.isFile()){
				response.setContentType("text/plain");
				FileInputStream fin = new FileInputStream(image_file);
				IOUtils.copy(fin, response.getOutputStream());
				fin.close();
			}
		}
	}

}
