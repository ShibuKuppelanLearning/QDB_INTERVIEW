package com.bank.dms.api.core.postcomment;

import org.springframework.http.ResponseEntity;

import com.bank.dms.api.core.request.CreatePostRequest;
import com.bank.dms.api.core.response.BaseResponse;

public interface PostCommentService {

	ResponseEntity<BaseResponse> createPost(CreatePostRequest request);
}