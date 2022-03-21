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

@Entity(name = "DOCUMENT")
public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1058124513231986477L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOC_SEQ")
	@SequenceGenerator(sequenceName = "DOC_SEQ", allocationSize = 1, name = "DOC_SEQ")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FPATH")
	private String path;

	@Column(name = "CONTENT")
	private byte[] content;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
	private Customer customer;

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public Document(Long id, String name, String description, String path, byte[] content) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.path = path;
		this.content = content;
	}

	public Document(String name, String description, String path, byte[] content, Customer customer) {
		super();
		this.name = name;
		this.description = description;
		this.path = path;
		this.content = content;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Document [name=" + name + ", description=" + description + ", path=" + path + "]";
	}
}
