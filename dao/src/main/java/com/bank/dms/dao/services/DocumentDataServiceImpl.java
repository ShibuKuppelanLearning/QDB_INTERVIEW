package com.bank.dms.dao.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bank.dms.core.vo.Document;
import com.bank.dms.dao.model.Customer;
import com.bank.dms.dao.repository.CustomerRepository;
import com.bank.dms.dao.repository.DocumentRepository;
import com.bank.dms.util.exception.BaseException;
import com.bank.dms.util.exception.CustomerNotFoundException;
import com.bank.dms.util.exception.DataSaveException;
import com.bank.dms.util.exception.DocumentNotFoundException;

@Component
public class DocumentDataServiceImpl implements DocumentDataService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentDataServiceImpl.class);

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public Long saveDocument(Long customerId, Document document) {
		Long documentId = null;
		if (customerId != null && customerId.longValue() > 0) {
			Customer customer = customerRepository.findById(customerId).orElse(null);
			if (customer != null && document != null) {
				try {
					com.bank.dms.dao.model.Document documentModel = new com.bank.dms.dao.model.Document(
							document.getName(), document.getDescription(), document.getPath(), document.getContent(),
							customer);
					documentModel = documentRepository.save(documentModel);
					if (documentModel != null) {
						documentId = documentModel.getId();
					}
				} catch (DataException | ConstraintViolationException | JDBCConnectionException exception) {
					logger.error("Error while saving document", exception);
					throw new DataSaveException("DATA_SAVE_ERROR", "Unable to save document");
				} catch (Exception exception) {
					logger.error("Error while saving document", exception);
					throw new DataSaveException("UNKNOWN_SAVE_ERROR", "Unable to save document due to unknown error");
				}
			} else if (customer == null) {
				logger.error("Error while saving document : Customer not found");
				throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found");
			}
		} else {
			logger.error("Error while saving document : Customer id cannot be null");
			throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found");
		}
		return documentId;
	}

	@Override
	public Document getDocumentById(Long id) {
		Document document = null;
		if (id != null && id.longValue() >= 0) {
			try {
				com.bank.dms.dao.model.Document documentModel = documentRepository.findById(id).orElse(null);
				if (documentModel != null) {
					document = new Document(documentModel.getName(), documentModel.getDescription(),
							documentModel.getPath(), documentModel.getContent());
				} else {
					throw new DocumentNotFoundException("DOCUMENT_NOT_FOUND", "Document with id does not exist");
				}
			} catch (DataException dataException) {
				logger.error("Error while fetching document", dataException);
			} catch (JDBCConnectionException jdbcConnectionException) {
				logger.error("JDBC connection exception", jdbcConnectionException);
			}
		}
		return document;
	}

	@Override
	public List<Document> getDocumentByCustomerId(Long customerId) {
		final List<Document> documents = new ArrayList<Document>();
		if (customerId != null && customerId.longValue() >= 0) {
			try {
				Customer customer = customerRepository.findById(customerId).orElse(null);
				if (customer != null) {
					List<com.bank.dms.dao.model.Document> documentModels = documentRepository.findByCustomer(customer);
					if (!CollectionUtils.isEmpty(documentModels)) {
						documentModels.forEach(documentModel -> {
							documents.add(new Document(documentModel.getName(), documentModel.getDescription(),
									documentModel.getPath(), documentModel.getContent()));
						});
					} else {
						throw new DocumentNotFoundException("DOCUMENT_NOT_FOUND", "No document found for customer");
					}
				} else {
					throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found");
				}
			} catch (DataException dataException) {
				logger.error("Error while saving document", dataException);
			} catch (JDBCConnectionException jdbcConnectionException) {
				logger.error("Constraint voilation exception", jdbcConnectionException);
			}
		}
		return documents;
	}

	@Override
	@Transactional
	public void removeDocumentByCustIdAndDocId(Long customerId, Long documentId) {
		try {
			Customer customer = customerRepository.findById(customerId).orElse(null);
			if (customer != null) {
				if (documentId != null && documentId.longValue() >= 0) {
					documentRepository.deleteByCustomerAndId(customer, documentId);
				}
			} else {
				throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found");
			}
		} catch (DataException exception) {
			throw new DocumentNotFoundException("DOCUMENT_NOT_FOUND", "No document found for customer");
		} catch (ConstraintViolationException | JDBCConnectionException exception) {
			throw new BaseException("UNKNOWN_ERROR", "Unable to remove document id");
		}
	}
}