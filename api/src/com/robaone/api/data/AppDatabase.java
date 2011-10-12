package com.robaone.api.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.robaone.api.business.ROTransformer;
import com.robaone.api.business.StringEncrypter;
import com.robaone.api.data.blocks.FindCredentialsBlock;
import com.robaone.api.data.blocks.UserRolesBlock;
import com.robaone.api.data.jdo.Credentials_jdo;
import com.robaone.api.data.jdo.Roles_jdo;
import com.robaone.api.data.jdo.Roles_jdoManager;
import com.robaone.api.data.jdo.User_jdo;
import com.robaone.api.data.jdo.User_jdoManager;
import com.robaone.dbase.hierarchial.ConfigManager;
import com.robaone.dbase.hierarchial.ConnectionBlock;
import com.robaone.util.INIFileReader;

public class AppDatabase {
	public static final int ROLE_ROOT = 0;
	public static final int ROLE_ADMINISTRATOR = 1;
	public static final int ROLE_CUSTOMERSERVICE = 2;
	public static final int ROLE_SERVICEPROVIDER = 3;
	public static final int ROLE_VENDOR = 4;
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
	public static void addUserRole(final Integer iduser,final int role) throws Exception {
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				Roles_jdoManager man = new Roles_jdoManager(this.getConnection());
				Roles_jdo role_record = man.newRoles();
				role_record.setIduser(iduser);
				role_record.setRole(role);
				man.save(role_record);
			}
			
		};
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
	}
	public static void removeUserRole(final Integer iduser,final int role) throws Exception {
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				Roles_jdoManager man = new Roles_jdoManager(this.getConnection());
				this.setPreparedStatement(man.prepareStatement(Roles_jdo.ROLE + " = ? and "+Roles_jdo.IDUSER + " = ?"));
				this.getPreparedStatement().setInt(1, role);
				this.getPreparedStatement().setInt(2, iduser.intValue());
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					Roles_jdo record = Roles_jdoManager.bindRoles(this.getResultSet());
					man.delete(record);
				}
			}
			
		};
		ConfigManager.runConnectionBlock(block , new DatabaseImpl().getConnectionManager());
	}
	public static User_jdo getUser(final Integer iduser) throws Exception {
		final Vector<User_jdo> retval = new Vector<User_jdo>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				User_jdoManager man = new User_jdoManager(this.getConnection());
				User_jdo user = man.getUser(iduser);
				retval.add(user);
			}
			
		};
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		return retval.size() > 0 ? retval.get(0) : null;
	}
	public static Roles_jdo[] getUserRoles(Integer iduser) throws Exception {
		Vector<Roles_jdo> retval = new Vector<Roles_jdo>();
		UserRolesBlock block = new UserRolesBlock(retval,iduser);
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		return retval.toArray(new Roles_jdo[0]);
	}
	public static boolean hasRole(final Integer iduser,final int... roles) throws Exception {
		final Vector<Boolean> retval = new Vector<Boolean>();
		ConnectionBlock block = new ConnectionBlock(){

			@Override
			public void run() throws Exception {
				String str = "select * from roles where iduser = ? and role in (";
				for(int i = 0; i < roles.length;i++){
					str += (i > 0 ? "," : "") + "?";
				}
				str += ")";
				this.setPreparedStatement(this.getConnection().prepareStatement(str));
				this.getPreparedStatement().setInt(1, iduser.intValue());
				for(int i = 0; i < roles.length;i++){
					this.getPreparedStatement().setInt(i+2, roles[i]);
				}
				this.setResultSet(this.getPreparedStatement().executeQuery());
				if(this.getResultSet().next()){
					retval.add(new Boolean(true));
				}
			}
			
		};
		ConfigManager.runConnectionBlock(block, new DatabaseImpl().getConnectionManager());
		return retval.size() > 0 ? retval.get(0).booleanValue() : false;
	}
}
