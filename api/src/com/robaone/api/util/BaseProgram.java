package com.robaone.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.robaone.dbase.HDBConnectionManager;

abstract public class BaseProgram {
	private FileOutputStream fout;
	private PrintWriter out;
	private Properties props;
	private Connection m_connection;
	public void run_main(String[] args){
		try{
			this.processArguments(args);
			this.execute();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}finally{
			try {
				this.writeLog("00036: -- Done --");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try{
				out.close();
				fout.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void writeLog(String string) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String message = df.format(new java.util.Date())+": "+string;
		System.out.println(message);
		this.out.println(message);
		this.out.flush();
	}

	private void processArguments(String[] args) throws Exception {
		Properties p = new Properties();
		FileInputStream config = null;
		try{
			config = new FileInputStream(new File(args[0]));
			p.load(config);
			this.props = p;
		}finally{
			config.close();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.kk.mm");
		fout = new FileOutputStream(new File(this.getProperty("log.folder")+System.getProperty("file.separator")+this.getClass().getName()+df.format(new java.util.Date())+".log"));
		out = new PrintWriter(fout);
	}
	public String getProperty(String prop) throws Exception {
		return this.props.getProperty(prop);
	}
	abstract public void execute() throws Exception;
	public HDBConnectionManager getConnectionManager(){
		return new HDBConnectionManager(){

			@Override
			public Connection getConnection() throws Exception {
				if(m_connection == null || m_connection.isClosed()){
					String driver = getProperty("db.driver");
					String url = getProperty("db.url");
					String username = getProperty("db.username");
					String password = getProperty("db.password");
					m_connection = com.robaone.dbase.DBManager.getConnection(driver, url, username, password);
				}
				return m_connection;
			}

			@Override
			public void closeConnection(Connection m_con) throws Exception {
				m_con.close();
			}

		};
	}
}
