package com.grokonez.jwtauthentication.message.response;

public class BadResourceException extends Exception {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;

	public BadResourceException(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getstatus() {
		return status;
	}

	public void setstatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
