package com.bank.dms.core.vo;

import java.io.Serializable;

public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1058124513231986477L;

	private Long id;
	private String name;
	private String description;
	private String path;
	private byte[] content;

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public Document(String name, String description, String path, byte[] content) {
		super();
		this.name = name;
		this.description = description;
		this.path = path;
		this.content = content;
	}

	public Document(Long id, String name, String description, String path, byte[] content) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.path = path;
		this.content = content;
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

	@Override
	public String toString() {
		return "Document [name=" + name + ", description=" + description + ", path=" + path + "]";
	}
}
