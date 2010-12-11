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
import com.robaone.gwt.projectmanager.client.ui.TasksList.TASK;

public class SectionTabs extends Composite {
	private DecoratedTabBar m_tabs;
	private SimplePanel m_content;
	private HorizontalPanel m_hp;
	private VerticalPanel m_vp;
	private ScrollPanel m_scroller;
	private Button left = new Button("<");
	private Button right = new Button(">");
	private HashMap<Integer,Widget> m_contents = new HashMap<Integer,Widget>();
	private Integer m_history = 0; 
	public SectionTabs() {
		DecoratedTabBar tabs = new DecoratedTabBar();
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
				setHistory(event.getSelectedItem());
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
	protected void setHistory(Integer selectedItem) {
		this.m_history = selectedItem;
	}
	public void setWidth(String width){
		m_hp.setWidth(width);
		m_vp.setWidth(width);
	}
	public HashMap<Integer,Widget> getTabContents(){
		return this.m_contents;
	}
	public void addTab(final Widget content,Widget title){
		HTML h = new HTML("");
		this.m_tabs.addTab(title);
		this.m_contents.put(new Integer(m_tabs.getTabCount()-1), content);
		this.m_tabs.getTab(m_tabs.getTabCount()-1).addClickHandler(new ClickHandler(){

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
	public int addTab(Widget content,String title){
		Label l = new Label(title);
		l.setWidth(title.length()*10+"px");
		this.addTab(content,l);
		return this.getTabs().getTabCount()-1;
	}
	public void selectTab(int index){
		this.m_tabs.selectTab(index);
		this.m_content.clear();
		this.m_content.add(m_contents.get(new Integer(index)));
	}
	public void removeTab(int index){
		this.m_tabs.removeTab(index);
		this.m_content.clear();
		this.m_contents.remove(new Integer(index));
		Integer[] keys = this.m_contents.keySet().toArray(new Integer[0]);
		HashMap<Integer,Widget> new_list = new HashMap<Integer,Widget>();
		for(int i = 0; i < keys.length;i++){
			if(keys[i].intValue() > index){
				new_list.put(new Integer(keys[i].intValue()-1), m_contents.get(keys[i]));
			}else{
				new_list.put(keys[i], m_contents.get(keys[i]));
			}
		}
		this.m_contents = new_list;
		int selected = this.m_history;
		if(m_tabs.getTab(m_history) == null){
			selected = 0;
		}
		this.selectTab(selected);
		int tab_width = this.m_tabs.getElement().getClientWidth();
		if(tab_width > m_scroller.getElement().getClientWidth()){
			showButtons();
		}else{
			hidebuttons();
		}
	}
	public TabBar getTabs(){
		return this.m_tabs;
	}
	public int addTab(final Widget content, final String title, boolean closable) {
		if(closable){
			final HorizontalPanel hp = new HorizontalPanel();
			Label l = new Label(title);
			l.setWidth(title.length()*10+"px");
			hp.add(l);
			Image img = new Image(GWT.getModuleBaseURL()+"close.gif");
			hp.add(img);
			img.getElement().setAttribute("style", "z-index:5");
			this.addTab(content,hp);
			img.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					String html = hp.getElement().getParentElement().getInnerHTML();
					for(int i = 0; i < m_tabs.getTabCount();i++){
						String tabhtml = m_tabs.getTabHTML(i);
						if(tabhtml.contains(html)){
							removeTab(i);
							return;
						}
					}
				}

			});
			return this.getTabs().getTabCount()-1;
		}else{
			return this.addTab(content, title);
		}
	}
}
