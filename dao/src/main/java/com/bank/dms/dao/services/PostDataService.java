package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.Post;

public interface PostDataService {

	Long createPost(Post post);

	Post fetchPostById(Long userId);
	
	List<Post> fetchPostsByUserId(Long userId);
	
	Post fetchPostsByDocumentId(Long documentId);
}
