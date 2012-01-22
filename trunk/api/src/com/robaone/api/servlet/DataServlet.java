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
import com.robaone.api.business.FieldValidator;
import com.robaone.api.data.AppDatabase;
import com.robaone.api.data.Error;
import com.robaone.api.data.SessionData;
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
		AppDatabase.writeLog("00030: Start Server Side Code:");
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
				type = request.getParameter("type");
				if(!FieldValidator.exists(type)){
					type = "json";
				}
			}catch(Exception e){}
			response.setContentType(content_type);
			if(type.equalsIgnoreCase("xml")){
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				dsp.runAction(request.getParameter("action"), request.getParameterMap(),bout);
				String xml = XML.toString(new JSONObject(bout.toString()));
				xml = "<?xml version=\"1.0\" ?>\n"+xml;
				response.getOutputStream().print(xml);
			}else{
				dsp.runAction(request.getParameter("action"), request.getParameterMap(),response.getOutputStream());
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
				try {
					pw.print(jo.toString(3));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
			pw.flush();
			pw.close();
		}
	}

}
