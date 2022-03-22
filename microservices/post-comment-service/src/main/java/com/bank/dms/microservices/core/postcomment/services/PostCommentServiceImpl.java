package com.bank.dms.microservices.core.postcomment.services;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dms.api.core.postcomment.PostCommentService;
import com.bank.dms.api.core.request.CreatePostRequest;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.core.vo.Post;
import com.bank.dms.dao.services.PostDataService;

@RestController
@RequestMapping("/post")
public class PostCommentServiceImpl implements PostCommentService {

	private static final Logger logger = LoggerFactory.getLogger(PostCommentServiceImpl.class);

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
}
