package com.robaone.api.business;

import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;

import com.robaone.api.data.SessionData;
import com.robaone.dbase.hierarchial.ConnectionBlock;

public abstract class BaseRecordHandler<T> extends BaseAction<T> {

	public BaseRecordHandler(OutputStream o, SessionData d,
			HttpServletRequest request) throws ParserConfigurationException {
		super(o, d, request);
	}
	abstract public void setData(ConnectionBlock conb) throws SQLException;
	abstract public void initialize(ConnectionBlock conb) throws SQLException;
	abstract public T initGet(final String id,ConnectionBlock conb);
	abstract public T save(final JSONObject jo,
			final String id,ConnectionBlock conb) throws Exception;
	abstract public T saveNew(final JSONObject jo, ConnectionBlock conb)
			throws Exception;
	abstract public void deleteRecord(final String id,ConnectionBlock conb)
			throws Exception;
}
