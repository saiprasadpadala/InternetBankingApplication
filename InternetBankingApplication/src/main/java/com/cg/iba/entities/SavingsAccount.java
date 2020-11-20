package com.cg.iba.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( value="SA" )
public class SavingsAccount extends Account{
    private double minBalance;
    private double fine;
    
    public SavingsAccount() {
        super();
    }
    
    public SavingsAccount(long accounId, double interestRate, double balance, LocalDate dateOfOpening, double minBalance, double fine) {
        super(accounId, interestRate, balance, dateOfOpening);
        this.minBalance = minBalance;
        this.fine = fine;
    }
    
    public SavingsAccount(long accounId, double interestRate, double balance, LocalDate dateOfOpening, Set<Customer> customers, Set<Nominee> nominees,
            Set<Beneficiary> beneficiaries, double minBalance, double fine) {
        super(accounId, interestRate, balance, dateOfOpening, customers, nominees, beneficiaries);
        this.minBalance = minBalance;
        this.fine = fine;
    }
    
	public double getMinBalance() {
		return minBalance;
	}
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}
	public double getFine() {
		return fine;
	}
	public void setFine(double fine) {
		this.fine = fine;
	} 

}
