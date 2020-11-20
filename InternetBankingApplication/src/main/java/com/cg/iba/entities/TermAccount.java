package com.cg.iba.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( value="TA" )
public class TermAccount extends Account{

    private double amount;
    private int months; 
    private double penaltyAmount;
    
	public TermAccount() {
        super();
    }
	
    public TermAccount(long accounId, double interestRate, double balance, LocalDate dateOfOpening, double amount, int months, double penaltyAmount) {
        super(accounId, interestRate, balance, dateOfOpening);
        this.amount = amount;
        this.months = months;
        this.penaltyAmount = penaltyAmount;
    }
   
    public TermAccount(long accounId, double interestRate, double balance, LocalDate dateOfOpening, Set<Customer> customers, Set<Nominee> nominees, Set<Beneficiary> beneficiaries,
            double amount, int months, double penaltyAmount) {
        super(accounId, interestRate, balance, dateOfOpening, customers, nominees, beneficiaries);
        this.amount = amount;
        this.months = months;
        this.penaltyAmount = penaltyAmount;
    }

    public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public double getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	} 

    
}
