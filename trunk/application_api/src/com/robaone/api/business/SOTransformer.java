package com.robaone.api.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;


/**
 * @author arobateau
 *
 */
public class SOTransformer {
	private TransformerFactory tFactory;
	private Transformer transformer;
	public SOTransformer(){
		this.tFactory = TransformerFactory.newInstance();
		try {
			this.transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			this.transformer = null;
		}
	}
	public SOTransformer(String xsl_document) throws Exception {
		StreamSource stream = this.getStreamSource(xsl_document);
		this.tFactory = TransformerFactory.newInstance();
		try {
			this.transformer = this.tFactory.newTemplates(stream).newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @param xsl_document
	 * @return
	 */
	public StreamSource getStreamSource(String xsl_document) throws Exception {
		try{
			DataOutputStream os;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			os = new DataOutputStream(bout);
			os.writeUTF(xsl_document);
			byte[] buffer = bout.toByteArray();
			ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
			int i = 2;
			
			bout2.write(buffer,i,buffer.length - i);
			StreamSource stream = new StreamSource(new ByteArrayInputStream(bout2.toByteArray()));
			return stream;
		}catch(Exception e){
			try{
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				xsl_document = this.recodeCharset(xsl_document);
				bout.write(xsl_document.getBytes());
				StreamSource stream = new StreamSource(new ByteArrayInputStream(bout.toByteArray()));
				return stream;
			}catch(Exception e2){
				e.printStackTrace();
				throw e;
			}
		}

	}
	public String recodeCharset(String input) throws Exception {
		String retval = "";
		int startindex = 0;
		int endindex = 0;
		boolean breakfound = false;
		for(int i = 0; i < input.length();i++){
			char c = input.charAt(i);
			endindex = i;
			if((int)c >= 130){
				breakfound = true;
				
				retval += input.substring(startindex,endindex)+"&#"+(int)c+";";
				startindex = i+1;
			}
		}
		if(breakfound == true){
			retval += input.substring(startindex,endindex+1);
			return retval;
		}else{
			return input;
		}
		
	}
	public SOTransformer(Reader stream){
		this.tFactory = TransformerFactory.newInstance();
		try {
			this.transformer = tFactory.newTransformer(new StreamSource(stream));
		} catch (TransformerConfigurationException e) {
			this.transformer = null;
		}
	}
	public SOTransformer(InputStream stream){
		this.tFactory = TransformerFactory.newInstance();
		try {
			this.transformer = tFactory.newTransformer(new StreamSource(stream));
		} catch (TransformerConfigurationException e) {
			this.transformer = null;
		}
	}
	public void setXSLDocument(String xsl_document) throws TransformerConfigurationException,Exception {
		this.transformer = null;
		this.transformer = tFactory.newTransformer(this.getStreamSource(xsl_document));
	}
	public void setXSLDocument(InputStream xsl_document) throws Exception {
		this.transformer = null;
		this.transformer = tFactory.newTransformer(new StreamSource(xsl_document));
	}
	public void setXSLDocument(Reader xsl_document) throws Exception {
		this.transformer = null;
		this.transformer = tFactory.newTransformer(new StreamSource(xsl_document));
	}
	public String transform(String xsl_document,String xml_document) throws Exception {
		this.transformer = null;
		this.transformer = tFactory.newTransformer(this.getStreamSource(xsl_document));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.transformer.transform(this.getStreamSource(xml_document),new StreamResult(out));
		String str = new String(out.toByteArray());
		return str;
	}
	public void transformToStream(String xml_document,OutputStream out) throws Exception {
		this.transformer.transform(this.getStreamSource(xml_document),new StreamResult(out));
	}
	public void transformToStream(InputStream xml_document,OutputStream out) throws Exception {
		this.transformer.transform(new StreamSource(xml_document),new StreamResult(out));
	}
	public String transform(String xml_document) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.transformer.transform(this.getStreamSource(xml_document),new StreamResult(out));
		return out.toString();
	}
}
