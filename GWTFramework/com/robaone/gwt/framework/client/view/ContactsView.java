package com.robaone.gwt.framework.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.presenter.ContactsPresenter;

public class ContactsView extends Composite  implements ContactsPresenter.Display {

	private static ContactsViewUiBinder uiBinder = GWT
			.create(ContactsViewUiBinder.class);

	interface ContactsViewUiBinder extends UiBinder<Widget, ContactsView> {
	}

	public ContactsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FlexTable table;

	public ContactsView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void load(ArrayList<String> values){
		if(values != null){
			for(int i = 0; i < values.size();i++){
				String displayName = values.get(i);
				table.setWidget(i, 0, new Label(displayName));
			}
			while(values.size() < table.getRowCount()){
				table.removeRow(values.size()+1);
			}
		}
	}

	public HasClickHandlers getList(){
		return table;
	}
	
	public int getClickedRow(ClickEvent event){
		int selectedRow = -1;
		HTMLTable.Cell cell = table.getCellForEvent(event);
		if(cell != null){
			selectedRow = cell.getRowIndex();
		}
		return selectedRow;
	}
}
