package com.robaone.gwt.framework.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
 public static final Resources INSTANCE = GWT.create(Resources.class);
 
 @Source("question.png")
 public ImageResource question();
 
}
