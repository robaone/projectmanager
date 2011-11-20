package com.robaone.page_service.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.robaone.api.data.AppDatabase;
import com.robaone.page_service.business.ParameterUtils;
import com.robaone.page_service.data.SessionData;

/**
 * Servlet implementation class RPCServlet
 */
public class RPCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RPCServlet() {
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
		InputStream in = request.getInputStream();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		IOUtils.copy(in, bout);
		String datastr = bout.toString();
		AppDatabase.writeLog("00079: Data = "+datastr);
		Map<String,String> parameters = null;
		if(datastr == null || datastr.length() == 0){
			parameters = new HashMap<String,String>();
			@SuppressWarnings("unchecked")
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements()){
				String name = names.nextElement();
				parameters.put(name, request.getParameter(name));
			}
		}else{
			parameters = ParameterUtils.parse(datastr);
		}
		String section = parameters.get("section");
		String page = parameters.get("page");
		SessionData data = (SessionData) request.getSession().getAttribute("appsessiondata");
		if(data == null){
			data = new SessionData();
			request.getSession().setAttribute("appsessiondata", data);
		}
		JSONObject jo = new JSONObject(data);
		try{
			String result = AppDatabase.generatePage(page, section, parameters,jo);
			response.getOutputStream().print(result);
		}catch(Exception e){
			String result = AppDatabase.getPage("error");
			response.getOutputStream().print(result);
		}
	}

}
