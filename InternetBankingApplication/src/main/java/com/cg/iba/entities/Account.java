package com.cg.iba.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    private double interestRate;
    private double balance;
    private LocalDate dateOfOpening;
    
    /**
     * default Constructor
     */
    public Account() {
        super();
    }

    /**
     * Parameterized Constructor
     * 
     * @param accountId
     * @param interestRate
     * @param balance
     * @param dateOfOpening
     */
    public Account(long accountId, double interestRate, double balance, LocalDate dateOfOpening) {
        super();
        this.accountId = accountId;
        this.interestRate = interestRate;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
    }

    /*
     * getters and setters for private fields
     */
    public long getAccountId() {
        return accountId;
    } 

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(LocalDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }
}
