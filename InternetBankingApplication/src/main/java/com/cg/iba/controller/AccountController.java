package com.cg.iba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.SavingsAccount;
import com.cg.iba.entities.TermAccount;
import com.cg.iba.entities.Transaction;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.service.AccountServiceImplementation;

@RestController
@RequestMapping("/Accounts")
public class AccountController {
    @Autowired
    private AccountServiceImplementation accountServiceImplementation;
    
    @PostMapping("/transferMoney/{senderAccounId}/{receiverAccountId}/{amount}/{username}/{password}")
    public ResponseEntity<Transaction> transferMoney(@PathVariable long senderAccounId, @PathVariable long receiverAccountId,@PathVariable double amount,@PathVariable long username,@PathVariable String password) throws LowBalanceException, InvalidAccountException, InvalidDetailsException{
        Transaction transferMoneyTransaction = accountServiceImplementation.transferMoney(senderAccounId, receiverAccountId, amount, username, password);
        return new ResponseEntity<Transaction>(transferMoneyTransaction, HttpStatus.OK);
    }
    
    @PostMapping("/withdrawMoney/{accounId}/{amount}/{username}/{password}")
    public ResponseEntity<Transaction> withdrawMoney(@PathVariable long accounId, @PathVariable double amount,@PathVariable long username,@PathVariable String password) throws LowBalanceException, InvalidDetailsException{
        Transaction withdrawMoneyTransaction = accountServiceImplementation.withdraw(accounId, amount, username, password);
        return new ResponseEntity<Transaction>(withdrawMoneyTransaction, HttpStatus.OK);   
    }
    
    @PostMapping("/depositMoney/{accounId}/{amount}")
    public ResponseEntity<Transaction> depositMoney(@PathVariable long accounId, @PathVariable double amount) throws InvalidAccountException, InvalidAmountException, InvalidDetailsException{
        Transaction depositMoneyTransaction = accountServiceImplementation.deposit(accounId, amount);
        return new ResponseEntity<Transaction>(depositMoneyTransaction, HttpStatus.OK);
    }
    
    @PostMapping("/addSavingsAccount")
    public ResponseEntity<SavingsAccount> addSavingsAccount(@RequestBody SavingsAccount saving) throws InvalidDetailsException{
        SavingsAccount  addedSavingsAccount = accountServiceImplementation.addSavingsAccount(saving);
        return new ResponseEntity<SavingsAccount>(addedSavingsAccount, HttpStatus.OK);
    }
    
    @PostMapping("/addTermAccount")
    public ResponseEntity<TermAccount> addTermAccount(@RequestBody TermAccount term) throws InvalidDetailsException{
        TermAccount  addedTermAccount = accountServiceImplementation.addTermAccount(term);
        return new ResponseEntity<TermAccount>(addedTermAccount, HttpStatus.OK);
    }
}
