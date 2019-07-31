package com.datalkz.demo.bean;

public class ResponseBean {

	private String message;
	private Object data;

	public ResponseBean() {

	}
	
	public ResponseBean(String message) {
		this(message, null);
	}

	public ResponseBean(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
