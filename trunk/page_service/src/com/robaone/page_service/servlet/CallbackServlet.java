package com.robaone.page_service.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.data.SessionData;

/**
 * Servlet implementation class CallbackServlet
 */
public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CallbackServlet() {
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
			HttpServletResponse response) throws ServletException, IOException {
		AppDatabase.writeLog("00050: CallbackServlet.processRequest(...)");
		AppDatabase.writeLog("00051: "+request.getRequestURL().toString()+"?"+request.getQueryString());
		SessionData data = (SessionData)request.getSession().getAttribute("appsessiondata");
		if(data != null){
			data.setAccessToken(request.getParameter("oauth_token"));
			String destination = (String)request.getSession().getAttribute("destination");
			request.getSession().removeAttribute("destination");
			if(destination == null || destination.length() == 0){
				destination = "?";
			}
			response.sendRedirect(destination);
		}else{
			response.sendRedirect("?page=error");
		}
	}

}
