package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.Customer;

public interface CustomerDataService {

	Long saveCustomer(Customer customer);

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();
}
