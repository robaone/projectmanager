package com.robaone.gwt.framework.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		String path = request.getRequestURI();
		String params = this.buildPath(request.getParameterMap());
		URL url = new URL("http://localhost:8080/sohvac/DataServlet" + ((params != null && params.length() > 0) ? ("?"+params) : ""));
		System.out.println(url);
		URLConnection con = url.openConnection();
		response.setContentLength(con.getContentLength());
		response.setContentType(con.getContentType());
		byte[] buffer = new byte[1024];
		OutputStream out = response.getOutputStream();
		InputStream in = con.getInputStream();
		for(int i = in.read(buffer); i > -1 ; i = in.read(buffer)){
			out.write(buffer,0,i);
		}
	}

	@SuppressWarnings("rawtypes")
	private String buildPath(Map parameterMap) throws UnsupportedEncodingException {
		String str = "";
		Iterator i = parameterMap.keySet().iterator();
		while(i.hasNext()){
			str += (str.length() > 0 ? "&" :"");
			String key = (String) i.next();
			String[] values = (String[]) parameterMap.get(key);
			for(int j = 0; j < values.length;j++){
				str += (j > 0 ? "&" : "") + key + "=" + URLEncoder.encode(values[j],"UTF-8");
			}
		}
		return str;
	}

}
