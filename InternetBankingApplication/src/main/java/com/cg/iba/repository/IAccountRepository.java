package com.cg.iba.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.SavingsAccount;


public interface IAccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select * from account where account_id in (select account_id from customers_accounts where customer_id=?1)",nativeQuery = true)
    public ArrayList<Account> viewAccounts(long customerId);
    
    @Query(value = "select * from account where account_type = 'SA' and account_id in (select account_id from customers_accounts where customer_id=?1)",nativeQuery = true)
    public SavingsAccount viewSavingAcc(long customerId);
}