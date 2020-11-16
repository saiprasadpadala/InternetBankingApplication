package com.cg.iba.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Customer;
import com.cg.iba.entities.Gender;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.CustomerRepository;

@SpringBootTest
class CustomerServiceImplementationTest {
    @Autowired
    CustomerServiceImplementation customerServiceImplementation;
    Set<Customer> customerSet = new HashSet<Customer>();
    Account account = new Account(1L,20000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")),customerSet);
    Set<Account> accountSet = new HashSet<Account>();
    Customer customer = new Customer(1,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
    
    @MockBean
    CustomerRepository customerRepository;
    
    @Test
    public void testaddCustomer() throws InvalidDetailsException{
        Set<Customer> customerSet = new HashSet<Customer>();
        customerSet.add(new Customer());
        Account account = new Account(1L,20000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")),customerSet);
        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(account);
        Customer customer = new Customer(3888,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerServiceImplementation.addCustomer(customer));
    }
    
    @Test
    public void testFindCustomer() {
        Set<Customer> customerSet = new HashSet<Customer>();
        Account account = new Account(1L,20000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")),customerSet);
        Set<Account> accountSet = new HashSet<Account>();
        Customer customer = new Customer(38488,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customer));
        assertNotNull(customer);
        assertEquals(customer, customer);
        
    }
    /*@Test
    public void testAddCustomersThrowsInvalidDetailsException() {
        Set<Customer> customerSet = new HashSet<Customer>();
        Account account = new Account(5,20000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")),customerSet);
        Set<Account> accountSet = new HashSet<Account>();
        Customer customer = new Customer(385588,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.save(customer)).thenReturn(customer);
        Assertions.assertThrows(InvalidDetailsException.class, ()-> {
            customerServiceImpl.addCustomer(customer);
        });
    }*/
    @Test
    public void testAddCustomer() throws InvalidDetailsException{
        Set<Customer> customerSet = new HashSet<Customer>();
        Account account = new Account(6,25000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")),customerSet);
        Set<Account> accountSet = new HashSet<Account>();
        Customer customer = new Customer(3855558,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer addedCustomer=null;
        addedCustomer=customerServiceImplementation.addCustomer(customer);
        assertNotNull(addedCustomer);
        assertEquals(customer, addedCustomer);
    }
    @Test
    public void testDeleteCustomerThrowsDetailsNotFoundException() {
        Customer customer = new Customer(6,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.findById((long) 6)).thenReturn(Optional.of(customer));
        Assertions.assertThrows(DetailsNotFoundException.class, () -> {
            customerServiceImplementation.deleteCustomer(4);
        });
    }
    /*@Test
    public void testDeleteCustomer() {
        when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customer));
        boolean deleteCustomer = customerServiceImpl.deleteCustomer(1);
        verify(customerRepository, times(1)).deleteById((long) 1);
        assertNotNull(deleteCustomer);
        assertEquals(true, deleteCustomer);
    }*/
    @Test
    public void testFindCustomerThrowsDetailsNotFoundException() {
        Customer customer=new Customer(1,"kumari","83593849589","kumair@gmail.com",20,Gender.FEMALE,accountSet);
        when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customer));
        Assertions.assertThrows(DetailsNotFoundException.class, () ->{
            customerServiceImplementation.findCustomerById(4);
        });
    }
    @Test
       public void testDeleteCustomer() {
           when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customer));
            boolean deleteCustomer = customerServiceImplementation.deleteCustomer(1);
            assertNotNull(deleteCustomer);
            assertEquals(true, deleteCustomer);
       }
    
    @Test
    public void testUpdateCustomer() throws InvalidDetailsException{
        when(customerRepository.findById((long) 1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer updatedCustomer = customerServiceImplementation.updateCustomer(customer);
        assertNotNull(updatedCustomer);
        assertEquals(customer, updatedCustomer);
    }
}

