package com.robaone.gwt.projectmanager.client.ui.project;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ProjectTab extends Composite {
	private FlexTable table = new FlexTable();
	private SimplePanel content = new SimplePanel();
	private boolean is_selected;
	public ProjectTab(){
		InlineLabel nlnlbl3 = new InlineLabel();
		InlineLabel nlnlbl4 = new InlineLabel();
		InlineLabel nlnlbl5 = new InlineLabel();
		InlineLabel nlnlbl6 = new InlineLabel();
		InlineLabel nlnlbl9 = new InlineLabel();
		table.setStyleName("tab");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		nlnlbl3.setText("");
		nlnlbl4.setText("");
		nlnlbl5.setText("");
		nlnlbl6.setText("");
		nlnlbl9.setText("");
		table.setWidget(0,0,nlnlbl3);
		table.getFlexCellFormatter().setStyleName(0, 0, "tab_tl");
		table.setWidget(0,1,nlnlbl4);
		table.getFlexCellFormatter().setStyleName(0, 1, "tab_tm");
		table.setWidget(0,2,nlnlbl5);
		table.getFlexCellFormatter().setStyleName(0, 2, "tab_tr");
		table.setWidget(1,0,nlnlbl6);
		table.getFlexCellFormatter().setStyleName(1, 0, "tab_l");
		table.setWidget(1,1,content);
		table.getFlexCellFormatter().setStyleName(1, 1, "tab_m");
		table.setWidget(1,2,nlnlbl9);
		table.getFlexCellFormatter().setStyleName(1, 2, "tab_r");
		this.initWidget(table);
	}
	public void load(Widget w) {
		content.clear();
		content.add(w);
	}
	public FlexTable getTable(){
		return this.table;
	}
	public SimplePanel getContent(){
		return this.content;
	}
	public void setSelected(boolean b) {
		this.is_selected = b;
		if(b){
			table.getFlexCellFormatter().addStyleName(0, 0, "tab_tl_selected");
			table.getFlexCellFormatter().addStyleName(0, 1, "tab_tm_selected");
			table.getFlexCellFormatter().addStyleName(0, 2, "tab_tr_selected");
			table.getFlexCellFormatter().addStyleName(1, 0, "tab_l_selected");
			table.getFlexCellFormatter().addStyleName(1, 1, "tab_m_selected");
			table.getFlexCellFormatter().addStyleName(1, 2, "tab_r_selected");
		}else{
			table.getFlexCellFormatter().removeStyleName(0, 0, "tab_tl_selected");
			table.getFlexCellFormatter().removeStyleName(0, 1, "tab_tm_selected");
			table.getFlexCellFormatter().removeStyleName(0, 2, "tab_tr_selected");
			table.getFlexCellFormatter().removeStyleName(1, 0, "tab_l_selected");
			table.getFlexCellFormatter().removeStyleName(1, 1, "tab_m_selected");
			table.getFlexCellFormatter().removeStyleName(1, 2, "tab_r_selected");
		}
	}
	public boolean isSelected() {
		return is_selected;
	}
}