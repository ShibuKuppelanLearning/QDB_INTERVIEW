package com.bank.dms.api.core.request;

public class AddNewCustomerRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5070887235376673513L;

	private String firstName;

	private String lastName;

	public AddNewCustomerRequest() {
		// TODO Auto-generated constructor stub
	}

	public AddNewCustomerRequest(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "AddNewCustomerRequest [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
