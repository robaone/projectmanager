package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PasswordResetResponse implements IsSerializable {

	private String message;
	private boolean passwordsent;

	public boolean passwordSent() {
		return this.passwordsent;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String string) {
		this.message = string;
	}

	public void setPasswordSent(boolean b){
		this.passwordsent = b;
	}
}
