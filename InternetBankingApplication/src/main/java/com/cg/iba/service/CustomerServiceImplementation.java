package com.cg.iba.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Customer;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.ICustomerRepository;


@Service
public class CustomerServiceImplementation implements ICustomerService {
	
	@Autowired
	ICustomerRepository customerRepository;
	
	/**
     * addCustomer
     * <p>
     * Adding Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws InvalidDetailsException
     */
	
	public Customer addCustomer(Customer customer) throws InvalidDetailsException {
		if(customer.getCustomerName().length()<=0)
		{
			throw new InvalidDetailsException("Adding customer to database failed.");
		}
		else {
		    return customerRepository.save(customer);
		}
			
	}
	
	/**
     * updateCustomer
     * <p>
     * updating Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws InvalidDetailsException
     */

	public Customer updateCustomer(Customer customer) throws InvalidDetailsException {
		Customer existingCustomer = customerRepository.findById(customer.getCustomerId()).orElse(null);
		if(existingCustomer == null)
		{
			throw new InvalidDetailsException("Updation of customer details to database failed.");
		}else
		{
			return customerRepository.save(customer);
		}		
	}
	
	/**
     * deleteCustomer
     * <p>
     * deleting Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws DetailsNotFoundException
     */

	public boolean deleteCustomer(long customer_id) throws DetailsNotFoundException {
		boolean isDeleted = false;
		Customer customer = customerRepository.findById(customer_id).orElse(null);
		if(customer == null) {
			throw new DetailsNotFoundException("Invalid customer id, deletion failed");
		}else {
			customerRepository.deleteById(customer_id);
			isDeleted = true;
		}
		return isDeleted;
	}
	
	/**
     * listAllCustomer
     * <p>
     * listing all Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws EmptyListException
     */

	public Set<Customer> listAllCustomers(double minBalance) throws EmptyListException {
	    List<Customer> availableCustomersList = new ArrayList<Customer>();
	    availableCustomersList = customerRepository.listAllcustomer(minBalance);
	    if(availableCustomersList.isEmpty()) {
	        throw new EmptyListException("No customer accounts found with minBalance "+minBalance);
	    }
	    else {
	        Set<Customer> availableCustomersSet= new HashSet<Customer>();
	            availableCustomersSet.addAll(availableCustomersList);
	            for(Customer customer: availableCustomersSet) {
	                customer.setAccounts(null);
	            }
	            return availableCustomersSet;
		}
	}
	
	/**
     * viewCustomer
     * <p>
     * viewing Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws DetailsNotFoundException
     */

	public List<Customer> viewCustomerDetails(long account_id) throws DetailsNotFoundException {
		//Customer retrivedCustomer = customerRepository.findById(account_id).orElse(null);
	    
	    List<Customer> availableCustomersList = new ArrayList<Customer>();
	    availableCustomersList = customerRepository.viewCustomerDetails(account_id);
		if(availableCustomersList.isEmpty()) {
			throw new DetailsNotFoundException("No customers found with account id "+account_id+" to fetch");
		}
		else {
		    for(Customer customer: availableCustomersList) {
		        customer.setAccounts(null);
		    }
			return availableCustomersList;
		}
	}
	
	/**
     * findCustomer
     * <p>
     * finding Customer details to database
     * </p>
     * 
     * @param customer
     * @return Customer
     * @throws DetailsNotFoundException
     */

	public Customer findCustomerById(long customer_id) throws DetailsNotFoundException {
		Customer retrivedCustomer = customerRepository.findById(customer_id).orElse(null);
		if(retrivedCustomer==null) {
			throw new DetailsNotFoundException("No customer found with id "+customer_id+" to fetch");
		}
		else {
		    //changes made for correct input format
			//retrivedCustomer.setAccounts(null);
		    Set<Account> customerAccountsSet=retrivedCustomer.getAccounts();
		    for(Account account:customerAccountsSet) {
		        account.setBeneficiaries(null);
		        account.setCustomers(null);
		        account.setNominees(null);
		    }
		    retrivedCustomer.setAccounts(customerAccountsSet);
			return retrivedCustomer;
		}
	}
}
