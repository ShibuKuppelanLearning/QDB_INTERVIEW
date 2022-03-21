package com.bank.dms.core.vo;

import java.io.Serializable;

public class ErrorInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8074122345814084035L;
	
	private String code;
	private String message;

	public ErrorInfo() {
		// TODO Auto-generated constructor stub
	}

	public ErrorInfo(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorInfo [code=" + code + ", message=" + message + "]";
	}

}
