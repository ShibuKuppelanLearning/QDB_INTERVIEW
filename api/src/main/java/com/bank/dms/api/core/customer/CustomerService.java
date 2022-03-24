package com.bank.dms.api.core.customer;

import org.springframework.http.ResponseEntity;

import com.bank.dms.api.core.request.AddNewCustomerRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchCustomerResponse;

public interface CustomerService {

	ResponseEntity<BaseResponse> addNewCustomer(AddNewCustomerRequest addNewCustomerRequest);

	ResponseEntity<FetchCustomerResponse> fetchCustomerById(Long customerId);
}
