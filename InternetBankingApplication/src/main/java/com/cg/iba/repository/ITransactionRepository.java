package com.cg.iba.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    /*
     * this method takes account id, from and to dates as parameter and fetches all
     * the transactions for the given account id and returns them as list, if no
     * transactions found then returns empty list
     */
    @Query("select t from Transaction t where date_time Between ?2 AND ?3 AND account_id = ?1")
    public List<Transaction> listAllTransactions(long accountId, LocalDate from, LocalDate to);

    /*
     * this method takes account id as parameter and fetches all the transactions
     * for the given account id and returns them as list, if no transactions found
     * then returns empty list
     */
    @Query("select t from Transaction t where account_id = ?1")
    public List<Transaction> getAllMyAccTransactions(long account_id);
}
