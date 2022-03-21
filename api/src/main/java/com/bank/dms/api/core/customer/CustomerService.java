package com.bank.dms.api.core.customer;

import org.springframework.http.ResponseEntity;

import com.bank.dms.api.core.request.AddNewCustomerRequest;
import com.bank.dms.api.core.response.BaseResponse;

public interface CustomerService {

	public ResponseEntity<BaseResponse> addNewCustomer(AddNewCustomerRequest addNewCustomerRequest);
}
