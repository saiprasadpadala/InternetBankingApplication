package com.cg.iba.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime dateTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account bankAccount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String transactionRemarks;

    /**
     * Default Constructor
     */
    public Transaction() {
        super();
    }

    /**
     * Parameterized Constructor
     * 
     * @param transactionId
     * @param amount
     * @param transactionType
     * @param dateTime
     * @param bankAccount
     * @param transactionStatus
     * @param transactionRemarks
     */
    public Transaction(long transactionId, double amount, TransactionType transactionType, LocalDateTime dateTime, Account bankAccount, TransactionStatus transactionStatus,
            String transactionRemarks) {
        super();
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.dateTime = dateTime;
        this.bankAccount = bankAccount;
        this.transactionStatus = transactionStatus;
        this.transactionRemarks = transactionRemarks;
    }

    /*
     * getters and setters for private fields
     */
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    } 

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Account getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionRemarks() {
        return transactionRemarks;
    }

    public void setTransactionRemarks(String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }
}
