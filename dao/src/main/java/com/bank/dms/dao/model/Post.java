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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "DMS_USER_POST")
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 955427448218814732L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_USER_POST_SEQ")
	@SequenceGenerator(sequenceName = "DMS_USER_POST_SEQ", allocationSize = 1, name = "DMS_USER_POST_SEQ")
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	private User createdBy;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID")
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
