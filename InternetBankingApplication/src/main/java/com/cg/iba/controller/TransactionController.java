package com.cg.iba.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.TransactionServiceImplementation;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionServiceImplementation transactionServiceImplementation;
	
	@PostMapping("/createTransaction")
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws InvalidDetailsException{
		
		//try {
			Transaction createdTransaction = transactionServiceImplementation.createTransaction(transaction);
			return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.OK);
			
		//} catch (InvalidDetailsException e) {
			//e.printStackTrace();
		//}
		//return null;
	}
	
	@GetMapping("/viewTransaction/{transactionId}")
	public ResponseEntity<Transaction> viewTransaction(@PathVariable long transactionId) throws DetailsNotFoundException{
		
		Transaction viewCreatedTransaction = null;
		//try {
			viewCreatedTransaction = transactionServiceImplementation.viewTransaction(transactionId);
			return new ResponseEntity<Transaction>(viewCreatedTransaction, HttpStatus.OK);
		//} catch (DetailsNotFoundException e) {
		//	e.printStackTrace();
		//}
		//return null;
	}
	
	@GetMapping("/viewTransactionById/{transactionId}")
	public ResponseEntity<Transaction> viewTransactionById(@PathVariable long transactionId) throws DetailsNotFoundException{
		
		Transaction viewFetchedTransactionById = null;
		//try {
			viewFetchedTransactionById = transactionServiceImplementation.findTransactionById(transactionId);
		//} catch (DetailsNotFoundException e) {
			//e.printStackTrace();
		//}
		return new ResponseEntity<Transaction>(viewFetchedTransactionById, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/listAllTransactions/{accountId}/{from}/{to}", method=RequestMethod.GET)
	//@GetMapping("/listAllTransactions/{accountId}/{from}/{to}")
	public Set<Transaction> listAllTransactions(@PathVariable("accountId") long accountId, @PathVariable("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) throws InvalidAccountException, EmptyListException{
		
		Set<Transaction> listAllTransactionsSet = new HashSet<Transaction>();
		//try {
			listAllTransactionsSet = transactionServiceImplementation.listAllTransactions(accountId, from, to);
		//} catch (InvalidAccountException | EmptyListException e) {
		//	e.printStackTrace();
		//}
		
		return listAllTransactionsSet;
	}
	
	@GetMapping("/getAllMyAccTransactions/{accountId}")
	public Set<Transaction> getAllMyAccTransactions(@PathVariable long accountId) throws InvalidAccountException, EmptyListException{
		
		Set<Transaction> allAccountTransactionsSet = new HashSet<Transaction>();
		//try {
			allAccountTransactionsSet = transactionServiceImplementation.getAllMyAccTransactions(accountId);
		//} catch (InvalidAccountException | EmptyListException e) {
		//	e.printStackTrace();
		//}
		return allAccountTransactionsSet;
	}
}