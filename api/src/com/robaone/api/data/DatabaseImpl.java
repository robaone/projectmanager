package com.robaone.api.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;

import com.robaone.api.business.BaseAction;
import com.robaone.api.business.FieldValidator;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.DBManager;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.dbase.RODataSourceFactory;
import com.robaone.dbase.ROPasswordStoreInterface;

public class DatabaseImpl{
	public static final String INSUFFICIENT_RIGHTS_MSG = "You do not have access rights to perform this action";
	public static final String NOT_IMPLEMENTED_MSG = "Not Yet Implemented";
	public static int allocated_connections = 0;
	static DocumentBuilderFactory factory;
	static DocumentBuilder builder;
	static XPathFactory xfactory;
	XPath xpath;
	public DatabaseImpl(){
		try{
			if(factory == null){
				factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true); // never forget this!
			}
			if(builder == null){
				builder = factory.newDocumentBuilder();
			}
			if(xfactory == null){
				xfactory = XPathFactory.newInstance();
			}
			xpath = xfactory.newXPath();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getConnectionCount(){
		return allocated_connections;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public java.sql.Connection getConnection() throws Exception {
		String context_file = AppDatabase.getProperty(AppDatabase.CONTEXT);
		FileInputStream fin = null;
		try{
			fin = new FileInputStream(new File(context_file));
			String env = AppDatabase.getProperty("env");
			String source = "jdbc/mydatabase";
			if(env != null && env.equals("dev")){
				source = "jdbc/mydatabase_dev";
			}
			Document doc = builder.parse(fin);
			String driver_path = "//Resource[@name='"+source+"']/@driverClassName";
			String url_path = "//Resource[@name='"+source+"']/@url";
			String username_path = "//Resource[@name='"+source+"']/@username";
			String password_path = "//Resource[@name='"+source+"']/@password";
			String factory_path = "//Resource[@name='"+source+"']/@factory";
			String driver = (String) xpath.compile(driver_path).evaluate(doc,  XPathConstants.STRING);
			String url = (String) xpath.compile(url_path).evaluate(doc, XPathConstants.STRING);
			String username = (String) xpath.compile(username_path).evaluate(doc, XPathConstants.STRING);
			String password = (String) xpath.compile(password_path).evaluate(doc, XPathConstants.STRING);
			String factory = (String) xpath.compile(factory_path).evaluate(doc,XPathConstants.STRING);
			if(FieldValidator.exists(factory)){
				Class myClass = Class.forName(factory);
				Class[] constrpartypes = new Class[]{};
				Constructor constr = myClass.getConstructor(constrpartypes);
				Object o = constr.newInstance(new Object[]{});
				if(o instanceof RODataSourceFactory){
					password = ((RODataSourceFactory)o).getPasswordStore().getPassword(password);
				}
			}
			Connection con = DBManager.getConnection(driver, url, username, password);
			allocated_connections++;
			return con;
		}catch(Exception e){
			throw e;
		}finally{
			try{fin.close();}catch(Exception e){}
		}
		/*
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		String env = AppDatabase.getProperty("env");
		DataSource ds;
		if(env != null && env.equals("dev")){
			ds = (DataSource)envContext.lookup("jdbc/mydatabase_dev");
		}else{
			ds = (DataSource)envContext.lookup("jdbc/mydatabase");
		}
		AppDatabase.writeLog(ds.toString());
		if(ds == null || ds.getConnection() == null){
			throw new Exception("Unable to establish database connection");
		}
		Connection conn = ds.getConnection();
		AppDatabase.writeLog("dbconnection allocated");
		allocated_connections++;
		AppDatabase.writeLog("allocated connections("+allocated_connections+")");
		return conn;
		 */
	}

	public void writeLog(String string) {
		AppDatabase.writeLog(string);
	}

	public BigDecimal getNextID(final String string) throws Exception {
		final Vector<BigDecimal> retval = new Vector<BigDecimal>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				String str = "update next_index set next_index = next_index + 1 where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, string);
				int updated = this.getPreparedStatement().executeUpdate();
				this.getPreparedStatement().close();
				if(updated == 0){
					str = "insert into next_index (name,next_index) values (?,0)";
					this.setPreparedStatement(this.getConnection().prepareStatement(str));
					this.getPreparedStatement().setString(1, string);
					this.getPreparedStatement().executeUpdate();
					this.getPreparedStatement().close();
				}
				str = "select next_index from next_index where name = ?";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setString(1, string);
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					retval.add(this.getResultSet().getBigDecimal(1));
				}
			}

		};
		ConfigManager.runConnectionBlock(block, this.getConnectionManager());
		return retval.size() > 0? retval.get(0):null;
	}

	public HDBConnectionManager getConnectionManager() {
		return new HDBConnectionManager(){

			@Override
			public Connection getConnection() throws Exception {
				return DatabaseImpl.this.getConnection();
			}

			@Override
			public void closeConnection(Connection m_con) throws Exception {
				m_con.close();
				allocated_connections--;
				AppDatabase.writeLog("allocated connections("+allocated_connections+")");
			}


		};
	}

}
