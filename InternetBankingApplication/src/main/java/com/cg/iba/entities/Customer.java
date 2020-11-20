package com.cg.iba.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId; 
    private String customerName;
    private String phoneNo;
    private String emailId;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    //using many to many mapping
    @ManyToMany
    @JoinTable(name="customers_accounts",joinColumns=@JoinColumn(name="customerId"),
    inverseJoinColumns=@JoinColumn(name="accountId"))
    private Set<Account> accounts=new HashSet<>();
    
    //default constructor
    public Customer() {
        super();
    
    }
    
    public Customer(long customerId, String customerName, String phoneNo, String emailId, int age, Gender gender) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.age = age;
        this.gender = gender;
    }

    //parameterized constructor
    public Customer(long customerId, String customerName, String phoneNo, String emailId, int age, Gender gender, Set<Account> accounts) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.age = age;
        this.gender = gender;
        this.accounts = accounts;
    }
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Set<Account> getAccounts(){
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    

}
