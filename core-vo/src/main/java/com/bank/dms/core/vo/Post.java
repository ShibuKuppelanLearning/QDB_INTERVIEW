package com.bank.dms.core.vo;

import java.io.Serializable;

public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 955427448218814732L;

	private Long id;

	private String description;

	private User createdBy;

	private Document document;

	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Post(String description, User createdBy, Document document) {
		super();
		this.description = description;
		this.createdBy = createdBy;
		this.document = document;
	}

	public Post(Long id, String description, User createdBy, Document document) {
		super();
		this.id = id;
		this.description = description;
		this.createdBy = createdBy;
		this.document = document;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
