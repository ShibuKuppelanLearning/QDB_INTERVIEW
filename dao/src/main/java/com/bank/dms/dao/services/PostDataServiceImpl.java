package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.Post;

public class PostDataServiceImpl implements PostDataService {

	@Override
	public Long createPost(Post post) {
		if (post.getDocument() != null) {

		}
		return null;
	}

	@Override
	public Post fetchPostById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> fetchPostsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}