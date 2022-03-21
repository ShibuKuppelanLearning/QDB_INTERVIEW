package com.bank.dms.api.core.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bank.dms.core.vo.ErrorInfo;

public class BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3357869152841429545L;

	private List<ErrorInfo> errors;

	public BaseResponse() {
		// TODO Auto-generated constructor stub
	}

	public List<ErrorInfo> getErrors() {		
		return errors;
	}

	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}

	public void addError(ErrorInfo errorInfo) {
		if(errors==null)
			errors = new ArrayList<ErrorInfo>();
		errors.add(errorInfo);
	}
}
