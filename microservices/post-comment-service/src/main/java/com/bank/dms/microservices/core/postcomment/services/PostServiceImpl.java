package com.bank.dms.microservices.core.postcomment.services;

import java.net.URI;
import java.util.List;

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

import com.bank.dms.api.core.postcomment.PostService;
import com.bank.dms.api.core.request.CreatePostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchPostResponse;
import com.bank.dms.core.vo.ErrorInfo;
import com.bank.dms.core.vo.Post;
import com.bank.dms.dao.services.PostDataService;
import com.bank.dms.util.exception.DataSaveException;
import com.bank.dms.util.exception.PostNotFoundException;
import com.bank.dms.util.exception.UserNotFoundException;

@RestController
@RequestMapping("/post")
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostDataService postDataService;

	@Override
	@PostMapping
	public ResponseEntity<BaseResponse> createPost(@RequestBody CreatePostRequest request) {
		ResponseEntity<BaseResponse> responseEntity = null;
		Post post = request.getPost();
		if (post != null) {
			if (post.getCreatedBy() != null) {
				Long postId = postDataService.createPost(post);
				if (postId != null && postId.longValue() > 0) {
					responseEntity = ResponseEntity.created(URI.create(StringUtils.join("/post/", postId))).build();
				}
			}
		}
		return responseEntity;
	}

	@Override
	@GetMapping(path = "/{postId}")
	public ResponseEntity<FetchPostResponse> fetchPostById(@PathVariable("postId") Long postId) {
		ResponseEntity<FetchPostResponse> response = null;
		if (postId != null && postId.longValue() > 0) {
			try {
				Post post = postDataService.fetchPostById(postId);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				fetchPostResponse.addPost(post);
				response = ResponseEntity.ok(fetchPostResponse);
			} catch (PostNotFoundException notFoundException) {
				response = ResponseEntity.notFound().build();
				logger.error("Error while fetching post by id ", notFoundException);
			} catch (Exception exception) {
				BaseResponse baseResponse = createPostServiceResponseForFailure(exception);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchPostResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchPostResponse);
				logger.error("Error while fetching post by id ", exception);
			}
		}
		return response;
	}
	
	
	
	private BaseResponse createPostServiceResponseForFailure(Exception exception) {
		BaseResponse response = new BaseResponse();
		if (exception instanceof DataSaveException) {
			DataSaveException dataSaveException = (DataSaveException) exception;
			response.addError(new ErrorInfo(dataSaveException.getErrorCode(), dataSaveException.getErrorMessage()));
		} else if (exception instanceof PostNotFoundException) {
			PostNotFoundException postNotFoundException = (PostNotFoundException) exception;
			response.addError(new ErrorInfo(postNotFoundException.getErrorCode(),
					postNotFoundException.getErrorMessage()));
		} else {
			response.addError(new ErrorInfo("UNABLE_TO_PROCESS", "Unable to process request"));
		}
		return response;
	}

	@Override
	@GetMapping(path = "/user/{userId}")
	public ResponseEntity<FetchPostResponse> fetchPostsByUserId(
			@PathVariable("userId") Long userId) {
		ResponseEntity<FetchPostResponse> response = null;
		if (userId != null && userId.longValue() > 0) {
			try {
				List<Post> posts = postDataService.fetchPostsByUserId(userId);
				response = ResponseEntity.ok(new FetchPostResponse(posts));
			} catch (PostNotFoundException notFoundException) {
				response = ResponseEntity.notFound().build();
				logger.error("Error while fetching post by user id ", notFoundException);
			} catch (UserNotFoundException userNotFoundException) {
				BaseResponse baseResponse = createPostServiceResponseForFailure(userNotFoundException);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchPostResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchPostResponse);
				logger.error("Error while fetching post by user id ", userNotFoundException);
			} catch (Exception exception) {
				BaseResponse baseResponse = createPostServiceResponseForFailure(exception);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchPostResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchPostResponse);
				logger.error("Error while fetching post by user id ", exception);
			}
		}
		return response;
	}

	@Override
	@GetMapping(path = "/document/{documentId}")
	public ResponseEntity<FetchPostResponse> fetchPostByDocumentId(Long documentId) {
		ResponseEntity<FetchPostResponse> response = null;
		if (documentId != null && documentId.longValue() > 0) {
			try {
				Post post = postDataService.fetchPostsByDocumentId(documentId);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				fetchPostResponse.addPost(post);
				response = ResponseEntity.ok(fetchPostResponse);
			} catch (PostNotFoundException notFoundException) {
				response = ResponseEntity.notFound().build();
				logger.error("Error while fetching post by document id ", notFoundException);
			} catch (UserNotFoundException userNotFoundException) {
				BaseResponse baseResponse = createPostServiceResponseForFailure(userNotFoundException);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchPostResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchPostResponse);
				logger.error("Error while fetching post by document id ", userNotFoundException);
			} catch (Exception exception) {
				BaseResponse baseResponse = createPostServiceResponseForFailure(exception);
				FetchPostResponse fetchPostResponse = new FetchPostResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchPostResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchPostResponse);
				logger.error("Error while fetching post by document id ", exception);
			}
		}
		return response;
	}
}