package com.bank.dms.util.exception;

public class DocumentNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3017886216815126683L;

	public DocumentNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public DocumentNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
