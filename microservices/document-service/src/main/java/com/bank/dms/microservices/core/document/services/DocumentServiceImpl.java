package com.bank.dms.microservices.core.document.services;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dms.api.core.document.DocumentService;
import com.bank.dms.api.core.response.BaseResponse;
import com.bank.dms.api.core.response.FetchDocumentResponse;
import com.bank.dms.core.vo.Document;
import com.bank.dms.core.vo.ErrorInfo;
import com.bank.dms.dao.services.DocumentDataService;
import com.bank.dms.util.exception.BaseException;
import com.bank.dms.util.exception.CustomerNotFoundException;
import com.bank.dms.util.exception.DataSaveException;
import com.bank.dms.util.exception.DocumentNotFoundException;

@RestController
@RequestMapping("/document")
public class DocumentServiceImpl implements DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	@Autowired
	private DocumentDataService documentDataService;

	@Override
	@PostMapping
	public ResponseEntity<BaseResponse> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("customerId") Long customerId, @RequestParam("fileDesc") String fileDesc) {
		Long docId = null;
		ResponseEntity<BaseResponse> response = null;
		if (file != null) {
			try {
				byte[] fileContent = file.getBytes();
				Document document = new Document(file.getName(), fileDesc, null, fileContent);
				docId = documentDataService.saveDocument(customerId, document);
				response = ResponseEntity.created(URI.create(StringUtils.join("/document/", docId))).build();
			} catch (DataSaveException dataSaveException) {
				logger.error("Error while uploading document", dataSaveException);
				response = ResponseEntity.unprocessableEntity()
						.body(createDocumentServiceResponseForFailure(dataSaveException));
			} catch (Exception exception) {
				logger.error("Error while uploading document", exception);
				response = ResponseEntity.unprocessableEntity()
						.body(createDocumentServiceResponseForFailure(exception));
			}
		}
		return response;
	}

	private BaseResponse createDocumentServiceResponseForFailure(Exception exception) {
		BaseResponse response = new BaseResponse();
		if (exception instanceof DataSaveException) {
			DataSaveException dataSaveException = (DataSaveException) exception;
			response.addError(new ErrorInfo(dataSaveException.getErrorCode(), dataSaveException.getErrorMessage()));
		} else if (exception instanceof CustomerNotFoundException) {
			CustomerNotFoundException customerNotFoundException = (CustomerNotFoundException) exception;
			response.addError(new ErrorInfo(customerNotFoundException.getErrorCode(),
					customerNotFoundException.getErrorMessage()));
		} else {
			response.addError(new ErrorInfo("UNABLE_TO_PROCESS", "Unable to process request"));
		}
		return response;
	}

	@Override
	@GetMapping(path = "/{documentId}")
	public ResponseEntity<FetchDocumentResponse> fetchDocumentById(@PathVariable("documentId") Long documentId) {
		ResponseEntity<FetchDocumentResponse> response = null;
		if (documentId != null && documentId.longValue() > 0) {
			try {
				Document document = documentDataService.getDocumentById(documentId);
				FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
				fetchDocumentResponse.addDocument(document);
				response = ResponseEntity.ok(fetchDocumentResponse);
			} catch (DocumentNotFoundException notFoundException) {
				response = ResponseEntity.notFound().build();
				logger.error("Error while fetching document by id ", notFoundException);
			} catch (Exception exception) {
				BaseResponse baseResponse = createDocumentServiceResponseForFailure(exception);
				FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchDocumentResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchDocumentResponse);
				logger.error("Error while fetching document by id ", exception);
			}
		}
		return response;
	}

	@Override
	@GetMapping(path = "/customer/{customerId}")
	public ResponseEntity<FetchDocumentResponse> fetchDocumentByCustomerId(
			@PathVariable("customerId") Long customerId) {
		ResponseEntity<FetchDocumentResponse> response = null;
		if (customerId != null && customerId.longValue() > 0) {
			try {
				List<Document> documents = documentDataService.getDocumentByCustomerId(customerId);
				response = ResponseEntity.ok(new FetchDocumentResponse(documents));
			} catch (DocumentNotFoundException notFoundException) {
				response = ResponseEntity.notFound().build();
				logger.error("Error while fetching document by customer id ", notFoundException);
			} catch (CustomerNotFoundException customerNotFoundException) {
				BaseResponse baseResponse = createDocumentServiceResponseForFailure(customerNotFoundException);
				FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchDocumentResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchDocumentResponse);
				logger.error("Error while fetching document by customer id ", customerNotFoundException);
			} catch (Exception exception) {
				BaseResponse baseResponse = createDocumentServiceResponseForFailure(exception);
				FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
				baseResponse.getErrors().forEach(errorInfo -> {
					fetchDocumentResponse.addError(errorInfo);
				});
				response = ResponseEntity.unprocessableEntity().body(fetchDocumentResponse);
				logger.error("Error while fetching document by customer id ", exception);
			}
		}
		return response;
	}

	@Override
	@DeleteMapping(path = "{customerId}/{documentId}")
	public ResponseEntity<BaseResponse> removeDocumentByCustomerIdAndDocumentId(
			@PathVariable("customerId") Long customerId, @PathVariable("documentId") Long documentId) {
		ResponseEntity<BaseResponse> response = null;
		if (customerId != null && customerId.longValue() > 0) {
			if (documentId != null && documentId.longValue() > 0) {
				try {
					documentDataService.removeDocumentByCustIdAndDocId(customerId, documentId);
					response = ResponseEntity.ok().build();
				} catch (CustomerNotFoundException notFoundException) {
					BaseResponse baseResponse = createDocumentServiceResponseForFailure(notFoundException);
					FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
					baseResponse.getErrors().forEach(errorInfo -> {
						fetchDocumentResponse.addError(errorInfo);
					});
					response = ResponseEntity.unprocessableEntity().body(baseResponse);
				} catch (DocumentNotFoundException notFoundException) {
					BaseResponse baseResponse = createDocumentServiceResponseForFailure(notFoundException);
					FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
					baseResponse.getErrors().forEach(errorInfo -> {
						fetchDocumentResponse.addError(errorInfo);
					});
					response = ResponseEntity.unprocessableEntity().body(baseResponse);
				} catch (BaseException baseException) {
					BaseResponse baseResponse = createDocumentServiceResponseForFailure(baseException);
					FetchDocumentResponse fetchDocumentResponse = new FetchDocumentResponse();
					baseResponse.getErrors().forEach(errorInfo -> {
						fetchDocumentResponse.addError(errorInfo);
					});
					response = ResponseEntity.unprocessableEntity().body(baseResponse);
				}
			} else {
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.addError(new ErrorInfo("INVALID_DOCUMENT_ID", "Invalid document id"));
				response = ResponseEntity.badRequest().body(baseResponse);
			}
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.addError(new ErrorInfo("INVALID_CUSTOMER_ID", "Invalid customer id"));
			response = ResponseEntity.badRequest().body(baseResponse);
		}
		return response;
	}
}