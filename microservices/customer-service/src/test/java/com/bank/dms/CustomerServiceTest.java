package com.bank.dms;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import com.bank.dms.api.core.request.AddNewCustomerRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchCustomerResponse;

@SpringBootApplication
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final String SCHEME = "http";
	private static final String HOST = "localhost";
	private static final String CONTEXT = "/customer-services";

	@Test
	void testAddCustomerAndFetchById() {
		ResponseEntity<BaseResponse> addCustomerResponseEntity = null;
		AddNewCustomerRequest addNewCustomerRequest = new AddNewCustomerRequest("Shibu", "Kuppelan");
		addCustomerResponseEntity = addNewCustomer(addNewCustomerRequest);
		assertAddNewCustomer(addCustomerResponseEntity);		
		HttpHeaders httpHeaders = addCustomerResponseEntity.getHeaders();		
		List<String> headerValues = httpHeaders.get("location");		
		String locationValue = headerValues.get(0);		
		ResponseEntity<FetchCustomerResponse> fetchCustomerResponseEntity = fetchCustomerById(locationValue);
		assertFetchCustomerResponse(fetchCustomerResponseEntity);
	}

	private ResponseEntity<BaseResponse> addNewCustomer(AddNewCustomerRequest addNewCustomerRequest) {
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, "/customer"));
		ResponseEntity<BaseResponse> responseEntity = testRestTemplate.postForEntity(uri, addNewCustomerRequest,
				BaseResponse.class);
		return responseEntity;
	}

	private ResponseEntity<FetchCustomerResponse> fetchCustomerById(String location) {
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchCustomerResponse> responseEntity = testRestTemplate.getForEntity(uri,
				FetchCustomerResponse.class);
		return responseEntity;
	}
	
	@Test
	public void addNewCustomer() {
		ResponseEntity<BaseResponse> addCustomerResponseEntity = addNewCustomer(new AddNewCustomerRequest("Shibu", "Kuppelan"));
		assertAddNewCustomer(addCustomerResponseEntity);
	}

	@Test
	public void fetchCustomerById() {
		ResponseEntity<FetchCustomerResponse> fetchCustomerResponseEntity = fetchCustomerById("/customer/53");
		assertFetchCustomerResponse(fetchCustomerResponseEntity);
	}
	
	private void assertAddNewCustomer(ResponseEntity<BaseResponse> addCustomerResponseEntity) {
		assertNotNull(addCustomerResponseEntity);
		HttpHeaders httpHeaders = addCustomerResponseEntity.getHeaders();
		assertNotNull(httpHeaders);
		List<String> headerValues = httpHeaders.get("location");
		assertTrue(!CollectionUtils.isEmpty(headerValues));
		String locationValue = headerValues.get(0);
		assertTrue(StringUtils.isNotBlank(locationValue));
	}
	
	private void assertFetchCustomerResponse(ResponseEntity<FetchCustomerResponse> fetchCustomerResponseEntity) {
		FetchCustomerResponse fetchCustomerResponse = fetchCustomerResponseEntity.getBody();
		assertNotNull(fetchCustomerResponse);
		assertNotNull(fetchCustomerResponse.getCustomers());
		assertTrue(!CollectionUtils.isEmpty(fetchCustomerResponse.getCustomers()));
	}
}