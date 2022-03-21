package com.bank.dms.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.dms.dao.model.Customer;
import com.bank.dms.dao.model.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
	List<Document> findByCustomer(Customer customer);
	
	void deleteByCustomerAndId(Customer customer,Long id);
}
