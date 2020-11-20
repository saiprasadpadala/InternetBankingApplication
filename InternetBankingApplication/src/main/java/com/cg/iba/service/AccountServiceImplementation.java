package com.cg.iba.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Beneficiary;
import com.cg.iba.entities.SavingsAccount;
import com.cg.iba.entities.TermAccount;
import com.cg.iba.entities.Transaction;
import com.cg.iba.entities.TransactionStatus;
import com.cg.iba.entities.TransactionType;
import com.cg.iba.entities.User;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IBeneficiaryRepository;
import com.cg.iba.repository.IUserRepository;


@Service
public class AccountServiceImplementation implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IBeneficiaryRepository beneficiaryRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private TransactionServiceImplementation transactionServiceImplementation;

    @Override
    public Transaction transferMoney(long senderAccounId, long receiverAccountId, double amount, long username, String password)
            throws LowBalanceException, InvalidAccountException, InvalidDetailsException {

        Account senderAccount = accountRepository.findById(senderAccounId).orElse(new Account());
        if (senderAccount.getAccounId() != senderAccounId) {
            throw new InvalidAccountException("Invalid account id. Transferring money failed");
        } else {
            Beneficiary recieverAccount = beneficiaryRepository.findById(receiverAccountId).orElse(new Beneficiary());
            if (recieverAccount.getBeneficiaryId() != receiverAccountId) {
                throw new InvalidAccountException("Receiver account is not added as beneficiary. Try after adding as beneficiary");
            } else {
                User isUserAvailable = userRepository.signIn(username, password);
                if (isUserAvailable == null) {
                    throw new InvalidAccountException("Check your credentials and try again");
                } else {
                    Transaction transaction = null;
                    Account account=null;
                    if(senderAccount instanceof SavingsAccount) {
                        account=new SavingsAccount(senderAccount.getAccounId(),senderAccount.getInterestRate(),senderAccount.getBalance(),senderAccount.getDateOfOpening(),((SavingsAccount) senderAccount).getMinBalance(),((SavingsAccount) senderAccount).getFine());
                    }
                    else {
                        account=new TermAccount(senderAccount.getAccounId(),senderAccount.getInterestRate(),senderAccount.getBalance(),senderAccount.getDateOfOpening(),((TermAccount) senderAccount).getAmount(),((TermAccount) senderAccount).getMonths(),((TermAccount) senderAccount).getPenaltyAmount());          
                    }
                    if (senderAccount.getBalance() < amount) {
                        transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), account, TransactionStatus.FAILED, "Insufficient Amount");
                        //transactionServiceImplementation.createTransaction(transaction);
                        throw new LowBalanceException("Insufficient amount. Try transferring amount lessthan " + senderAccount.getBalance());
                    } else {
                        double updatedBalance = senderAccount.getBalance() - amount;
                        senderAccount.setBalance(updatedBalance);
                        transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), account, TransactionStatus.SUCCESSFUL, "Transfer Successful");
                    }
                    return transactionServiceImplementation.createTransaction(transaction);
                }
            }
        }
    }

    @Override
    public Transaction withdraw(long accounId, double amount, long username, String password) throws LowBalanceException, InvalidDetailsException {
        Account withdrawerAccount = accountRepository.findById(accounId).orElse(new Account());
        if(withdrawerAccount.getAccounId()!=accounId) {
            throw new InvalidAccountException(" Invalid account id. No account found with id "+accounId);
        }
        else {
            User isUserAvailable = userRepository.signIn(username, password);
            if (isUserAvailable == null) {
                throw new InvalidAccountException("Invalid Account details.Withdrawl failed");
            }
            else {
                Transaction transaction = null;
                Account account=null;
                if(withdrawerAccount instanceof SavingsAccount) {
                    account=new SavingsAccount(withdrawerAccount.getAccounId(),withdrawerAccount.getInterestRate(),withdrawerAccount.getBalance(),withdrawerAccount.getDateOfOpening(),((SavingsAccount) withdrawerAccount).getMinBalance(),((SavingsAccount) withdrawerAccount).getFine());
                }
                else {
                    account=new TermAccount(withdrawerAccount.getAccounId(),withdrawerAccount.getInterestRate(),withdrawerAccount.getBalance(),withdrawerAccount.getDateOfOpening(),((TermAccount) withdrawerAccount).getAmount(),((TermAccount) withdrawerAccount).getMonths(),((TermAccount) withdrawerAccount).getPenaltyAmount());          
                }
                if(withdrawerAccount.getBalance()<amount) {
                    transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), account, TransactionStatus.FAILED, "Insufficient Amount");
                    transactionServiceImplementation.createTransaction(transaction);
                    throw new LowBalanceException("Insufficient amount. Try withdrawing amount lessthan " + withdrawerAccount.getBalance());
                }
                else {
                    double updatedBalance=withdrawerAccount.getBalance()-amount;
                    withdrawerAccount.setBalance(updatedBalance);
                    transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), account, TransactionStatus.SUCCESSFUL, "Withdrawl Successful");
                }
                return transactionServiceImplementation.createTransaction(transaction);
            }
        }
    }

    @Override
    public Transaction deposit(long accounId, double amount) throws InvalidAccountException, InvalidAmountException, InvalidDetailsException {
        Account depositorAccount = accountRepository.findById(accounId).orElse(new Account());
        if(depositorAccount.getAccounId()!=accounId) {
            throw new InvalidAccountException(" Invalid account id. No account found with id "+accounId);
        }
        else {
            Transaction transaction = null;
            if(amount<=0) {
                throw new InvalidAccountException(" Invalid amount. Amount should be greater than >0");
            }
            else {
                double updatedBalance=depositorAccount.getBalance()+amount;
                depositorAccount.setBalance(updatedBalance);
                //Account account=new Account(depositorAccount.getAccounId(),depositorAccount.getBalance(),depositorAccount.getInterestRate(),LocalDate.now());
                Account account=null;
                if(depositorAccount instanceof SavingsAccount) {
                    account=new SavingsAccount(depositorAccount.getAccounId(),depositorAccount.getInterestRate(),depositorAccount.getBalance(),depositorAccount.getDateOfOpening(),((SavingsAccount) depositorAccount).getMinBalance(),((SavingsAccount) depositorAccount).getFine());
                }
                else {
                    account=new TermAccount(depositorAccount.getAccounId(),depositorAccount.getInterestRate(),depositorAccount.getBalance(),depositorAccount.getDateOfOpening(),((TermAccount) depositorAccount).getAmount(),((TermAccount) depositorAccount).getMonths(),((TermAccount) depositorAccount).getPenaltyAmount());          
                }
                
                transaction = new Transaction(amount, TransactionType.CREDIT, LocalDateTime.now(), account, TransactionStatus.SUCCESSFUL, "deposit Successful");
            }
            return transactionServiceImplementation.createTransaction(transaction);
        }
    }

    @Override
    public SavingsAccount addSavingsAccount(SavingsAccount saving) throws InvalidDetailsException {
        if(saving.getMinBalance()<=0) {
            throw new InvalidDetailsException("Invalid account details. Adding savings account failed");
        }
        else {
            SavingsAccount  addedSavingsAccount = accountRepository.save(saving);
            return addedSavingsAccount;
        }
    }

    @Override
    public TermAccount addTermAccount(TermAccount term) throws InvalidDetailsException {
        if(term.getPenaltyAmount()<=0) {
            throw new InvalidDetailsException("Invalid account details. Adding term account failed");
        }
        else {
            TermAccount  addedTermAccount = accountRepository.save(term);
            return addedTermAccount;
        }
    }

    @Override
    public SavingsAccount updateSavingsAccount(SavingsAccount saving) throws InvalidDetailsException {
        
        return null;
    }

    @Override
    public TermAccount updateTermAccount(TermAccount term) throws InvalidDetailsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean closeSavingsAccount(SavingsAccount accountNo) throws InvalidAccountException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean closeTermAccount(TermAccount accountNo) throws InvalidAccountException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Account findAccountById(int account_id) throws InvalidAccountException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Account> viewAccounts(long customerId) throws DetailsNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SavingsAccount viewSavingAcc(long customerId) throws DetailsNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TermAccount viewTermAcc(long customerId) throws DetailsNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
