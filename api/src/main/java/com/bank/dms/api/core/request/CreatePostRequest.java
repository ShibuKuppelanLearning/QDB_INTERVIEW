package com.bank.dms.api.core.request;

import java.io.Serializable;

import com.bank.dms.core.vo.Post;

public class CreatePostRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407460959217752694L;

	private Post post;

	public CreatePostRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreatePostRequest(Post post) {
		super();
		this.post = post;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
