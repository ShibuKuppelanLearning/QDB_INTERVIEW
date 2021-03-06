package com.bank.dms.core.vo;

import java.io.Serializable;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8767826936801658400L;

	private Long id;

	private String content;

	private User createdBy;

	private Post post;

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(String content, User createdBy, Post post) {
		super();
		this.content = content;
		this.createdBy = createdBy;
		this.post = post;
	}

	public Comment(Long id, String content, User createdBy, Post post) {
		super();
		this.id = id;
		this.content = content;
		this.createdBy = createdBy;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
