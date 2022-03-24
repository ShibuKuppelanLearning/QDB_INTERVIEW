package com.bank.dms.util.exception;

public class UserNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2654097147934136243L;

	public UserNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
