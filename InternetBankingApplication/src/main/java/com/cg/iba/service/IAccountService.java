package com.cg.iba.service;

import java.util.Set;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.SavingsAccount;
import com.cg.iba.entities.TermAccount;
import com.cg.iba.entities.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;

public interface IAccountService {
	//changed username datatype to long
	public Transaction transferMoney(long senderAccounId, long receiverAccountId, double amount,
			long username, String password) throws LowBalanceException, InvalidAccountException, InvalidDetailsException;
	//changed username datatype to long and added double amount as field 
	public Transaction withdraw(long accounId, double amount, long username, String password) throws LowBalanceException, InvalidDetailsException;
	
	public Transaction deposit(long accounId, double amount) throws InvalidAccountException,InvalidAmountException, InvalidDetailsException;
	
	public SavingsAccount addSavingsAccount(SavingsAccount saving) throws InvalidDetailsException;
	
	public  TermAccount addTermAccount(TermAccount term) throws InvalidDetailsException;
	
	public SavingsAccount updateSavingsAccount(SavingsAccount saving) throws InvalidDetailsException;
	
	public  TermAccount updateTermAccount(TermAccount term) throws InvalidDetailsException;
	
	public  boolean closeSavingsAccount(SavingsAccount accountNo) throws InvalidAccountException;
	
	public  boolean closeTermAccount(TermAccount accountNo) throws InvalidAccountException;
	
	public  Account findAccountById(int account_id) throws InvalidAccountException;
	
	public Set<Account> viewAccounts(long customerId) throws DetailsNotFoundException;
	
	public SavingsAccount viewSavingAcc(long customerId) throws DetailsNotFoundException;
	
	public TermAccount viewTermAcc(long customerId) throws DetailsNotFoundException;

}
