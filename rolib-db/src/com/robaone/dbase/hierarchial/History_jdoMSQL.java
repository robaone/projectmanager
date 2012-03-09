/*
 * Created on Feb 20, 2011
 *
 */
package com.robaone.dbase.hierarchial;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Vector;

import com.robaone.dbase.ConnectionBlock;
import com.robaone.dbase.HDBConnectionManager;
import com.robaone.jdo.RO_JDO;


public class History_jdoMSQL extends RO_JDO{
	public final static String ID = "ID";
	public final static String OBJECTID = "OBJECTID";
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
	public final static String MODIFIED_BY = "MODIFIED_BY";
	public final static String MODIFIED_DATE = "MODIFIED_DATE";
	public final static String MODIFIER_HOST = "MODIFIER_HOST";
	public final static String DELETED_BY = "DELETED_BY";
	public final static String DELETED_DATE = "DELETED_DATE";
	public final static String DELETION_HOST = "DELETION_HOST";
	protected History_jdoMSQL(){

	}
	public void setId(Integer id){
		this.setField(ID,id);
	}
	public final Integer getId(){
		Object[] val = this.getField(ID);
		if(val != null && val[0] != null){
			return (Integer)val[0];
		}else{
			return null;
		}
	}
	public void setObjectid(Integer integer){
		this.setField(OBJECTID,integer);
	}
	public Integer getObjectid(){
		Object[] val = this.getField(OBJECTID);
		if(val != null && val[0] != null){
			return (Integer)val[0];
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
	public void setParent(Integer integer){
		this.setField(PARENT,integer);
	}
	public Integer getParent(){
		Object[] val = this.getField(PARENT);
		if(val != null && val[0] != null){
			return (Integer)val[0];
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
	public String getDescription(final String table,HDBConnectionManager cman){
		Object[] val = this.getField(DESCRIPTION);
		if(val != null && val[0] != null)
			try {

				if(val[0] instanceof String){
					return (String)val[0];
				}else{
					final Vector retval = new Vector();
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							String sql = "select "+DESCRIPTION+" from "+table+" where ID = ?";
							this.setPreparedStatement(this.getConnection().prepareStatement(sql));
							this.getPreparedStatement().setObject(1, History_jdoMSQL.this.getId());
							this.setResultSet(this.getPreparedStatement().executeQuery());
							if(this.getResultSet().next()){
								String str = this.getResultSet().getString(1);
								retval.add(str);
							}
						}
					};
					ConfigManagerMSQL.runConnectionBlock(block,cman);
					if(retval.size() > 0){
						return (String)retval.get(0);
					}else{
						return null;
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
	public String getText_value(final String table,HDBConnectionManager cman){
		Object[] val = this.getField(TEXT_VALUE);
		if(val != null && val[0] != null)try {

			if(val[0] instanceof String){
				return (String)val[0];
			}else{
				final Vector retval = new Vector();
				ConnectionBlock block = new ConnectionBlock(){

					protected void run() throws Exception {
						String sql = "select "+TEXT_VALUE+" from "+table+" where ID = ?";
						this.setPreparedStatement(this.getConnection().prepareStatement(sql));
						this.getPreparedStatement().setObject(1, History_jdoMSQL.this.getId());
						this.setResultSet(this.getPreparedStatement().executeQuery());
						if(this.getResultSet().next()){
							String str = this.getResultSet().getString(1);
							retval.add(str);
						}
					}
					
				};
				ConfigManagerMSQL.runConnectionBlock(block,cman);
				if(retval.size() > 0){
					return (String)retval.get(0);
				}else{
					return null;
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
	public byte[] getBinary_value(final String table,HDBConnectionManager cman){
		Object[] val = this.getField(BINARY_VALUE);
		if(val != null && val[0] != null){
			try{
				if(val[0] instanceof byte[]){
					return (byte[])val[0];
				}else if(val[0] instanceof java.sql.Blob){
					java.sql.Blob b = (java.sql.Blob)val[0];
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					byte[] buff = new byte[500];
					InputStream in = b.getBinaryStream();
					for(int i = in.read(buff);i > -1;i = in.read(buff)){
						bout.write(buff, 0, i);
					}
					return bout.toByteArray();
				}else{
					final Vector retval = new Vector();
					ConnectionBlock block = new ConnectionBlock(){

						protected void run() throws Exception {
							String sql = "select "+BINARY_VALUE+" from "+table+" where ID = ?";
							this.setPreparedStatement(this.getConnection().prepareStatement(sql));
							this.getPreparedStatement().setObject(1, History_jdoMSQL.this.getId());
							this.setResultSet(this.getPreparedStatement().executeQuery());
							if(this.getResultSet().next()){
								retval.add(this.getResultSet().getBytes(1));
							}
						}
					};
					ConfigManagerMSQL.runConnectionBlock(block, cman);
					if(retval.size() > 0){
						return (byte[])retval.get(0);
					}else{
						return null;
					}
				}
			}catch(Exception e){
				return null;
			}
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
	public void setDeleted_by(String deleted_by){
		this.setField(DELETED_BY,deleted_by);
	}
	public String getDeleted_by(){
		Object[] val = this.getField(DELETED_BY);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public void setDeleted_date(java.sql.Timestamp deleted_date){
		this.setField(DELETED_DATE,deleted_date);
	}
	public java.sql.Timestamp getDeleted_date(){
		Object[] val = this.getField(DELETED_DATE);
		if(val != null && val[0] != null){
			return (java.sql.Timestamp)val[0];
		}else{
			return null;
		}
	}
	public void setDeletion_host(String deletion_host){
		this.setField(DELETION_HOST,deletion_host);
	}
	public String getDeletion_host(){
		Object[] val = this.getField(DELETION_HOST);
		if(val != null && val[0] != null){
			return (String)val[0];
		}else{
			return null;
		}
	}
	public String getIdentityName() {
		return "ID";
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
	public java.sql.Blob getBinaryValueReader(){
		try{
			Object[] val = this.getField(BINARY_VALUE);
			if(val != null && val[0] != null){
				return (java.sql.Blob)val[0];
			}
		}catch(Exception e){
		}
		return null;
	}

}