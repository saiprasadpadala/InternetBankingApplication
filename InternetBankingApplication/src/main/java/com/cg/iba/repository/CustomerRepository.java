package com.cg.iba.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query(value="select customer_id,age,customer_name,email_id,gender,phone_no from Customer where customer_id in (select customer_id from customers_accounts where account_id in (select account_id from account where min_balance=?1) )",nativeQuery=true)                                                      
	List<Customer> listAllcustomer(double minBalance);
	
	@Query(value="select * from Customer where customer_id in(select customer_id from customers_accounts where account_id=?1)",nativeQuery=true)
	public List<Customer> viewCustomerDetails(long account_id);

}
