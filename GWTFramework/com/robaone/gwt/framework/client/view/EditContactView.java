package com.robaone.gwt.framework.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.robaone.gwt.framework.client.presenter.EditContactPresenter;

public class EditContactView extends Composite implements EditContactPresenter.Display {

	private static EditContactViewUiBinder uiBinder = GWT
			.create(EditContactViewUiBinder.class);

	interface EditContactViewUiBinder extends UiBinder<Widget, EditContactView> {
	}

	public EditContactView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField TextBox name;
	@UiField TextBox phone;
	@Override
	public void load(String name, String phone) {
		this.name.setText(name);
		this.phone.setText(phone);
	}

}
