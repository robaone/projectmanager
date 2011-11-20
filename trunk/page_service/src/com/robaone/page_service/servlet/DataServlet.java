package com.robaone.page_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.robaone.page_service.business.ActionDispatcher;
import com.robaone.page_service.business.AuthorizationException;
import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.data.SessionData;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;

/**
 * Servlet implementation class DataServlet
 */
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataServlet() {
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
		AppDatabase.writeLog("00053: Start Server Side Code:");
		SessionData sdata = (SessionData)request.getSession().getAttribute("appsessiondata");
		if(sdata == null){
			sdata = new SessionData();
			request.getSession().setAttribute("appsessiondata", sdata);
		}
		sdata.setRemoteHost(request.getRemoteHost());
		ActionDispatcher dsp = new ActionDispatcher(sdata,request);
		try{
			response.setContentType("text/html");
			dsp.runAction(request.getParameter("action"), request.getParameterMap(),response.getOutputStream());
		}catch(AuthorizationException e){
			response.sendRedirect(sdata.getAuthenticateUrl());
		}catch(Exception e){
			DSResponse<Error> dsr = new DSResponse<Error>();
			dsr.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			dsr.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
			PrintWriter pw = new PrintWriter(response.getOutputStream());
			JSONObject jo = new JSONObject(dsr);
			pw.print(jo.toString());
			pw.flush();
			pw.close();
		}finally{
			try{response.getOutputStream().close();}catch(Exception e){}
		}
	}

}
