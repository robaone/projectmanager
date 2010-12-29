package com.robaone.gwt.projectmanager.client.ui;

import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public class ArticleFeedUI extends Composite {
	private FlowPanel parent;
	private Vector<Widget> articles = new Vector<Widget>();
	private Timer timer;
	private int m_index = 0;
	public ArticleFeedUI(){
		this.parent = new FlowPanel();
		this.parent.setStyleName("articlefeed");

		this.initWidget(parent);
		this.initialize();
	}
	private void initialize() {
		Element articles_node = Document.get().getElementById("articles");
		final Vector<Element> articles = new Vector<Element>();
		articles_node.setAttribute("style", "display:none");
		if(articles_node != null){
			NodeList<Element> list = articles_node.getElementsByTagName("div");
			for(int i = 0; i < list.getLength();i++){
				Element article = list.getItem(i);
				if(article.getId().startsWith("article_")){
					articles.add(article);
				}
			}
		}
		try{
			String h = articles.get(m_index).getInnerHTML();
			this.parent.add(new HTML(h));
		}catch(Exception e){}
		ArticleFeedUI.this.timer = new Timer(){

			@Override
			public void run() {
				if(articles.size() > 0){
					m_index = (m_index + 1) % articles.size();
					Element doc = articles.get(m_index);
					String id = doc.getAttribute("id");
					String html = doc.getInnerHTML();
					parent.clear();
					parent.add(new HTML(html));
				}
			}

		};
		ArticleFeedUI.this.timer.scheduleRepeating(10000);
	}
	protected int nextArticle() {
		this.m_index ++;
		if(this.m_index == articles.size()){
			this.m_index = 0;
		}

		return this.m_index;
	}
}
