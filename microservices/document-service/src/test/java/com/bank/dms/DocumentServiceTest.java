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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchDocumentResponse;

@SpringBootApplication
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DocumentServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final String SCHEME = "http";
	private static final String HOST = "localhost";
	private static final String CONTEXT = "/document-services";
	
	@Test
	void testUploadDocument() {	
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, "/document"));
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("file", new org.springframework.core.io.ClassPathResource("OAuth.pdf"));
		requestMap.add("customerId", 22);
		requestMap.add("fileDesc", "OAuth guide");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, httpHeaders);
		ResponseEntity<BaseResponse> responseEntityBaseResponse = testRestTemplate.exchange(uri, HttpMethod.POST, httpEntity, BaseResponse.class);
		assertUploadDocument(responseEntityBaseResponse);
	}

	@Test
	void testFetchDocumentById() {
		String location = "/document/41";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchDocumentResponse> responseEntity = testRestTemplate.getForEntity(uri,FetchDocumentResponse.class);
		assertFetchDocumentResponse(responseEntity);
	}

	@Test
	void testFetchDocumentByCustomerId() {
		String location = "/document/customer/22";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchDocumentResponse> responseEntity = testRestTemplate.getForEntity(uri,FetchDocumentResponse.class);
		assertFetchDocumentResponse(responseEntity);
	}

	@Test
	void testRemoveDocumentByCustomerIdAndDocumentId() {
		String location = "/document/22/41";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		testRestTemplate.delete(uri);
	}
	
	private void assertUploadDocument(ResponseEntity<BaseResponse> uploadDocumentResponseEntity) {
		assertNotNull(uploadDocumentResponseEntity);
		HttpHeaders httpHeaders = uploadDocumentResponseEntity.getHeaders();
		assertNotNull(httpHeaders);
		List<String> headerValues = httpHeaders.get("location");
		assertTrue(!CollectionUtils.isEmpty(headerValues));
		String locationValue = headerValues.get(0);
		assertTrue(StringUtils.isNotBlank(locationValue));
	}
	
	private void assertFetchDocumentResponse(ResponseEntity<FetchDocumentResponse> fetchDocumentResponseEntity) {
		FetchDocumentResponse fetchDocumentResponse = fetchDocumentResponseEntity.getBody();
		assertNotNull(fetchDocumentResponse);
		assertNotNull(fetchDocumentResponse.getDocuments());
		assertTrue(!CollectionUtils.isEmpty(fetchDocumentResponse.getDocuments()));
	}
}
