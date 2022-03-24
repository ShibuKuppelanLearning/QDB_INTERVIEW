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

import com.bank.dms.api.core.request.CreatePostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchPostResponse;
import com.bank.dms.core.vo.Document;
import com.bank.dms.core.vo.Post;
import com.bank.dms.core.vo.User;

@SpringBootApplication
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final String SCHEME = "http";
	private static final String HOST = "localhost";
	private static final String CONTEXT = "/post-services";

	@Test
	public void testCreatePost() {
		User user = new User(1L,null,null);
		Document document = new Document();
		document.setId(22L);
		Post post = new Post("OAuth - Guide", user, document);
		ResponseEntity<BaseResponse> createPostResponseEntity = createNewPost(new CreatePostRequest(post));
		assertCreatePost(createPostResponseEntity);
	}
	
	@Test
	public void testFetchPostById() {
		String location = "/post/41";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchPostResponse> responseEntity = testRestTemplate.getForEntity(uri,FetchPostResponse.class);
		assertFetchPostResponse(responseEntity);
	}

	@Test
	public void testFetchPostsByUserId() {
		String location = "/user/41";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchPostResponse> responseEntity = testRestTemplate.getForEntity(uri,FetchPostResponse.class);
		assertFetchPostResponse(responseEntity);
	};

	@Test
	public void testFetchPostByDocumentId() {
		String location = "/document/41";
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, location));
		ResponseEntity<FetchPostResponse> responseEntity = testRestTemplate.getForEntity(uri,FetchPostResponse.class);
		assertFetchPostResponse(responseEntity);
	}

	private ResponseEntity<BaseResponse> createNewPost(CreatePostRequest createPostRequest) {
		URI uri = URI.create(StringUtils.join(SCHEME, "://", HOST, ":", port, CONTEXT, "/post"));
		ResponseEntity<BaseResponse> responseEntity = testRestTemplate.postForEntity(uri, createPostRequest,
				BaseResponse.class);
		return responseEntity;
	}

	private void assertCreatePost(ResponseEntity<BaseResponse> createPostResponseEntity) {
		assertNotNull(createPostResponseEntity);
		HttpHeaders httpHeaders = createPostResponseEntity.getHeaders();
		assertNotNull(httpHeaders);
		List<String> headerValues = httpHeaders.get("location");
		assertTrue(!CollectionUtils.isEmpty(headerValues));
		String locationValue = headerValues.get(0);
		assertTrue(StringUtils.isNotBlank(locationValue));
	}

	private void assertFetchPostResponse(ResponseEntity<FetchPostResponse> fetchPostResponseEntity) {
		FetchPostResponse fetchPostResponse = fetchPostResponseEntity.getBody();
		assertNotNull(fetchPostResponse);
		assertNotNull(fetchPostResponse.getPosts());
		assertTrue(!CollectionUtils.isEmpty(fetchPostResponse.getPosts()));
	}
}