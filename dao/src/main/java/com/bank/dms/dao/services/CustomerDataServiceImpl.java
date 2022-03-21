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

import com.bank.dms.core.vo.Customer;
import com.bank.dms.dao.repository.CustomerRepository;
import com.bank.dms.util.exception.DataSaveException;

@Component
public class CustomerDataServiceImpl implements CustomerDataService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerDataServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public Long saveCustomer(Customer customer) {
		Long customerId = null;
		if (customer != null) {
			try {
				com.bank.dms.dao.model.Customer customerModel = new com.bank.dms.dao.model.Customer(
						customer.getFirstName(), customer.getLastName());
				customerModel = customerRepository.save(customerModel);
				if (customerModel != null) {
					customerId = customerModel.getId();
				}
			} catch (DataException | ConstraintViolationException | JDBCConnectionException exception) {
				logger.error("Error while saving customer", exception);
				throw new DataSaveException("DATA_SAVE_ERROR", "Unable to save customer");
			} catch (Exception exception) {
				logger.error("Error while saving customer", exception);
				throw new DataSaveException("UNKNOWN_SAVE_ERROR", "Unable to save customer due to unknown error");
			}
		}
		return customerId;
	}

	@Override
	public Customer getCustomerById(Long id) {
		Customer customer = null;
		if (id != null && id.longValue() >= 0) {
			try {
				com.bank.dms.dao.model.Customer customerModel = customerRepository.findById(id).orElse(null);
				customer = new Customer(customerModel.getId(), customerModel.getFirstName(),
						customerModel.getLastName());
			} catch (DataException dataException) {
				logger.error("Error while fetching customer details", dataException);
			} catch (ConstraintViolationException constraintViolationException) {
				logger.error("Constraint voilation exception", constraintViolationException);
			} catch (JDBCConnectionException jdbcConnectionException) {
				logger.error("Constraint voilation exception", jdbcConnectionException);
			}
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		final List<Customer> customers = new ArrayList<Customer>();
		try {
			Iterable<com.bank.dms.dao.model.Customer> customerModels = customerRepository.findAll();
			customerModels.forEach(customerModel -> {
				customers.add(
						new Customer(customerModel.getId(), customerModel.getFirstName(), customerModel.getLastName()));
			});
		} catch (DataException dataException) {
			logger.error("Error while saving document", dataException);
		} catch (ConstraintViolationException constraintViolationException) {
			logger.error("Constraint voilation exception", constraintViolationException);
		} catch (JDBCConnectionException jdbcConnectionException) {
			logger.error("Constraint voilation exception", jdbcConnectionException);
		}
		return customers;
	}
}
