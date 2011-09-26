package com.robaone.api.business;

import org.apache.xml.serialize.EncodingInfo;

/**
 * @author arobateau
 *
 */
public class ROEncodingInfo extends EncodingInfo {
	private int m_lastPrintable;
	/**
	 * @param ianaName
	 * @param javaName
	 * @param lastPrintable
	 */
	public ROEncodingInfo(String ianaName, String javaName, int lastPrintable) {
		super(ianaName, javaName, lastPrintable);
		this.m_lastPrintable = lastPrintable;
	}
	public boolean isPrintable(char c){
		if((int)c > this.m_lastPrintable || c == '"'){
			return false;
		}else{
			return true;
		}
	}
}
