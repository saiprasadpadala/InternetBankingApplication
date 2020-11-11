package com.cg.iba;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Transaction;
import com.cg.iba.entities.TransactionStatus;
import com.cg.iba.entities.TransactionType;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.service.TransactionServiceImplementation;

@SpringBootTest
class InternetBankingApplicationTests {
	
	@Autowired
	private TransactionServiceImplementation transactionServiceImplementation;
	
	@MockBean
	private ITransactionRepository transactionRepository;
	
	Account account = new Account(1,20000.0,3.4,LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));
	
	Transaction transaction = new Transaction(3,1000.0,TransactionType.DEBIT,LocalDateTime.parse("2010-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ,account,TransactionStatus.SUCCESSFUL,"For Movie Tickets");
	Transaction transaction1 = new Transaction(4,546.0,TransactionType.CREDIT,LocalDateTime.parse("2010-11-10 11:30:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ,account,TransactionStatus.SUCCESSFUL,"From friend");
	
	Account account1 = new Account(2,45690,7.4,LocalDate.parse("2015-04-14", DateTimeFormatter.ofPattern("yyyy-MM-d")));
	
	Transaction transaction2 = new Transaction(5,789,TransactionType.DEBIT,LocalDateTime.parse("2020-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ,account1,TransactionStatus.FAILED,"At Supermarket");
	Transaction transaction3 = new Transaction(6,789,TransactionType.DEBIT,LocalDateTime.parse("2020-11-19 13:36:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ,account1,TransactionStatus.SUCCESSFUL,"At Supermarket");	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testCreateTransaction() throws InvalidDetailsException {
		when(transactionRepository.save(transaction)).thenReturn(transaction);
		Transaction createdTransaction=null;
		createdTransaction = transactionServiceImplementation.createTransaction(transaction);
		
		assertNotNull(createdTransaction);
		assertEquals(transaction, createdTransaction);
	}
	
	@Test
	public void testCreateTransactionThrowsException() throws InvalidDetailsException  {
		Transaction transactions = new Transaction(3,0,TransactionType.DEBIT,LocalDateTime.parse("2010-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ,account,TransactionStatus.SUCCESSFUL,"For Movie Tickets");
		when(transactionRepository.save(transaction)).thenThrow(InvalidDetailsException.class);
		//assertThrows(InvalidDetailsException.class,()->transactionServiceImplementation.createTransaction(transactions));
		assertEquals(transactions, transactionServiceImplementation.createTransaction(transactions));
	}
	
	
	@Test
	public void testViewTransaction() {
		when(transactionRepository.findById((long) 1)).thenReturn(Optional.of(transaction));
		Transaction fetchedTransaction = transactionServiceImplementation.viewTransaction(1);
		assertNotNull(fetchedTransaction);
		assertEquals(transaction, fetchedTransaction);
	}
	
	@Test
	public void testFindTransactionById() {	
		when(transactionRepository.findById((long) 1)).thenReturn(Optional.of(transaction));
		Transaction fetchedTransaction = transactionServiceImplementation.viewTransaction(1);
		assertNotNull(fetchedTransaction);
		assertEquals(transaction, fetchedTransaction);
	}
	
	@Test
	public void testListAllTransactions() throws InvalidAccountException, EmptyListException {
		List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
		allAccountTransactionsList.add(transaction2);
		allAccountTransactionsList.add(transaction3);
		
		LocalDate from = LocalDate.parse("2020-11-06", DateTimeFormatter.ofPattern("yyyy-MM-d"));
		LocalDate to = LocalDate.parse("2020-11-11", DateTimeFormatter.ofPattern("yyyy-MM-d"));
		
		when(transactionRepository.listAllTransactions(2,from,to)).thenReturn(allAccountTransactionsList);
		Set<Transaction> allAccountTransactionsSet =  transactionServiceImplementation.listAllTransactions(2, from, to);
		assertNotNull(allAccountTransactionsSet);
		assertEquals(allAccountTransactionsSet.size(),allAccountTransactionsList.size());
	}
	
	@Test
	public void testGetAllMyAccTransactions() throws InvalidAccountException, EmptyListException {
		List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
		allAccountTransactionsList.add(transaction);
		allAccountTransactionsList.add(transaction1);
		when(transactionRepository.getAllMyAccTransactions(1)).thenReturn(allAccountTransactionsList);
		Set<Transaction> allAccountTransactionsSet =  transactionServiceImplementation.getAllMyAccTransactions(1);
		assertNotNull(allAccountTransactionsSet);
		assertEquals(allAccountTransactionsSet.size(),allAccountTransactionsList.size());
	}
	
}