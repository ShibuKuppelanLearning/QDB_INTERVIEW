package com.bank.dms.microservices.core.customer.services;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dms.api.core.customer.CustomerService;
import com.bank.dms.api.core.request.AddNewCustomerRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchCustomerResponse;
import com.bank.dms.core.vo.Customer;
import com.bank.dms.core.vo.ErrorInfo;
import com.bank.dms.dao.services.CustomerDataService;
import com.bank.dms.util.exception.CustomerNotFoundException;
import com.bank.dms.util.exception.DataSaveException;

@RestController
@RequestMapping("/customer")
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDataService customerDataService;

	private BaseResponse createCustomerServiceFailureResponse(Exception exception) {
		BaseResponse response = null;
		if (exception instanceof DataSaveException) {
			DataSaveException dataSaveException = (DataSaveException) exception;
			response = new BaseResponse();
			response.addError(new ErrorInfo(dataSaveException.getErrorCode(), dataSaveException.getErrorMessage()));
		} else {
			response = new BaseResponse();
			response.addError(new ErrorInfo("UNABLE_TO_PROCESS", "Unable to process request"));
		}
		return response;
	}

	@Override
	@PostMapping
	public ResponseEntity<BaseResponse> addNewCustomer(@RequestBody AddNewCustomerRequest addNewCustomerRequest) {
		ResponseEntity<BaseResponse> response = null;
		if (addNewCustomerRequest != null && StringUtils.isNotBlank(addNewCustomerRequest.getFirstName())
				&& StringUtils.isNotBlank(addNewCustomerRequest.getLastName())) {
			try {
				Customer customer = new Customer(addNewCustomerRequest.getFirstName(),
						addNewCustomerRequest.getLastName());
				Long customerId = customerDataService.saveCustomer(customer);
				if (customerId != null && customerId.longValue() > 0) {
					response = ResponseEntity.created(URI.create(StringUtils.join("/customer/", customerId))).build();
				}
			} catch (DataSaveException dataSaveException) {
				logger.error("Error while saving customer", dataSaveException);
				response = ResponseEntity.unprocessableEntity()
						.body(createCustomerServiceFailureResponse(dataSaveException));
			} catch (Exception exception) {
				logger.error("Error while saving customer", exception);
				response = ResponseEntity.unprocessableEntity().body(createCustomerServiceFailureResponse(exception));
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			if (StringUtils.isNotBlank(addNewCustomerRequest.getFirstName())) {
				baseResponse.addError(new ErrorInfo("INVALID_FIRST_NAME", "Invalid first name"));
			}

			if (StringUtils.isNotBlank(addNewCustomerRequest.getLastName())) {
				baseResponse.addError(new ErrorInfo("INVALID_LAST_NAME", "Invalid last name"));
			}
			response = ResponseEntity.unprocessableEntity().body(baseResponse);
			logger.error("Error while saving customer");
		}
		return response;
	}

	@GetMapping(path = "{customerId}")
	public ResponseEntity<FetchCustomerResponse> fetchCustomerById(@PathVariable("customerId") Long customerId) {
		ResponseEntity<FetchCustomerResponse> response = null;
		if (customerId != null && customerId.longValue() > 0) {
			try {
				Customer customer = customerDataService.getCustomerById(customerId);
				if (customer != null) {
					FetchCustomerResponse fetchCustomerResponse = new FetchCustomerResponse();
					fetchCustomerResponse.addCustomer(customer);
					response = ResponseEntity.ok(fetchCustomerResponse);
				}
			} catch (CustomerNotFoundException customerNotFoundException) {
				logger.error("Error while fetching customer by id", customerNotFoundException);				
				response = ResponseEntity.notFound().build();
			} catch (Exception exception) {
				logger.error("Error while fetching customer by id", exception);
				BaseResponse baseResponse = createCustomerServiceFailureResponse(exception);
				if (baseResponse != null) {
					FetchCustomerResponse fetchCustomerResponse = new FetchCustomerResponse();
					fetchCustomerResponse
							.addError(new ErrorInfo("UNKNOWN_ERROR", "Error while fetching customer by id"));
					response = ResponseEntity.unprocessableEntity().body(fetchCustomerResponse);
				}
			}
		} else {
			logger.error("Error while fetching customer by id");
			FetchCustomerResponse fetchCustomerResponse = new FetchCustomerResponse();
			fetchCustomerResponse.addError(new ErrorInfo("UNKNOWN_ERROR", "Error while fetching customer by id"));
			response = ResponseEntity.badRequest().body(fetchCustomerResponse);
		}
		return response;
	}
}