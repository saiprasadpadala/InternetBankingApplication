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
public class TransactionServiceImplementation implements ITransactionService {

    @Autowired
    ITransactionRepository transactionRepository;

    /**
     * createTransaction.
     * <p>
     * this method takes transaction object as parameter without transaction id and
     * returns transaction object after saving in database with id if the
     * transaction amount is valid else throws exception
     * </p>
     * 
     * @param transaction
     * @return Transaction
     * @throws InvalidDetailsException
     */
    @Override
    public Transaction createTransaction(Transaction transaction) throws InvalidDetailsException {
        if (transaction.getAmount() <= 0) {
            throw new InvalidDetailsException("Invalid Transaction details. Amount should be >0");
        } else {
            return transactionRepository.save(transaction);
        }
    }

    /**
     * viewTransaction
     * <p>
     * this method takes transaction id as parameter and returns the transaction
     * object if the transaction id is valid else throws exception
     * </p>
     * 
     * @param transaction_id
     * @return Transaction
     * @throws DetailsNotFoundException
     */
    @Override
    public Transaction viewTransaction(long transaction_id) throws DetailsNotFoundException {
        Transaction retrivedTransaction = transactionRepository.findById(transaction_id).orElse(new Transaction());
        if (retrivedTransaction.getTransactionId() != transaction_id) {
            throw new DetailsNotFoundException("No transaction found with id " + transaction_id + " to view");
        } else {
            return retrivedTransaction;
        }
    }

    /**
     * findTransactionById
     * <p>
     * this method takes transaction id as parameter and returns the transaction
     * object if the transaction id is valid else throws exception
     * </p>
     * 
     * @param transaction_id
     * @return Transaction
     * @throws DetailsNotFoundException
     */
    @Override
    public Transaction findTransactionById(long transaction_id) throws DetailsNotFoundException {
        Transaction retrivedTransaction = transactionRepository.findById(transaction_id).orElse(new Transaction());
        if (retrivedTransaction.getTransactionId() != transaction_id) {
            throw new DetailsNotFoundException("No transaction found with id " + transaction_id + " to fetch");
        } else {
            return retrivedTransaction;
        }
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
     * @return Set<Transaction>
     * @throws InvalidAccountException
     * @throws EmptyListException
     */
    @Override
    public Set<Transaction> listAllTransactions(long accountId, LocalDate from, LocalDate to) throws InvalidAccountException, EmptyListException {
        /*
         * need to verify accountId, if account id is valid then get all transactions
         * else throw InvalidAccountException. (use findAccountById() method in
         * IAccountService) if account id is valid then
         */

        List<Transaction> transactionsBetweenDatesList = new ArrayList<Transaction>();
        transactionsBetweenDatesList = transactionRepository.listAllTransactions(accountId, from, to);
        if (transactionsBetweenDatesList.isEmpty()) {
            throw new EmptyListException("No Transactions found between " + from + " and " + to + " for account with id " + accountId);
        } else {
            Set<Transaction> transactionsBetweenDatesSet = new HashSet<Transaction>();
            transactionsBetweenDatesSet.addAll(transactionsBetweenDatesList);
            return transactionsBetweenDatesSet;
        }
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
     * @return Set<Transaction>
     * @throws InvalidAccountException
     * @throws EmptyListException
     */
    @Override
    public Set<Transaction> getAllMyAccTransactions(long accountId) throws InvalidAccountException, EmptyListException {
        /*
         * need to verify accountId, if account id is valid then get all transactions
         * else throw InvalidAccountException. (use findAccountById() method in
         * IAccountService) if accountId is valid then
         */

        List<Transaction> allAccountTransactionsList = new ArrayList<Transaction>();
        allAccountTransactionsList = transactionRepository.getAllMyAccTransactions(accountId);

        if (allAccountTransactionsList.isEmpty()) {
            throw new EmptyListException("No Transactions found for account with id " + accountId);
        } else {
            Set<Transaction> allAccountTransactionsSet = new HashSet<Transaction>();
            allAccountTransactionsSet.addAll(allAccountTransactionsList);
            return allAccountTransactionsSet;
        }
    }
}