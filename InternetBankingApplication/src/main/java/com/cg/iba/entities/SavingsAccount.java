package com.cg.iba.entities;

public class SavingsAccount extends Account{

    private double minBalance;
    private double fine;
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