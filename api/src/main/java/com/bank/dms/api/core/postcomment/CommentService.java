package com.bank.dms.api.core.postcomment;

import org.springframework.http.ResponseEntity;

import com.bank.dms.api.core.request.CreateCommentAgainstPostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchCommentResponse;

public interface CommentService {

	ResponseEntity<BaseResponse> createCommentAgainstPost(CreateCommentAgainstPostRequest commentAgainstPostRequest);

	ResponseEntity<FetchCommentResponse> fetchCommentsByPost(Long postId);
}
