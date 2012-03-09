/*
 * Created on Feb 20, 2011
 *
 */
package com.robaone.dbase.hierarchial;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.jdo.RO_JDO;


public class Config_jdo extends RO_JDO{
	public final static String ID = "ID";
	public final static String NAME = "NAME";
	public final static String PARENT = "PARENT";
	public final static String TYPE = "TYPE";
	public final static String TITLE = "TITLE";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String STRING_VALUE = "STRING_VALUE";
	public final static String NUMBER_VALUE = "NUMBER_VALUE";
	public final static String BOOL_VALUE = "BOOL_VALUE";
	public final static String DATE_VALUE = "DATE_VALUE";
	public final static String TEXT_VALUE = "TEXT_VALUE";
	public final static String BINARY_VALUE = "BINARY_VALUE";
	public final static String CONTENT_TYPE = "CONTENT_TYPE";
	public final static String CREATED_BY = "CREATED_BY";
	public final static String CREATED_DATE = "CREATED_DATE";
	public final static String MODIFIED_BY = "MODIFIED_BY";
	public final static String MODIFIED_DATE = "MODIFIED_DATE";
	public final static String MODIFIER_HOST = "MODIFIER_HOST";
	protected Config_jdo(){

	}
	protected void setId(BigDecimal id){
		this.setField(ID,id);
	}
	public final BigDecimal getId(){
		Object[] val = this.getField(ID);
		if(val != null && val[0] != null){
			return (BigDecimal)val[0];
		}else{
			return null;
		}
	}
	public void setName(String name){
		this.setField(NAME,name);
	}
	public String getName(){
		Object[] val = this.getField(NAME);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setParent(BigDecimal parent){
		this.setField(PARENT,parent);
	}
	public BigDecimal getParent(){
		Object[] val = this.getField(PARENT);
		if(val != null && val[0] != null){
			return (BigDecimal)val[0];
		}else{
			return null;
		}
	}
	public void setType(Integer type){
		this.setField(TYPE,type);
	}
	public Integer getType(){
		Object[] val = this.getField(TYPE);
		if(val != null && val[0] != null){
			if(val[0] instanceof java.lang.Short){
				return new Integer(((java.lang.Short)val[0]).toString());
			}else{
				return (Integer)val[0];
			}
		}else{
			return null;
		}
	}
	public void setTitle(String title){
		this.setField(TITLE,title);
	}
	public String getTitle(){
		Object[] val = this.getField(TITLE);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setDescription(String description){
		this.setField(DESCRIPTION,description);
	}
	public java.sql.Clob getDescriptionReader(){
		try{
			Object[] val = this.getField(DESCRIPTION);
			if(val != null && val[0] != null){
				return (java.sql.Clob)val[0];
			}
		}catch(Exception e){

		}
		return null;
	}
	public java.sql.Clob getTextValueReader(){
		try{
			Object[] val = this.getField(TEXT_VALUE);
			if(val != null && val[0] != null){
				return (java.sql.Clob)val[0];
			}
		}catch(Exception e){

		}
		return null;
	}
	public java.sql.ResultSet getBinaryValueReader(String table,java.sql.Connection con) throws Exception{
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		try{
			String str = "Select "+Config_jdo.BINARY_VALUE+" from "+table+" where "+Config_jdo.ID+" = ?";
			ps = con.prepareStatement(str);
			ps.setBigDecimal(1, this.getId());
			rs = ps.executeQuery();
			rs.next();
			return rs;
		}catch(Exception e){
			throw e;
		}finally{
			
		}
	}
	public String getDescription(String table,HDBConnectionManager cman){
		Object[] val = this.getField(DESCRIPTION);
		if(val != null && val[0] != null)
			try {

				if(val[0] instanceof String){
					return (String)val[0];
				}else{
					ProjectDatabase db = new ProjectDatabase(table,cman);
					java.sql.Connection con = null;
					java.sql.PreparedStatement ps = null;
					java.sql.ResultSet rs = null;
					try{
						con = db.getConnection();
						ps = con.prepareStatement("select "+DESCRIPTION+" from "+table+" where ID = ?");
						ps.setBigDecimal(1, this.getId());
						rs = ps.executeQuery();
						if(rs.next()){
							String retval = rs.getString(1);
							return retval;
						}
					}finally{
						try{rs.close();}catch(Exception e){}
						try{ps.close();}catch(Exception e){}
						try{con.close();}catch(Exception e){}
					}
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;

	}
	public void setString_value(String string_value){
		this.setField(STRING_VALUE,string_value);
	}
	public String getString_value(){
		Object[] val = this.getField(STRING_VALUE);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setNumber_value(BigDecimal number_value){
		this.setField(NUMBER_VALUE,number_value);
	}
	public BigDecimal getNumber_value(){
		Object[] val = this.getField(NUMBER_VALUE);
		if(val != null && val[0] != null){
			return (BigDecimal)val[0];
		}else{
			return null;
		}
	}
	public void setBool_value(Integer bool_value){
		this.setField(BOOL_VALUE,bool_value);
	}
	public Integer getBool_value(){
		Object[] val = this.getField(BOOL_VALUE);
		if(val != null && val[0] != null){
			if(val[0] instanceof java.lang.Short){
				return new Integer(((java.lang.Short)val[0]).toString());
			}else{
				return (Integer)val[0];
			}
		}else{
			return null;
		}
	}
	public void setDate_value(java.sql.Timestamp date_value){
		this.setField(DATE_VALUE,date_value);
	}
	public java.sql.Timestamp getDate_value(){
		Object[] val = this.getField(DATE_VALUE);
		if(val != null && val[0] != null){
			return (java.sql.Timestamp)val[0];
		}else{
			return null;
		}
	}
	public void setText_value(String text_value){
		this.setField(TEXT_VALUE,text_value);
	}
	public String getText_value(String table,HDBConnectionManager cman){
		Object[] val = this.getField(TEXT_VALUE);
		if(val != null && val[0] != null)try {

			if(val[0] instanceof String){
				return (String)val[0];
			}else{
				ProjectDatabase db = new ProjectDatabase(table,cman);
				java.sql.Connection con = null;
				java.sql.PreparedStatement ps = null;
				java.sql.ResultSet rs = null;
				try{
					con = db.getConnection();
					ps = con.prepareStatement("select "+TEXT_VALUE+" from "+table+" where ID = ?");
					ps.setBigDecimal(1, this.getId());
					rs = ps.executeQuery();
					if(rs.next()){
						String retval = rs.getString(1);
						return retval;
					}
				}finally{
					try{rs.close();}catch(Exception e){}
					try{ps.close();}catch(Exception e){}
					try{con.close();}catch(Exception e){}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	public void setBinary_value(byte[] binary_value){
		this.setField(BINARY_VALUE,binary_value);
	}
	public byte[] getBinary_value(String table,HDBConnectionManager cman){
		Object[] val = this.getField(BINARY_VALUE);
		try{
				if(val[0] instanceof byte[]){
					return (byte[])val[0];
				}else if(val[0] instanceof java.sql.Blob){
					java.sql.Connection con = null;
					java.sql.PreparedStatement ps = null;
					java.sql.ResultSet rs = null;
					try{
						ProjectDatabase db = new ProjectDatabase(table,cman);
						con = db.getConnection();
						ps = con.prepareStatement("select "+BINARY_VALUE+" from "+table+" where ID = ?");
						ps.setBigDecimal(1, this.getId());
						rs = ps.executeQuery();
						if(rs.next()){
							java.sql.Blob b = rs.getBlob(1);
							ByteArrayOutputStream bout = new ByteArrayOutputStream();
							byte[] buff = new byte[500];
							InputStream in = b.getBinaryStream();
							for(int i = in.read(buff);i > -1;i = in.read(buff)){
								bout.write(buff, 0, i);
							}
							return bout.toByteArray();
						}
					}catch(Exception e){
						e.printStackTrace();
						throw e;
					}finally{
						try{rs.close();}catch(Exception e){}
						try{ps.close();}catch(Exception e){}
						try{con.close();}catch(Exception e){}
					}
				}else{
					java.sql.Connection con = null;
					java.sql.PreparedStatement ps = null;
					java.sql.ResultSet rs = null;
					try{
						ProjectDatabase db = new ProjectDatabase(table,cman);
						con = db.getConnection();
						ps = con.prepareStatement("select "+BINARY_VALUE+" from "+table+" where ID = ?");
						ps.setBigDecimal(1, this.getId());
						rs = ps.executeQuery();
						if(rs.next()){
							byte[] retval = rs.getBytes(1);
							return retval;
						}
					}catch(Exception e){
						e.printStackTrace();
						throw e;
					}finally{
						try{rs.close();}catch(Exception e){}
						try{ps.close();}catch(Exception e){}
						try{con.close();}catch(Exception e){}
					}
				}
			}catch(Exception e){
				return null;
			}
		return null;

	}
	public void setContent_type(String content_type){
		this.setField(CONTENT_TYPE,content_type);
	}
	public String getContent_type(){
		Object[] val = this.getField(CONTENT_TYPE);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setCreated_by(String created_by){
		this.setField(CREATED_BY,created_by);
	}
	public String getCreated_by(){
		Object[] val = this.getField(CREATED_BY);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setCreated_date(java.sql.Timestamp created_date){
		this.setField(CREATED_DATE,created_date);
	}
	public java.sql.Timestamp getCreated_date(){
		Object[] val = this.getField(CREATED_DATE);
		if(val != null && val[0] != null){
			return (java.sql.Timestamp)val[0];
		}else{
			return null;
		}
	}
	public void setModified_by(String modified_by){
		this.setField(MODIFIED_BY,modified_by);
	}
	public String getModified_by(){
		Object[] val = this.getField(MODIFIED_BY);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setModified_date(java.sql.Timestamp modified_date){
		this.setField(MODIFIED_DATE,modified_date);
	}
	public java.sql.Timestamp getModified_date(){
		Object[] val = this.getField(MODIFIED_DATE);
		if(val != null && val[0] != null){
			return (java.sql.Timestamp)val[0];
		}else{
			return null;
		}
	}
	public void setModifier_host(String modifier_host){
		this.setField(MODIFIER_HOST,modifier_host);
	}
	public String getModifier_host(){
		Object[] val = this.getField(MODIFIER_HOST);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public String getIdentityName() {
		return "ID";
	}
}