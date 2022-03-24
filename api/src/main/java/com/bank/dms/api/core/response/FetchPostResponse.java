package com.bank.dms.api.core.response;

import java.util.ArrayList;
import java.util.List;

import com.bank.dms.core.vo.Post;

public class FetchPostResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1810992330167199573L;

	private List<Post> posts;

	public FetchPostResponse() {
		// TODO Auto-generated constructor stub
	}

	public FetchPostResponse(List<Post> posts) {
		super();
		this.posts = posts;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void addPost(Post post) {
		if (this.posts == null)
			this.posts = new ArrayList<Post>();
		this.posts.add(post);
	}
}
