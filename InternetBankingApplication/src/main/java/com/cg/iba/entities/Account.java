package com.cg.iba.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "AccountType" )
public class Account {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
	private long accountId;
	private double interestRate;
	private double balance;
	private LocalDate dateOfOpening;
	
	@ManyToMany(mappedBy = "accounts",cascade=CascadeType.ALL)
	private Set<Customer> customers;
	
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
	private Set<Nominee> nominees;
	
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
	private Set<Beneficiary> beneficiaries;
	
	public Account() {
        super();
    }
	
    public Account(long accountId, double interestRate, double balance, LocalDate dateOfOpening) {
        super();
        this.accountId = accountId;
        this.interestRate = interestRate;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
    }

    public Account(long accountId, double interestRate, double balance, LocalDate dateOfOpening, Set<Customer> customers, Set<Nominee> nominees, Set<Beneficiary> beneficiaries) {
        super();
        this.accountId = accountId;
        this.interestRate = interestRate;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.customers = customers;
        this.nominees = nominees;
        this.beneficiaries = beneficiaries;
    }
   
    public long getAccountId() {
		return accountId;
	}

	public void setAccounId(long accounId) {
		this.accountId = accounId;
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

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public Set<Nominee> getNominees() {
		return nominees;
	}

	public void setNominees(Set<Nominee> nominees) {
		this.nominees = nominees;
	}

	public Set<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
}