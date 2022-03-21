package com.bank.dms.api.core.document;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchDocumentResponse;

public interface DocumentService {

	public ResponseEntity<BaseResponse> uploadDocument(MultipartFile file, Long customerId, String fileDesc);

	public ResponseEntity<FetchDocumentResponse> fetchDocumentById(Long documentId);
	
	public ResponseEntity<FetchDocumentResponse> fetchDocumentByCustomerId(Long customerId);
	
	public ResponseEntity<BaseResponse> removeDocumentByCustomerIdAndDocumentId(Long customerId,Long documentId);

}
