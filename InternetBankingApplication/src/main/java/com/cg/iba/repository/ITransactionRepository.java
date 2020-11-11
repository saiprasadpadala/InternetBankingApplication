package com.cg.iba.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction,Long>{

    //public Transaction createTransaction(Transaction transaction) throws InvalidDetailsException;
	//public Transaction viewTransaction(long transaction_id) throws DetailsNotFoundException;
	//public Transaction findTransactionById(long transaction_id) throws DetailsNotFoundException;
	
	@Query("select t from Transaction t where date_time Between ?2 AND ?3 AND account_id = ?1")
	public  List<Transaction> listAllTransactions(long accountId, LocalDate from, LocalDate to); //throws EmptyListException;
	@Query("select t from Transaction t where account_id = ?1")
	public  List<Transaction> getAllMyAccTransactions(long account_id); //throws InvalidAccountException,EmptyListException;
}
