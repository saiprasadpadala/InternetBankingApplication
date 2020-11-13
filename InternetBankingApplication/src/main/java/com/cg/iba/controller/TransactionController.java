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
import org.springframework.web.bind.annotation.RestController;
import com.cg.iba.entities.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.TransactionServiceImplementation;

@RestController
@RequestMapping("/Transactions")
public class TransactionController {

    @Autowired
    TransactionServiceImplementation transactionServiceImplementation;

    /**
     * createTransaction.
     * <p>
     * this method takes transaction object as parameter without transaction id and
     * returns transaction object after saving in database with id if the transactions details are valid else
     * throws exception
     * </p>
     * 
     * @param transaction
     * @return ResponseEntity<Transaction>
     * @throws InvalidDetailsException
     */
    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws InvalidDetailsException {
        Transaction createdTransaction = transactionServiceImplementation.createTransaction(transaction);
        return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.OK);
    }

    /**
     * viewTransaction
     * <p>
     * this method takes transaction id as parameter and returns the transaction
     * object if the transaction id is valid else throws exception
     * </p>
     * 
     * @param transactionId
     * @return ResponseEntity<Transaction>
     * @throws DetailsNotFoundException
     */
    @GetMapping("/viewTransaction/{transactionId}")
    public ResponseEntity<Transaction> viewTransaction(@PathVariable long transactionId) throws DetailsNotFoundException {
        Transaction viewCreatedTransaction = null;
        viewCreatedTransaction = transactionServiceImplementation.viewTransaction(transactionId);
        return new ResponseEntity<Transaction>(viewCreatedTransaction, HttpStatus.OK);
    }

    /**
     * viewTransactionById
     * <p>
     * this method takes transaction id as parameter and returns the transaction
     * object if the transaction id is valid else throws exception
     * </p>
     * 
     * @param transactionId
     * @return ResponseEntity<Transaction>
     * @throws DetailsNotFoundException
     */
    @GetMapping("/findTransactionById/{transactionId}")
    public ResponseEntity<Transaction> findTransactionById(@PathVariable long transactionId) throws DetailsNotFoundException {
        Transaction viewFetchedTransactionById = null;
        viewFetchedTransactionById = transactionServiceImplementation.findTransactionById(transactionId);
        return new ResponseEntity<Transaction>(viewFetchedTransactionById, HttpStatus.OK);
    }

    /**
     * listAllTransactions
     * <p>
     * this method takes account id , from and to dates as parameter and validates
     * account id, if the account id is valid, searches for list of transactions
     * between from and to dates, if found returns the list of transactions else
     * throws appropriate exceptions
     * </p>
     * 
     * @param accountId
     * @param from
     * @param to
     * @return ResponseEntity<Set<Transaction>>
     * @throws InvalidAccountException
     * @throws EmptyListException
     */
    @GetMapping("/listAllTransactions/{accountId}/{from}/{to}")
    public ResponseEntity<Set<Transaction>> listAllTransactions(@PathVariable("accountId") long accountId,
            @PathVariable("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to)
            throws InvalidAccountException, EmptyListException {
        Set<Transaction> listAllTransactionsSet = new HashSet<Transaction>();
        listAllTransactionsSet = transactionServiceImplementation.listAllTransactions(accountId, from, to);
        return new ResponseEntity<Set<Transaction>>(listAllTransactionsSet, HttpStatus.OK);
    }

    /**
     * getAllMyAccTransactions
     * <p>
     * this method takes account id as parameter and validates account id, if the
     * account id is valid, searches for list of transactions for the given account
     * id, if found returns the list of transactions else throws appropriate
     * exceptions
     * </p>
     * 
     * @param accountId
     * @return ResponseEntity<Set<Transaction>>
     * @throws InvalidAccountException
     * @throws EmptyListException
     */
    @GetMapping("/getAllMyAccTransactions/{accountId}")
    public ResponseEntity<Set<Transaction>> getAllMyAccTransactions(@PathVariable long accountId) throws InvalidAccountException, EmptyListException {
        Set<Transaction> allAccountTransactionsSet = new HashSet<Transaction>();
        allAccountTransactionsSet = transactionServiceImplementation.getAllMyAccTransactions(accountId);
        return new ResponseEntity<Set<Transaction>>(allAccountTransactionsSet, HttpStatus.OK);
    }
} 