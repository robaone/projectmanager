package com.robaone.gwt.projectmanager.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ProjectManagerLayout extends Composite {
	private FlowPanel flwpnl0 = new FlowPanel();
	private FlowPanel search_bar = new FlowPanel();
	private FlowPanel main_content = new FlowPanel();
	private FlowPanel side_bar = new FlowPanel();
	private FlowPanel profile_section = new FlowPanel();
	private FlowPanel tasks_section = new FlowPanel();
	public ProjectManagerLayout(){
		search_bar.setStyleName("search_bar");
		main_content.setStyleName("main_content");
		side_bar.setStyleName("side_bar");
		profile_section.setStyleName("profile_section");
		tasks_section.setStyleName("tasks_section");
		flwpnl0.add(search_bar);
		flwpnl0.add(main_content);
		side_bar.add(profile_section);
		side_bar.add(tasks_section);
		flwpnl0.add(side_bar);
		this.initWidget(flwpnl0);
	}
	public void load() throws Exception {
	}
	public FlowPanel getSearch_bar(){
		return this.search_bar;
	}
	public FlowPanel getMain_content(){
		return this.main_content;
	}
	public FlowPanel getSide_bar(){
		return this.side_bar;
	}
	public FlowPanel getProfile_section(){
		return this.profile_section;
	}
	public FlowPanel getTasks_section(){
		return this.tasks_section;
	}
}