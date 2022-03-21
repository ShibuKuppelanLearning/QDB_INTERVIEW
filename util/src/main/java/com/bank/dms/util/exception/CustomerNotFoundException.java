package com.bank.dms.util.exception;

public class CustomerNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7426985044729345363L;

	public CustomerNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public CustomerNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
