package com.robaone.api.business;

import java.io.InputStream;

public interface DataScriptor {

	void handleField(String name, String asString) throws Exception;

	void handleStream(String name, InputStream stream, String content_type) throws Exception;

}
