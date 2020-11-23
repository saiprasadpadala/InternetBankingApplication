package com.cg.iba.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.SavingsAccount;
import com.cg.iba.entities.TermAccount;


public interface IAccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select * from account where account_id in (select account_id from customers_accounts where customer_id=?1)",nativeQuery = true)
    public ArrayList<Account> viewAccounts(long customerId);
    //Changed the return type to list a customer can have more than one saving account
    @Query(value = "select * from account where account_type = 'SA' and account_id in (select account_id from customers_accounts where customer_id=?1)",nativeQuery = true)
    public List<SavingsAccount> viewSavingAcc(long customerId);
    
  //Changed the return type to list a customer can have more than one term account
    @Query(value = "select * from account where account_type = 'TA' and account_id in (select account_id from customers_accounts where customer_id=?1)",nativeQuery = true)
    public List<TermAccount> viewTermAcc(long customerId);
    
}