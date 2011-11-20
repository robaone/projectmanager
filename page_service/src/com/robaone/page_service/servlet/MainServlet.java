package com.robaone.page_service.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.business.ApiCall;
import com.robaone.page_service.business.AuthorizationException;
import com.robaone.page_service.data.SessionData;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
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
		printHeaders(request);
		SessionData data = (SessionData)request.getSession().getAttribute("appsessiondata");
		if(data == null){
			data = new SessionData();
			request.getSession().setAttribute("appsessiondata", data);
		}
		String page = request.getParameter("page");
		if(page == null) page = "home";
		if(page.equals("dashboard")){
			try {
				ApiCall.authorize(data);
				request.getRequestDispatcher("root.jsp").forward(request, response);
			} catch (AuthorizationException e) {
				request.getSession().setAttribute("destination",request.getRequestURL()+"?"+request.getQueryString());
				response.sendRedirect(data.getAuthenticateUrl());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}else{
			request.getRequestDispatcher("root.jsp").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private void printHeaders(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		AppDatabase.writeLog("00080: MainServlet.printHeaders(...)");
		while(names.hasMoreElements()){
			String name = names.nextElement();
			AppDatabase.writeLog("00080: "+name+" = "+request.getHeader(name));
		}
		AppDatabase.writeLog("00080: Protocol = "+request.getProtocol());
	}

}
