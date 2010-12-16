package com.robaone.gwt.projectmanager.client.ui.tabs;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TabBar.Tab;
import com.robaone.gwt.projectmanager.client.ui.ProjectTab;
import com.robaone.gwt.projectmanager.client.ui.TasksList.TASK;

public class SectionTabs extends Composite {
	private VerticalPanel vp = new VerticalPanel();
	private FlowPanel tab_flow = new FlowPanel();
	private SimplePanel content_panel = new SimplePanel();
	private HashMap<Widget,Widget> tabs = new HashMap<Widget,Widget>();
	private HashMap<Widget,Widget> contents = new HashMap<Widget,Widget>();
	public SectionTabs() {
		vp.add(tab_flow);
		vp.add(content_panel);
		this.initWidget(vp);
	}
	public void setWidth(String width){
		vp.setWidth(width);
	}
	public int addTab(final Widget content, String string) {
		Label title = new Label(string);
		title.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int index = SectionTabs.this.getTabIndex(content);
				SectionTabs.this.selectTab(index);
			}
			
		});
		return this.addTab(content, title);
	}
	private int addTab(Widget content, Widget title) {
		ProjectTab tab = new ProjectTab();
		tab.load(title);
		this.tab_flow.add(tab);
		tabs.put(title, content);
		contents.put(content, title);
		
		return this.tab_flow.getWidgetCount()-1;
	}
	public void selectTab(int i) {
		Widget w = this.tab_flow.getWidget(i);
		for(int index = 0; index < this.tab_flow.getWidgetCount();index++){
			if(this.tab_flow.getWidget(index) instanceof ProjectTab){
				((ProjectTab)this.tab_flow.getWidget(index)).setSelected(false);
			}
		}
		if(w instanceof ProjectTab){
			ProjectTab tab = (ProjectTab)this.tab_flow.getWidget(i);
			tab.setSelected(true);
			Widget content = tabs.get(tab.getContent().getWidget());
			this.getContentPanel().clear();
			this.getContentPanel().add(content);
		}
	}
	public SimplePanel getContentPanel() {
		return content_panel;
	}
	public void removeTab(int i) {
		ProjectTab tab = (ProjectTab)this.tab_flow.getWidget(i);
		Widget content = tabs.get(tab.getContent().getWidget());
		tabs.remove(tab.getContent().getWidget());
		contents.remove(content);
		this.tab_flow.remove(i);
		try{this.selectTab(i-1);}catch(Exception e){}
	}
	public int getTabCount(){
		return this.tab_flow.getWidgetCount();
	}
	public int getTabIndex(Widget w){
		Widget tab = contents.get(w);
		for(int i = 0; i < this.tab_flow.getWidgetCount();i++){
			Widget widget = ((ProjectTab)this.tab_flow.getWidget(i)).getContent().getWidget();
			if(widget.equals(tab)){
				return i;
			}
		}
		return -1;
	}
	public Widget getTab(int i) {
		Widget w = this.tab_flow.getWidget(i);
		if(w instanceof ProjectTab){
			ProjectTab tab = (ProjectTab)w;
			return this.tabs.get(tab.getContent().getWidget());
		}else{
			return null;
		}
	}
	public int addTab(final Widget w, String title, boolean b) {
		if(b){
			ProjectTab tab = new ProjectTab();
			HorizontalPanel hp = new HorizontalPanel();
			Label l = new Label(title);
			l.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					int index = SectionTabs.this.getTabIndex(w);
					SectionTabs.this.selectTab(index);
				}
				
			});
			Label x = new Label("x");
			x.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					int index = SectionTabs.this.getTabIndex(w);
					SectionTabs.this.removeTab(index);
				}
				
			});
			hp.add(l);
			hp.add(x);
			return this.addTab(w, hp);
		}else{
			return this.addTab(w, title);
		}
	}
}
