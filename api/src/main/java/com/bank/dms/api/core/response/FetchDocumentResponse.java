package com.bank.dms.api.core.response;

import java.util.ArrayList;
import java.util.List;

import com.bank.dms.core.vo.Document;

public class FetchDocumentResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1125753594109406862L;

	private List<Document> documents;

	public FetchDocumentResponse() {
		// TODO Auto-generated constructor stub
	}

	public FetchDocumentResponse(List<Document> documents) {
		super();
		this.documents = documents;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public void addDocument(Document document) {
		if (this.documents == null)
			this.documents = new ArrayList<Document>();
		this.documents.add(document);
	}
}
