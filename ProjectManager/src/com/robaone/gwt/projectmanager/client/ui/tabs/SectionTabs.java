package com.robaone.gwt.projectmanager.client.ui.tabs;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
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
import com.robaone.gwt.projectmanager.client.ProjectManager;
import com.robaone.gwt.projectmanager.client.ui.MainContent;
import com.robaone.gwt.projectmanager.client.ui.project.NewProjectUI2;
import com.robaone.gwt.projectmanager.client.ui.project.ProjectTab;
import com.robaone.gwt.projectmanager.client.ui.tasks.TasksList.TASK;

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
	public int addTab(Widget content,String string){
		return this.addTab(content, string,null);
	}
	public int addTab(final Widget content,Label title,String token){
		final String tab_history_token = token;
		title.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int index = SectionTabs.this.getTabIndex(content);
				SectionTabs.this.selectTab(index);
				if(tab_history_token != null){
					History.newItem(tab_history_token, false);
				}
			}
		});
		return this.addTab(content, title);

	}
	public int addTab(final Widget content, String string,String token) {
		Label title = new Label(string);
		return this.addTab(content,title,token);
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
	public class Closable extends Composite {
		private Widget m_tab_widget;
		private Widget m_content_widget;
		public Closable(final Widget w, Widget l, SectionTabs sectionTabs,final String token){
			this.m_tab_widget = l;
			this.m_content_widget = w;
			Label x = new Label("x");
			x.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					int index = SectionTabs.this.getTabIndex(w);
					try{
						SectionTabs.this.removeTab(index);
						if(token != null){
							History.newItem(token, false);
						}
					}catch(Exception e){

					}
				}

			});
			HorizontalPanel hp = new HorizontalPanel();
			hp.add(l);
			hp.add(x);
			this.initWidget(hp);
		}
		public Widget getTabWidget(){
			return this.m_tab_widget;
		}
		public Widget getContentWidget(){
			return this.m_content_widget;
		}
	}
	class TabLabel extends Label{
		Widget m_widget;
		public TabLabel(String text){
			super(text);
		}
		public void setWidget(Widget w){
			this.m_widget = w;
		}
		public Widget getWidget(){
			MainContent main = (MainContent)ProjectManager.getSection(ProjectManager.MAIN_CONTENT);
			for(int i = 0; i < main.getDecoratedTabPanel().getTabCount();i++){
				Widget tab = main.getDecoratedTabPanel().getTabWidget(i);
				if(tab.equals(this)){
					this.m_widget = main.getDecoratedTabPanel().getTab(i);
					break;
				}
			}
			return this.m_widget;
		}
	}
	public int addTab(Widget w, String title, boolean b,final String token) {
		if(b){
			TabLabel l = new TabLabel(title);
			l.setWidget(w);
			Closable c = new Closable(w,l,this,token);
			l.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					TabLabel l = (TabLabel) event.getSource();
					
					Widget w = l.getWidget();
					int index = SectionTabs.this.getTabIndex(w);
					SectionTabs.this.selectTab(index);
					if(token != null){
						History.newItem(token, false);
					}
				}

			});
			return this.addTab(w, c);
		}else{
			return this.addTab(w, title,token);
		}
	}
	public Widget getTabWidget(int tabIndex) {
		Widget w = this.tab_flow.getWidget(tabIndex);
		if(w instanceof ProjectTab){
			ProjectTab tab = (ProjectTab)w;
			Widget c = tab.getContent();
			if(c instanceof SimplePanel){
				Widget cw = ((SimplePanel)c).getWidget();
				if(cw instanceof Closable){
					Widget retval = ((Closable)cw).getTabWidget();
					return retval;
				}else{
					Widget retval = cw;
					return retval;
				}
			}else
				return c;
		}
		return null;
	}
	public void setWidget(int index, Widget item) {
		ProjectTab t = (ProjectTab)this.tab_flow.getWidget(index);
		Widget old = tabs.get(t.getContent().getWidget());
		tabs.put(t.getContent().getWidget(), item);
		contents.remove(old);
		contents.put(item, t.getContent().getWidget());
	}
}
