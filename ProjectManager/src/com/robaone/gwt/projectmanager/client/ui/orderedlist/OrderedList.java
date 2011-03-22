package com.robaone.gwt.projectmanager.client.ui.orderedlist;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrderedList extends ComplexPanel { 
    public OrderedList() { 
            setElement(DOM.createElement("UL")); 
    } 
    public void add(Widget w) { 
            super.add(w, getElement()); 
    } 
    public void insert(Widget w, int beforeIndex) { 
            super.insert(w, getElement(), beforeIndex, true); 
    } 
} 