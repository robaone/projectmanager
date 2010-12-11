package com.robaone.gwt.projectmanager.client.ui.tabs;

import java.util.HashMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SectionTabs extends Composite {
	private DecoratedTabPanel m_tabs;
	private SimplePanel m_content;
	private HorizontalPanel m_hp;
	private VerticalPanel m_vp;
	private ScrollPanel m_scroller;
	private Button left = new Button("<");
	private Button right = new Button(">");
	private HashMap<Integer,Widget> m_contents = new HashMap<Integer,Widget>();
	public SectionTabs() {
		DecoratedTabPanel tabs = new DecoratedTabPanel();
		m_content = new SimplePanel();
		m_content.setStyleName("tab_content");
		m_tabs = tabs;
		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();
		m_hp = hp;
		m_vp = vp;
		final ScrollPanel scroller = new ScrollPanel();
		this.m_scroller = scroller;
		
		hp.setWidth("550px");
		vp.setWidth("550px");
		scroller.add(tabs);
		scroller.setAlwaysShowScrollBars(false);
		scroller.getElement().setAttribute("style", "overflow:hidden");
		hp.add(scroller);
		hp.add(left);
		hp.add(right);
		vp.add(hp);
		vp.add(m_content);
		scroller.setWidth(500+"px");
		this.initWidget(vp);
		
		
		left.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int position = scroller.getHorizontalScrollPosition() - scroller.getElement().getClientWidth();
				if(position < 0) position = 0;
				scroller.setHorizontalScrollPosition(position);
			}
			
		});
		
		right.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int position = scroller.getHorizontalScrollPosition() + scroller.getElement().getClientWidth();
				scroller.setHorizontalScrollPosition(position);
			}
			
		});
		
		tabs.addSelectionHandler(new SelectionHandler<Integer>(){

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Object o = event.getSource();
				if ( o instanceof Widget ){
					Widget w = (Widget)o;
					NodeList node_list = w.getElement().getElementsByTagName("table");
					int position = 0;
					int tab_width = 0;
					for(int i = 0; i < node_list.getLength();i++){
						try{
							String classes = node_list.getItem(i).getFirstChild().getParentElement().getAttribute("class");
							if(classes.contains("selected")){
								Element p = node_list.getItem(i).getFirstChild().getParentElement();
								position = p.getAbsoluteLeft();
								tab_width = p.getClientWidth();
								break;
							}
						}catch(Exception e){}
					}
					
					System.out.print(position + " ");
					position =  position - scroller.getElement().getAbsoluteLeft();
					int width = scroller.getElement().getClientWidth();
					
					System.out.println(position);
					if((position + tab_width) > (width - 10)){
						position = (position + tab_width - (width + 10));
						scroller.setHorizontalScrollPosition(scroller.getHorizontalScrollPosition() + position + 13);
					}else if(position < 11){
						scroller.setHorizontalScrollPosition(position + scroller.getHorizontalScrollPosition() - 11);
					}
					
					
				}
			}
			
		});
	}
	public void setWidth(String width){
		m_hp.setWidth(width);
		m_vp.setWidth(width);
	}
	public void addTab(final Widget content,Widget title){
		HTML h = new HTML("");
		this.m_tabs.add(h,title);
		this.m_contents.put(new Integer(m_tabs.getTabBar().getTabCount()-1), content);
		this.m_tabs.getTabBar().getTab(m_tabs.getTabBar().getTabCount()-1).addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				m_content.clear();
				m_content.add(content);
			}
			
		});
		int tab_width = this.m_tabs.getElement().getClientWidth();
		if(tab_width > m_scroller.getElement().getClientWidth()){
			showButtons();
		}else{
			hidebuttons();
		}
	}
	private void hidebuttons() {
		left.setVisible(false);
		right.setVisible(false);
	}
	private void showButtons() {
		left.setVisible(true);
		right.setVisible(true);
	}
	public void addTab(Widget content,String title){
		Label l = new Label(title);
		l.setWidth(title.length()*10+"px");
		this.addTab(content,l);
	}
	public void selectTab(int index){
		this.m_tabs.selectTab(index);
		this.m_content.clear();
		this.m_content.add(m_contents.get(new Integer(index)));
	}
	public void removeTab(int index){
		this.m_tabs.remove(index);
		this.m_content.clear();
		this.m_contents.remove(new Integer(index));
		int selected = this.m_tabs.getTabBar().getSelectedTab();
		this.selectTab(selected);
		int tab_width = this.m_tabs.getElement().getClientWidth();
		if(tab_width > m_scroller.getElement().getClientWidth()){
			showButtons();
		}else{
			hidebuttons();
		}
	}
}
