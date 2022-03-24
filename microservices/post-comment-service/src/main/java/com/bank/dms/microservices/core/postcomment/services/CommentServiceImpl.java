package com.bank.dms.microservices.core.postcomment.services;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dms.api.core.postcomment.CommentService;
import com.bank.dms.api.core.request.CreateCommentAgainstPostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchCommentResponse;
import com.bank.dms.core.vo.Comment;
import com.bank.dms.dao.services.CommentDataService;

@RestController
@RequestMapping("/comment")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDataService commentDataService;  
	
	@Override
	@PostMapping
	public ResponseEntity<BaseResponse> createCommentAgainstPost(@RequestBody CreateCommentAgainstPostRequest commentAgainstPostRequest) {
		ResponseEntity<BaseResponse> responseEntity = null;
		Long postId = commentAgainstPostRequest.getPostId();
		Comment comment = commentAgainstPostRequest.getComment();
		Long commentId = null;
		if (postId != null && postId.longValue() > 0) {
			commentId = commentDataService.createCommentAgainstPost(postId, comment);
			if (commentId != null) {
				responseEntity = ResponseEntity.created(URI.create(StringUtils.join("/comment/", commentId))).build();	
			}
		}		
		return responseEntity;
	}


	@Override
	@GetMapping(path = "/post/{postId}")
	public ResponseEntity<FetchCommentResponse> fetchCommentsByPost(@PathVariable("postId") Long postId) {
		ResponseEntity<FetchCommentResponse> responseEntity = null;;
		List<Comment> comments = commentDataService.fetchCommentsByPost(postId);
		if(!CollectionUtils.isEmpty(comments)) {			
			FetchCommentResponse fetchCommentResponse = new FetchCommentResponse(comments);
			responseEntity = ResponseEntity.ok(fetchCommentResponse);			
		}
		return responseEntity;
	}	
}