package com.bank.dms.util.exception;

public class PostNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1615322599848318565L;

	public PostNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public PostNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}