package com.cg.iba.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Customer;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.CustomerRepository;

@Service
public class CustomerServiceImplementation implements ICustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
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
	
	@Transactional
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

	@Transactional
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

	@Transactional
	public boolean deleteCustomer(long customer_id) throws DetailsNotFoundException {
		boolean isDeleted = false;
		Customer customer = customerRepository.findById(customer_id).orElse(null);
		if(customer == null) {
			throw new DetailsNotFoundException("Invalid customer id, deletion failed");
		}else {
			customerRepository.delete(customer);
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

	@Transactional
	public Set<Customer> listAllCustomers(double minBalance) throws EmptyListException {
	    List<Customer> availableCustomersList = new ArrayList<Customer>();
	    availableCustomersList = customerRepository.listAllcustomer(minBalance);
	    if(availableCustomersList.isEmpty()) {
	        throw new EmptyListException("No customers found with minBalance "+minBalance);
	    }
	    else {
	        Set<Customer> availableCustomersSet= new HashSet<Customer>();
	            availableCustomersSet.addAll(availableCustomersList);
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

	@Transactional
	public List<Customer> viewCustomerDetails(long account_id) throws DetailsNotFoundException {
		//Customer retrivedCustomer = customerRepository.findById(account_id).orElse(null);
	    List<Customer> availableCustomersList = new ArrayList<Customer>();
	    availableCustomersList = customerRepository.viewCustomerDetails(account_id);
		if(availableCustomersList.isEmpty()) {
			throw new DetailsNotFoundException("No customers found with id "+account_id+" to fetch");
		}
		else {
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
	
	@Transactional
	public Customer findCustomerById(long customer_id) throws DetailsNotFoundException {
		Customer retrivedCustomer = customerRepository.findById(customer_id).orElse(null);
		if(retrivedCustomer==null) {
			throw new DetailsNotFoundException("No customer found with id "+customer_id+" to fetch");
		}
		else {
			return retrivedCustomer;
		}
	}
}
