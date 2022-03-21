package com.bank.dms.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.dms.dao.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
