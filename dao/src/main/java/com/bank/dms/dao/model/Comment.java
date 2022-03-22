package com.bank.dms.dao.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "DMS_USER_COMMENT")
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8767826936801658400L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_USER_COMMENT_SEQ")
	@SequenceGenerator(sequenceName = "DMS_USER_COMMENT_SEQ", allocationSize = 1, name = "DMS_USER_COMMENT_SEQ")
	private Long id;

	@Column(name = "CONTENT")
	private String content;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	private User createdBy;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID", referencedColumnName = "ID")
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
