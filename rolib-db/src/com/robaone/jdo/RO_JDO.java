/*
 * Created on Apr 9, 2010
 *
 */
package com.robaone.jdo;

import java.util.HashMap;

/**
 * @author arobateau  http://www.robaone.com
 * 
 */
abstract public class RO_JDO {
	private HashMap<String,Object[]> m_fields = new HashMap<String,Object[]>();
	public Object[] getField(String name){
		return (Object[])this.m_fields.get(name);
	}
	public void bindField(String name,Object[] val){
		this.m_fields.put(name.toUpperCase(),val);
	}
	protected void setField(String name,Object value){
		Object[] val = this.getField(name.toUpperCase());
		if(val == null){
			val = new Object[2];
		}
		if(val[0] != null){
			if(value == null || !val[0].equals(value)){
				val[1] = new Boolean(true);
			}
		}else if(val[0] == null && value != null){
			val[1] = new Boolean(true);
		}
		val[0] = value;
		this.m_fields.put(name.toUpperCase(),val);
	}
	public abstract String getIdentityName();

	public int getDirtyFieldCount() {
		int count = 0;
		Object[] keys = this.m_fields.keySet().toArray();
		for(int i = 0; i < keys.length;i++){
			Object key = keys[i];
			Object[] val = (Object[])this.m_fields.get(key);
			if(val != null && val[1] != null){
				Boolean dirty = (Boolean)val[1];
				if(dirty.booleanValue() == true){
					count++;
				}
			}
		}
		return count;
	}
	
	public String getDirtyField(int key_index) throws Exception {
		Object[] keys = this.m_fields.keySet().toArray();
		int index = 0;
		for(int i = 0; i < keys.length;i++){
			Object[] val = (Object[])this.m_fields.get(keys[i]);
			if(val != null && val[1] != null){
				Boolean dirty = (Boolean)val[1];
				if(dirty.booleanValue() == true){
					if(key_index == index){
						return keys[i].toString();
					}
					index++;
				}
			}
		}
		throw new Exception("Dirty field at index "+key_index+" not found.");
	}

	/**
	 * @return
	 */
	public int getFieldCount() {
		int count = this.m_fields.size();
		return count;
	}

	/**
	 * @param i
	 * @return
	 */
	public String getField(int i) {
		Object[] keys = this.m_fields.keySet().toArray();
		
		return keys[i].toString();
	}
	
	public void copy(RO_JDO source){
		for(int i = 0; i < source.getFieldCount();i++){
			String field = source.getField(i);
			if(!field.equalsIgnoreCase(source.getIdentityName())){
				this.setField(field, source.getField(field)[0]);
			}
		}
	}
	
	public void encapsulate(RO_JDO source){
		this.m_fields = source.m_fields;
	}
	
	public boolean equals(RO_JDO source){
		return this.m_fields.equals(source.m_fields);
	}
}
