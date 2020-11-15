package com.cg.iba.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Transaction;
import com.cg.iba.entities.TransactionStatus;
import com.cg.iba.entities.TransactionType;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.ITransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplementationTest {

    @Mock 
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImplementation transactionServiceImplementation;

    Account accountFirst = new Account(1, 20000.0, 3.4, LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));

    Transaction transactionFirst = new Transaction(3, 1000.0, TransactionType.DEBIT, LocalDateTime.parse("2010-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            accountFirst, TransactionStatus.SUCCESSFUL, "For Movie Tickets");

    Transaction transactionSecond = new Transaction(4, 546.0, TransactionType.CREDIT,
            LocalDateTime.parse("2010-11-10 11:30:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), accountFirst, TransactionStatus.SUCCESSFUL, "From friend");

    Account accountSecond = new Account(2, 45690, 7.4, LocalDate.parse("2015-04-14", DateTimeFormatter.ofPattern("yyyy-MM-d")));

    Transaction transactionThird = new Transaction(5, 789, TransactionType.DEBIT, LocalDateTime.parse("2020-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            accountSecond, TransactionStatus.FAILED, "At Supermarket");
    Transaction transactionFourth = new Transaction(6, 789, TransactionType.DEBIT, LocalDateTime.parse("2020-11-19 13:36:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            accountSecond, TransactionStatus.SUCCESSFUL, "At KFC");

    @Test
    void testCreateTransaction() throws InvalidDetailsException {
        Account createAccount = new Account();
        createAccount.setAccountId(1);
        createAccount.setBalance(20000.0);
        createAccount.setInterestRate(3.4);
        createAccount.setDateOfOpening(LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));
        Transaction createTransaction = new Transaction();
        createTransaction.setTransactionId(3);
        createTransaction.setAmount(10000);
        createTransaction.setTransactionType(TransactionType.DEBIT);
        createTransaction.setDateTime(LocalDateTime.parse("2010-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        createTransaction.setBankAccount(createAccount);
        createTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        createTransaction.setTransactionRemarks("For Movie Tickets");
        when(transactionRepository.save(createTransaction)).thenReturn(createTransaction);
        Transaction createdTransaction = null;
        createdTransaction = transactionServiceImplementation.createTransaction(createTransaction);

        assertNotNull(createdTransaction);

        assertEquals(createTransaction.getTransactionId(), createdTransaction.getTransactionId());
        assertEquals(createTransaction.getAmount(), createdTransaction.getAmount());
        assertEquals(createTransaction.getTransactionType(), createdTransaction.getTransactionType());
        assertEquals(createTransaction.getDateTime(), createdTransaction.getDateTime());

        assertEquals(createTransaction.getBankAccount().getAccountId(), createdTransaction.getBankAccount().getAccountId());
        assertEquals(createTransaction.getBankAccount().getBalance(), createdTransaction.getBankAccount().getBalance());
        assertEquals(createTransaction.getBankAccount().getDateOfOpening(), createdTransaction.getBankAccount().getDateOfOpening());
        assertEquals(createTransaction.getBankAccount().getInterestRate(), createdTransaction.getBankAccount().getInterestRate());

        assertEquals(createTransaction.getTransactionStatus(), createdTransaction.getTransactionStatus());
        assertEquals(createTransaction.getTransactionRemarks(), createdTransaction.getTransactionRemarks());
    }

    @Test
    public void testCreateTransaction_Throws_InvalidDetailsException() {
        Transaction transactions = new Transaction(3, -10, TransactionType.DEBIT, LocalDateTime.parse("2010-11-09 13:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                accountFirst, TransactionStatus.SUCCESSFUL, "For Movie Tickets");
        Assertions.assertThrows(InvalidDetailsException.class, () -> {
            transactionServiceImplementation.createTransaction(transactions);
        });
    }

      @Test 
      public void testViewTransaction() { 
          when(transactionRepository.findById(transactionFirst.getTransactionId())).thenReturn(Optional.of(transactionFirst));
          Transaction fetchedTransaction = transactionServiceImplementation.viewTransaction(3);
          assertNotNull(fetchedTransaction);
          assertEquals(transactionFirst, fetchedTransaction);
      }
      
      @Test
      public void testViewTransaction_Throws_DetailsNotFoundException() {
          Assertions.assertThrows(DetailsNotFoundException.class, () -> {
              transactionServiceImplementation.viewTransaction(transactionFirst.getTransactionId());
          });
      }
      
      @Test 
      public void testFindTransactionById() { 
          when(transactionRepository.findById(transactionFirst.getTransactionId())).thenReturn(Optional.of(transactionFirst));
          Transaction fetchedTransaction = transactionServiceImplementation.findTransactionById(3);
          assertNotNull(fetchedTransaction);
          assertEquals(transactionFirst, fetchedTransaction);
      }
      
      @Test
      public void testFindTransactionById_Throws_DetailsNotFoundException() {
          Assertions.assertThrows(DetailsNotFoundException.class, () -> {
              transactionServiceImplementation.findTransactionById(transactionFirst.getTransactionId());
          });
      }
      
      @Test 
      public  void testListAllTransactions() { 
          List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
          allAccountTransactionsList.add(transactionThird);
          allAccountTransactionsList.add(transactionFourth);

          LocalDate from = LocalDate.parse("2020-11-06", DateTimeFormatter.ofPattern("yyyy-MM-d"));
          LocalDate to = LocalDate.parse("2020-11-11", DateTimeFormatter.ofPattern("yyyy-MM-d"));

          when(transactionRepository.listAllTransactions(2, from, to)).thenReturn(allAccountTransactionsList);
          Set<Transaction> allAccountTransactionsSet = transactionServiceImplementation.listAllTransactions(2, from, to);
          assertNotNull(allAccountTransactionsSet);
          assertEquals(allAccountTransactionsSet.size(), allAccountTransactionsList.size());
      }
      
      @Test
      public void testListAllTransactions_Throws_EmptyListException() throws InvalidAccountException, EmptyListException {
          List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
          LocalDate from = LocalDate.parse("2020-11-06", DateTimeFormatter.ofPattern("yyyy-MM-d"));
          LocalDate to = LocalDate.parse("2020-11-11", DateTimeFormatter.ofPattern("yyyy-MM-d"));
          when(transactionRepository.listAllTransactions(2, from, to)).thenReturn(allAccountTransactionsList);
          Assertions.assertThrows(EmptyListException.class, () -> {
              transactionServiceImplementation.listAllTransactions(2, from, to);
          });
      }
       
      @Test void testGetAllMyAccTransactions() { 
          List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
          allAccountTransactionsList.add(transactionFirst);
          allAccountTransactionsList.add(transactionSecond);
          when(transactionRepository.getAllMyAccTransactions(1)).thenReturn(allAccountTransactionsList);
          Set<Transaction> allAccountTransactionsSet = transactionServiceImplementation.getAllMyAccTransactions(1);
          assertNotNull(allAccountTransactionsSet);
          assertEquals(allAccountTransactionsSet.size(), allAccountTransactionsList.size());
      }
      @Test
      public void testGetAllMyAccTransactions_Throws_EmptyListException() {
          List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
          when(transactionRepository.getAllMyAccTransactions(1)).thenReturn(allAccountTransactionsList);
          Assertions.assertThrows(EmptyListException.class, () -> {
              transactionServiceImplementation.getAllMyAccTransactions(1);
          });
      }
}
