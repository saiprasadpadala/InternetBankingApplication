package com.cg.iba.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.Customer;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.CustomerServiceImplementation;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
	@Autowired
	public CustomerServiceImplementation customerServiceImplementation;
	
	/**
     * addCustomer
     * <p>
     * Adding Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws InvalidDetailsException
     */
	
    @PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomerDetails(@RequestBody Customer customer) throws InvalidDetailsException
	{
    	Customer addedCustomer=customerServiceImplementation.addCustomer(customer);
		return new ResponseEntity<Customer>(addedCustomer,HttpStatus.OK);
	}
    
    /**
     * deleteCustomer
     * <p>
     * deleting Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws DetailsNotFoundException
     */
	
	@DeleteMapping("/deleteCustomer/{Id}")
	public boolean deleteCustomer(@PathVariable long Id) throws DetailsNotFoundException
	{
		boolean isDeleted=false;
		isDeleted=customerServiceImplementation.deleteCustomer(Id);
		return isDeleted;					
	}
	
	/**
     * updateCustomer
     * <p>
     * updating Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws InvalidDetailsException
     */
	
	@PutMapping("/updateCustomer") 
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws InvalidDetailsException
	{
		Customer updatedCustomer=customerServiceImplementation.updateCustomer(customer);
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
	}
	
	/**
     * findCustomer
     * <p>
     * Finding Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws InvalidDetailsException
     */
	
	@GetMapping("/findCustomerById/{customer_id}")
		public ResponseEntity<Customer> findCustomerById(@PathVariable long customer_id) throws InvalidDetailsException
		{
			Customer findCustomer=customerServiceImplementation.findCustomerById(customer_id);
			return new ResponseEntity<Customer>(findCustomer,HttpStatus.OK);
		}
	
	/**
     * viewCustomer
     * <p>
     * Viewing Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws DetailsNotFoundException
     */
	
	@GetMapping("/viewCustomerAccountById/{accountId}")
	    public ResponseEntity<List<Customer>> viewCustomerById(@PathVariable long accountId) throws DetailsNotFoundException
	  {
		List<Customer> viewFetchedCustomers = customerServiceImplementation.viewCustomerDetails(accountId);
		return new ResponseEntity<List<Customer>>(viewFetchedCustomers, HttpStatus.OK);

	}
	
	/**
     * listAllCustomer
     * <p>
     * list all Customer details to database
     * </p>
     * 
     * @param customer
     * @return ResponseEntity<Customer>
     * @throws EmptyListException
     */
	
	@GetMapping("/listAllCustomersByMinBalance/{minBalance}")
	public ResponseEntity<Set<Customer>> listAllTransactions(@PathVariable double minBalance) throws EmptyListException{
		
		Set<Customer> listAllCustomersSet = new HashSet<Customer>();
			listAllCustomersSet = customerServiceImplementation.listAllCustomers(minBalance);
		return new ResponseEntity<Set<Customer>>(listAllCustomersSet, HttpStatus.OK);
	}

}