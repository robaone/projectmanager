package com.robaone.gwt.projectmanager.client;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataServiceResponse<T extends IsSerializable> implements IsSerializable {
	private String error;
	private int status = 0;
	private Vector<T> data;
	private int totalrecords;
	private int startindex;
	private int endindex;
	private HashMap<String,String> fielderrors;
	public DataServiceResponse(){
		data = new Vector<T>();
		fielderrors = new HashMap<String,String>();
	}
	public void setStatus(int sts) {
		this.status = sts;
	}
	public int getStatus(){
		return this.status;
	}
	public T getData(int index){
		return data.get(index);
	}
	public int getDataCount(){
		return data.size();
	}
	public String getError(){
		return this.error;
	}
	public void setError(String error){
		this.error = error;
	}
	public int getTotalRecords(){
		return this.totalrecords;
	}
	public void setTotalRecords(int total){
		this.totalrecords = total;
	}
	public int getStartIndex(){
		return this.startindex;
	}
	public int getEndIndex(){
		return this.endindex;
	}
	public void setStartIndex(int start){
		this.startindex = start;
	}
	public void setEndIndex(int end){
		this.endindex = end;
	}
	public void addData(T item) {
		this.data.add(item);
	}
	public void removeData(T item){
		this.data.remove(item);
	}
	public void insertData(T item,int index){
		this.data.insertElementAt(item, index);
	}
	public void addFieldError(String string, String string2) {
		this.fielderrors.put(string,string2);
	}
	public String[] getFieldErrorNames(){
		return this.fielderrors.keySet().toArray(new String[0]);
	}
	public String getFieldError(String field){
		return this.fielderrors.get(field);
	}
}
