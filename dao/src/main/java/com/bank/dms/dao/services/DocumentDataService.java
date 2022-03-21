package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.Document;

public interface DocumentDataService {

	Long saveDocument(Long customerId,Document document);

	Document getDocumentById(Long id);

	List<Document> getDocumentByCustomerId(Long customerId);
	
	void removeDocumentByCustIdAndDocId(Long customerId,Long documentId);
}
