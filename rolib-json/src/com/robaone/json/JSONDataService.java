package com.robaone.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.robaone.json.DSException;
import com.robaone.json.DSResponse;
import com.robaone.json.JSONResponse;

/**
 * Servlet implementation class DataService
 */
abstract public class JSONDataService extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7454205666457317595L;
	public static int STATUS_FAILURE = -1;
    public static int STATUS_LOGIN_INCORRECT = -5;
    public static int STATUS_LOGIN_REQUIRED = -7;
    public static int STATUS_LOGIN_SUCCESS = -8;
    public static int STATUS_MAX_LOGIN_ATTEMPTS_EXCEEDED = -6;
    public static int STATUS_SERVER_TIMEOUT = -100;
    public static int STATUS_SUCCESS = 0;
    public static int STATUS_TRANSPORT_ERROR = -90;
    public static int STATUS_VALIDATION_ERROR = -4;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONDataService() {
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
			HttpServletResponse response) throws ServletException {
		String callback = request.getParameter("callback");
		try{
			String method = request.getParameter("method");
			if(method == null){
				throw new Exception("invalid request");
			}
			this.log("method = "+method);
			String retval = null;
			JSONObject o = new JSONObject();
			if(method.equalsIgnoreCase("fetch")){
				o = this.processFetchMethod(request,response);
			}else if(method.equalsIgnoreCase("put") ||
					method.equalsIgnoreCase("update")){
				o = this.processPutMethod(request,response);
			}else if(method.equalsIgnoreCase("delete")){
				o = this.processDeleteMethod(request,response);
			}else if(method.equalsIgnoreCase("get")){
				o = this.processGetMethod(request,response);
			}else{
				throw new Exception("invalid request");
			}
			retval = o.toString();
			if(callback != null){
				retval = "callback"+callback+"("+o.toString()+");";
			}
			response.setContentType("text/plain");
			response.getOutputStream().print(retval);
			this.log("response = "+retval);
		}catch(DSException e){
			try{
				JSONObject o = new JSONObject(e.getResponse());
				String retval = o.toString();
				if(callback != null){
					retval = "callback"+callback+"("+o.toString()+");";
				}
				response.setContentType("text/plain");
				response.getOutputStream().print(retval);
				this.log("response = "+retval);
			}catch(Exception e1){
				throw new ServletException(e1);
			}
		}catch(Exception e){
			try{
				JSONResponse r = new JSONResponse();
				r.setStatus(JSONDataService.STATUS_FAILURE);
				r.addError("general",e.getMessage());
				JSONObject o = new JSONObject(new DSResponse(r));
				String retval = o.toString();
				if(callback != null){
					retval = "callback"+callback+"("+o.toString()+");";
				}
				response.setContentType("text/plain");
				response.getOutputStream().print(retval);
				this.log("response = "+retval);
			}catch(Exception e1){
				throw new ServletException(e1);
			}
		}
	}

	private JSONObject processGetMethod(HttpServletRequest request,
			HttpServletResponse response) throws DSException,Exception {
		DSResponse retval = new DSResponse();
		processGetMethod(retval,request.getParameter(this.getTableParamName()),request.getParameter(this.getPrimaryKeyParamName()),request,response);
		return new JSONObject(retval);
	}

	protected String getTableParamName() {
		return "table";
	}
	protected String getPrimaryKeyParamName(){
		return "id";
	}

	abstract protected void processGetMethod(DSResponse retval,
			String table,String primarykey,
			HttpServletRequest request, HttpServletResponse response) throws DSException,Exception;

	private JSONObject processDeleteMethod(HttpServletRequest request,
			HttpServletResponse response)throws DSException,Exception {
		DSResponse retval = new DSResponse();
		processDeleteMethod(retval,request.getParameter(this.getTableParamName()),request.getParameter(this.getPrimaryKeyParamName()),request,response);
		return new JSONObject(retval);
	}

	abstract protected void processDeleteMethod(DSResponse retval,
			String table,String primarykey,
			HttpServletRequest request, HttpServletResponse response) throws DSException,Exception;

	private JSONObject processPutMethod(HttpServletRequest request,
			HttpServletResponse response) throws DSException, Exception {
		DSResponse retval = new DSResponse();
		processPutMethod(retval,request.getParameter(this.getTableParamName()),
				request.getParameter(this.getDOParamName()),request,response);
		return new JSONObject(retval);
	}

	abstract protected void processPutMethod(DSResponse retval,
			String parameter, String parameter2, HttpServletRequest request,
			HttpServletResponse response) throws DSException, Exception;

	protected String getDOParamName() {
		return "do";
	}

	private JSONObject processFetchMethod(HttpServletRequest request,
			HttpServletResponse response) throws DSException, Exception {
		DSResponse retval = new DSResponse();
		String _startRow,_endRow;
		_startRow = request.getParameter(this.getStartRowParamName());
		_endRow = request.getParameter(this.getEndRowParamName());
		String query = request.getParameter(this.getQueryParamName());
		processFetchMethod(retval,query,_startRow,_endRow,request,response);
		return new JSONObject(retval);
	}

	abstract protected void processFetchMethod(DSResponse retval,
			String query, String startRow, String endRow,
			HttpServletRequest request, HttpServletResponse response) throws DSException,Exception;

	private String getQueryParamName() {
		return "query";
	}

	private String getEndRowParamName() {
		return "_startRow";
	}

	private String getStartRowParamName() {
		return "_endRow";
	}


}
