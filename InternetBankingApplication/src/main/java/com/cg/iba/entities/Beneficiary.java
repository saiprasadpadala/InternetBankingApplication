package com.cg.iba.entities;

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
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long  beneficiaryId;
	private String beneficiaryName;
	private long  beneficiaryAccNo;
	private String IFSC;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account bankAccount;
    public Beneficiary() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public Beneficiary(long beneficiaryId, String beneficiaryName, long beneficiaryAccNo, String iFSC, AccountType accountType) {
        super();
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccNo = beneficiaryAccNo;
        IFSC = iFSC;
        this.accountType = accountType;
    }

    public Beneficiary(long beneficiaryId, String beneficiaryName, long beneficiaryAccNo, String iFSC, AccountType accountType, Account bankAccount) {
        super();
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccNo = beneficiaryAccNo;
        IFSC = iFSC;
        this.accountType = accountType;
        this.bankAccount = bankAccount;
    }

    public long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public long getBeneficiaryAccNo() {
        return beneficiaryAccNo;
    }

    public void setBeneficiaryAccNo(long beneficiaryAccNo) {
        this.beneficiaryAccNo = beneficiaryAccNo;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String iFSC) {
        IFSC = iFSC;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Account getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
    }
    
}
