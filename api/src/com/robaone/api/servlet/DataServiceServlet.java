package com.robaone.api.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.robaone.api.business.ActionDispatcher;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.Error;
import com.robaone.api.data.SessionData;
import com.robaone.api.json.DSResponse;
import com.robaone.api.json.JSONResponse;

/**
 * Servlet implementation class DataServiceServlet
 */
public class DataServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServiceServlet() {
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
		AppDatabase.writeLog("00029: Start Server Side Code:");
		SessionData sdata = (SessionData)request.getSession().getAttribute("sessiondata");
		if(sdata == null){
			sdata = new SessionData();
			request.getSession().setAttribute("sessiondata", sdata);
		}
		sdata.setRemoteHost(request.getRemoteHost());
		ActionDispatcher dsp = new ActionDispatcher(sdata,request);
		String content_type = "text/plain";
		String type = "json";
		try{
			try{
				type = request.getParameter("_type");
			}catch(Exception e){}
			response.setContentType(content_type);
			if(type.equalsIgnoreCase("xml")){
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				dsp.runFormAction(request.getParameterMap(),bout);
				String xml = XML.toString(new JSONObject(bout.toString()));
				xml = "<?xml version=\"1.0\" ?>\n"+xml;
				response.getOutputStream().print(xml);
			}else{
				dsp.runFormAction(request.getParameterMap(),response.getOutputStream());
			}
		}catch(Exception e){
			DSResponse<Error> dsr = new DSResponse<Error>();
			dsr.getResponse().setStatus(JSONResponse.GENERAL_ERROR);
			dsr.getResponse().setError(e.getClass().getName()+": "+e.getMessage());
			PrintWriter pw = new PrintWriter(response.getOutputStream());
			JSONObject jo = new JSONObject(dsr);
			if(type.equalsIgnoreCase("xml")){
				try {
					pw.print("<?xml version=\"1.0\" ?>\n"+XML.toString(jo));
				} catch (JSONException e1) {
					throw new ServletException(e1);
				}
			}else{
				pw.print(jo.toString());
			}
			pw.flush();
			pw.close();
		}
		
	}

}
