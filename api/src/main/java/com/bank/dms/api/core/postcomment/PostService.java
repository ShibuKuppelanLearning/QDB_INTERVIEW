package com.bank.dms.api.core.postcomment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.dms.api.core.request.CreatePostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchPostResponse;

public interface PostService {

	ResponseEntity<BaseResponse> createPost(CreatePostRequest request);
	
	ResponseEntity<FetchPostResponse> fetchPostById(@PathVariable("postId") Long postId);
	
	ResponseEntity<FetchPostResponse> fetchPostsByUserId(@PathVariable("userId") Long userId);
	
	ResponseEntity<FetchPostResponse> fetchPostByDocumentId(@PathVariable("documentId") Long documentId);
}