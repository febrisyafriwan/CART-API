package com.grokonez.jwtauthentication.message.response;


public class CommonResponse<T> {

	private int status;
	private String message;
	private T data;

	public CommonResponse() {
		this.status = 200;
		this.message = "success";
	}

	public CommonResponse(int status, String message) {
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}