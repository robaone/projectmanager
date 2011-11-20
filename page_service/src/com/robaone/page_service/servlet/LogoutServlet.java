package com.robaone.page_service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuthMessage;

import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.data.SessionData;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
			HttpServletResponse response) throws IOException {
		SessionData data = (SessionData)request.getSession().getAttribute("appsessiondata");
		try{
			if(data.isAuthorized()){
				String action = "Login.logout";
				String adata = "{}";
				OAuthMessage oresponse = data.executeAPI(action,adata);
				JSONObject jo = new JSONObject(oresponse.readBodyAsString());
				AppDatabase.writeLog("00075: response = "+jo.toString());
				String xml = XML.toString(jo, "response");
			}
		}catch(Exception e){
			response.sendRedirect("?page=error");
		}
		
		response.sendRedirect("?page=logout");
	}

}
