package com.bank.dms.api.core.response;

import java.util.ArrayList;
import java.util.List;

import com.bank.dms.core.vo.Customer;

public class FetchCustomerResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3391169021069629566L;

	private List<Customer> customers;

	public FetchCustomerResponse() {
		// TODO Auto-generated constructor stub
	}

	public FetchCustomerResponse(List<Customer> customers) {
		super();
		this.customers = customers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void addCustomer(Customer customer) {
		if (this.customers == null)
			this.customers = new ArrayList<Customer>();
		this.customers.add(customer);
	}
}
