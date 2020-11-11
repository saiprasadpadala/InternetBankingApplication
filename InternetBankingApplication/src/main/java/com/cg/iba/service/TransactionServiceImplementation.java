package com.cg.iba.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.iba.entities.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.ITransactionRepository;

@Service
public class TransactionServiceImplementation implements ITransactionService{
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Override
	public Transaction createTransaction(Transaction transaction) throws InvalidDetailsException {
		if(transaction.getAmount()<0) {
			throw new InvalidDetailsException("Invalid Transaction details. Amount should be >0");
		}
		else {
			return transactionRepository.save(transaction);
		}
	}

	@Override
	public Transaction viewTransaction(long transaction_id) throws DetailsNotFoundException {
		Transaction retrivedTransaction = transactionRepository.findById(transaction_id).orElse(null);
		if(retrivedTransaction==null) {
			throw new DetailsNotFoundException("No transaction found with id "+transaction_id+" to fetch");
		}
		else {
			return retrivedTransaction;
		}
	}

	@Override
	public Transaction findTransactionById(long transaction_id) throws DetailsNotFoundException {
		Transaction retrivedTransaction = transactionRepository.findById(transaction_id).orElse(null);
		if(retrivedTransaction==null) {
			throw new DetailsNotFoundException("No transaction found with id "+transaction_id+" to fetch");
		}
		else {
			return retrivedTransaction;
		}
	}

	@Override
	public Set<Transaction> listAllTransactions(long accountId, LocalDate from, LocalDate to)
			throws InvalidAccountException, EmptyListException {
		/*need to verify accountId, if account id is valid then get all transactions 
		 * else throw InvalidAccountException. (use findAccountById() method in IAccountService)*/
		//if accountId is valid then
		
		List<Transaction> transactionsBetweenDatesList = new ArrayList<Transaction>();
		transactionsBetweenDatesList = transactionRepository.listAllTransactions(accountId, from, to);
		if(transactionsBetweenDatesList.isEmpty()) {
			throw new EmptyListException("No Transactions found between "+from+" and "+to+" for account with id "+accountId);
		}
		else {
			Set<Transaction> transactionsBetweenDatesSet = new HashSet<Transaction>();
			for(Transaction transaction : transactionsBetweenDatesList) {
				transactionsBetweenDatesSet.add(transaction);
			}
			return transactionsBetweenDatesSet;
		}
	}

	@Override
	public Set<Transaction> getAllMyAccTransactions(long accountId) throws InvalidAccountException, EmptyListException {		
		/*need to verify accountId, if account id is valid then get all transactions 
		 * else throw InvalidAccountException. (use findAccountById() method in IAccountService)*/
		//if accountId is valid then
		
		List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
		allAccountTransactionsList = transactionRepository.getAllMyAccTransactions(accountId);
		
		if(allAccountTransactionsList.isEmpty()) {
			throw new EmptyListException("No Transactions found for account with id "+accountId);
		}
		else {
			Set<Transaction> allAccountTransactionsSet = new HashSet<Transaction>();
			for(Transaction transaction : allAccountTransactionsList) {
				allAccountTransactionsSet.add(transaction);
			}
			return allAccountTransactionsSet;
		}
	}
}