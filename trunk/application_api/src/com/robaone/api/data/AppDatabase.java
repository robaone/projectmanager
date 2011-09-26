package com.robaone.api.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.robaone.api.business.ROTransformer;
import com.robaone.api.business.StringEncrypter;
import com.robaone.api.data.blocks.FindCredentialsBlock;
import com.robaone.api.data.jdo.Credentials_jdo;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.util.INIFileReader;

public class AppDatabase {
	private static final String PAGE_NOT_FOUND_ERROR = "<h1>Page Not Found</h1>";
	private static INIFileReader inifile;
	public static String getProperty(String prop){
		if(inifile == null){
			try {
				inifile = new INIFileReader(System.getProperty("resource.path")+System.getProperty("file.separator")+"config.ini");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			return inifile.getValue(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getPage(String page){
		String page_folder = getProperty("page.folder");
		String retval = null;
		try{
			retval = org.apache.commons.io.FileUtils.readFileToString(new File(page_folder+System.getProperty("file.separator")+page+".html"));
		}catch(Exception e){
			retval = AppDatabase.PAGE_NOT_FOUND_ERROR;
		}
		return retval;
	}
	public static String filterPageParameter(String page){
		if(page == null || page.trim().length() == 0){
			page = "home";
		}
		return page;
	}
	public static void copyStream(FileInputStream fin, OutputStream out) throws IOException {
		byte[] buffer= new byte[1024];
		for(int i = fin.read(buffer);i > -1;i = fin.read(buffer)){
			out.write(buffer,0,i);
		}
	}
	public static String getStylesheet(String string) throws Exception {
		String path = AppDatabase.getProperty("xsl.folder");
		String filename = path + System.getProperty("file.separator")+string+".xsl";
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		FileInputStream fin = null;
		try{
			fin = new FileInputStream(new File(filename));
			copyStream(fin,bout);
			return new String(bout.toByteArray());
		}catch(Exception e){
			throw e;
		}finally{
			fin.close();
		}
	}
	public static String transform(String stylesheet, String xml) throws Exception {
		ROTransformer trn = new ROTransformer(stylesheet);
		return trn.transform(xml);
	}
	public static void writeLog(String string) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		System.out.println(df.format(new java.util.Date())+": "+string);
	}
	public static String generateToken() throws Exception {
		return StringEncrypter.encryptString(new java.util.Date().getTime()+"");
	}
	public static Timestamp getTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	public static Credentials_jdo getCredentials(String string, String token) throws Exception {
		final Vector<Credentials_jdo> retval = new Vector<Credentials_jdo>();
		ConnectionBlock block = new FindCredentialsBlock(retval,string,token);
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		return retval.size() > 0 ? retval.get(0) : null;
	}
}
