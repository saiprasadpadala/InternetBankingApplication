package com.cg.iba.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Beneficiary;
import com.cg.iba.entities.Customer;
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
import com.cg.iba.repository.ICustomerRepository;
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
    private ICustomerRepository customerRepository;
    @Autowired
    private TransactionServiceImplementation transactionServiceImplementation;

    @Override
    public Transaction transferMoney(long senderAccounId, long receiverAccountId, double amount, long username, String password)
            throws LowBalanceException, InvalidAccountException, InvalidDetailsException {

        Account senderAccount = accountRepository.findById(senderAccounId).orElse(new Account());
        if (senderAccount.getAccountId() != senderAccounId) {
            throw new InvalidAccountException("Invalid account id. Transferring money failed");
        } else {
            Beneficiary recieverAccount = beneficiaryRepository.findById(receiverAccountId).orElse(new Beneficiary());
            if (recieverAccount.getBeneficiaryId() != receiverAccountId) {
                throw new InvalidAccountException("Receiver account is not added as beneficiary. Try after adding as beneficiary");
            } else {
                User isUserAvailable = userRepository.signIn(username, password);
                if (isUserAvailable == null) {
                    throw new InvalidDetailsException("Check your credentials and try again");
                } else {
                    Transaction transaction = null;
                    /*Account account = null;
                    if (senderAccount instanceof SavingsAccount) {
                        account = new SavingsAccount(senderAccount.getAccountId(), senderAccount.getInterestRate(), senderAccount.getBalance(), senderAccount.getDateOfOpening(),
                                ((SavingsAccount) senderAccount).getMinBalance(), ((SavingsAccount) senderAccount).getFine());
                    } else {
                        account = new TermAccount(senderAccount.getAccountId(), senderAccount.getInterestRate(), senderAccount.getBalance(), senderAccount.getDateOfOpening(),
                                ((TermAccount) senderAccount).getAmount(), ((TermAccount) senderAccount).getMonths(), ((TermAccount) senderAccount).getPenaltyAmount());
                    }*/
                    senderAccount.setBeneficiaries(null);
                    senderAccount.setCustomers(null);
                    senderAccount.setNominees(null);
                    if (senderAccount.getBalance() < amount) {
                        transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), senderAccount, TransactionStatus.FAILED, "Insufficient Amount");
                        // transactionServiceImplementation.createTransaction(transaction);
                        throw new LowBalanceException("Insufficient amount. Try transferring amount lessthan " + senderAccount.getBalance());
                    } else {
                        double updatedBalance = senderAccount.getBalance() - amount;
                        senderAccount.setBalance(updatedBalance);
                        transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), senderAccount, TransactionStatus.SUCCESSFUL, "Transfer Successful");
                    }
                    return transactionServiceImplementation.createTransaction(transaction);
                }
            }
        }
    }

    @Override
    public Transaction withdraw(long accounId, double amount, long username, String password) throws LowBalanceException, InvalidDetailsException {
        Account withdrawerAccount = accountRepository.findById(accounId).orElse(new Account());
        if (withdrawerAccount.getAccountId() != accounId) {
            throw new InvalidAccountException(" Invalid account id. No account found with id " + accounId);
        } else {
            User isUserAvailable = userRepository.signIn(username, password);
            if (isUserAvailable == null) {
                throw new InvalidAccountException("Invalid Account details.Withdrawl failed");
            } else {
                Transaction transaction = null;
               /* Account account = null;
                if (withdrawerAccount instanceof SavingsAccount) {
                    account = new SavingsAccount(withdrawerAccount.getAccountId(), withdrawerAccount.getInterestRate(), withdrawerAccount.getBalance(),
                            withdrawerAccount.getDateOfOpening(), ((SavingsAccount) withdrawerAccount).getMinBalance(), ((SavingsAccount) withdrawerAccount).getFine());
                } else {
                    account = new TermAccount(withdrawerAccount.getAccountId(), withdrawerAccount.getInterestRate(), withdrawerAccount.getBalance(),
                            withdrawerAccount.getDateOfOpening(), ((TermAccount) withdrawerAccount).getAmount(), ((TermAccount) withdrawerAccount).getMonths(),
                            ((TermAccount) withdrawerAccount).getPenaltyAmount());
                }*/
                withdrawerAccount.setBeneficiaries(null);
                withdrawerAccount.setCustomers(null);
                withdrawerAccount.setNominees(null);
                if (withdrawerAccount.getBalance() < amount) {
                    transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), withdrawerAccount, TransactionStatus.FAILED, "Insufficient Amount");
                    transactionServiceImplementation.createTransaction(transaction);
                    throw new LowBalanceException("Insufficient amount. Try withdrawing amount lessthan " + withdrawerAccount.getBalance());
                } else {
                    double updatedBalance = withdrawerAccount.getBalance() - amount;
                    withdrawerAccount.setBalance(updatedBalance);
                    transaction = new Transaction(amount, TransactionType.DEBIT, LocalDateTime.now(), withdrawerAccount, TransactionStatus.SUCCESSFUL, "Withdrawl Successful");
                }
                return transactionServiceImplementation.createTransaction(transaction);
            }
        }
    }

    @Override
    public Transaction deposit(long accounId, double amount) throws InvalidAccountException, InvalidAmountException, InvalidDetailsException {
        Account depositorAccount = accountRepository.findById(accounId).orElse(new Account());
        if (depositorAccount.getAccountId() != accounId) {
            throw new InvalidAccountException(" Invalid account id. No account found with id " + accounId);
        } else {
            Transaction transaction = null;
            if (amount %100!=0) {
                throw new InvalidAccountException(" Invalid amount. Amount should be multiple of 100");
            } else {
                double updatedBalance = depositorAccount.getBalance() + amount;
                depositorAccount.setBalance(updatedBalance);
                // Account account=new
                // Account(depositorAccount.getAccounId(),depositorAccount.getBalance(),depositorAccount.getInterestRate(),LocalDate.now());
                /*Account account = null;
                if (depositorAccount instanceof SavingsAccount) {
                    account = new SavingsAccount(depositorAccount.getAccountId(), depositorAccount.getInterestRate(), depositorAccount.getBalance(),
                            depositorAccount.getDateOfOpening(), ((SavingsAccount) depositorAccount).getMinBalance(), ((SavingsAccount) depositorAccount).getFine());
                } else {
                    account = new TermAccount(depositorAccount.getAccountId(), depositorAccount.getInterestRate(), depositorAccount.getBalance(),
                            depositorAccount.getDateOfOpening(), ((TermAccount) depositorAccount).getAmount(), ((TermAccount) depositorAccount).getMonths(),
                            ((TermAccount) depositorAccount).getPenaltyAmount());
                }*/
                depositorAccount.setBeneficiaries(null);
                depositorAccount.setNominees(null);
                depositorAccount.setCustomers(null);
                transaction = new Transaction(amount, TransactionType.CREDIT, LocalDateTime.now(), depositorAccount, TransactionStatus.SUCCESSFUL, "deposit Successful");
            }
            return transactionServiceImplementation.createTransaction(transaction);
        }
    }

    @Override
    public SavingsAccount addSavingsAccount(SavingsAccount saving) throws InvalidDetailsException {
        if (saving.getMinBalance() <= 0) {
            throw new InvalidDetailsException("Invalid account details. Adding savings account failed");
        } else {
            SavingsAccount addedSavingsAccount = accountRepository.save(saving);
            return addedSavingsAccount;
        }
    }

    @Override
    public TermAccount addTermAccount(TermAccount term) throws InvalidDetailsException {
        if (term.getPenaltyAmount() <= 0) {
            throw new InvalidDetailsException("Invalid account details. Adding term account failed");
        } else {
            TermAccount addedTermAccount = accountRepository.save(term);
            return addedTermAccount;
        }
    }

    @Override
    public SavingsAccount updateSavingsAccount(SavingsAccount saving) throws InvalidDetailsException {
        SavingsAccount updatedAccount = (SavingsAccount) accountRepository.findById(saving.getAccountId()).orElse(new SavingsAccount());
        if (updatedAccount.getAccountId() != saving.getAccountId()) {
            throw new InvalidDetailsException("No savings account found with id " + saving.getAccountId());
        } else {
            return accountRepository.save(saving);
        }
    }

    @Override
    public TermAccount updateTermAccount(TermAccount term) throws InvalidDetailsException {
        TermAccount updatedAccount = (TermAccount) accountRepository.findById(term.getAccountId()).orElse(new TermAccount());
        if (updatedAccount.getAccountId() != term.getAccountId()) {
            throw new InvalidDetailsException("No term account found with id " + term.getAccountId());
        } else {
            return accountRepository.save(term);
        }
    }

    @Override
    public boolean closeSavingsAccount(SavingsAccount accountNo) throws InvalidAccountException {
        boolean isDeleted = false;
        SavingsAccount deletedAccount = (SavingsAccount) accountRepository.findById(accountNo.getAccountId()).orElse(new SavingsAccount());
        if (deletedAccount.getAccountId() != accountNo.getAccountId()) {
            throw new InvalidAccountException("No savings account found with id " + accountNo.getAccountId() + " to delete");
        } else {
            accountRepository.deleteById(accountNo.getAccountId());
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean closeTermAccount(TermAccount accountNo) throws InvalidAccountException {
        boolean isDeleted = false;
        TermAccount deletedAccount = (TermAccount) accountRepository.findById(accountNo.getAccountId()).orElse(new TermAccount());
        if (deletedAccount.getAccountId() != accountNo.getAccountId()) {
            throw new InvalidAccountException("No term account found with id " + accountNo.getAccountId() + "to delete");
        } else {
            accountRepository.deleteById(accountNo.getAccountId());
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public Account findAccountById(int account_id) throws InvalidAccountException {
        Account fetchedAccount = accountRepository.findById((long) account_id).orElse(new Account());
        if (fetchedAccount.getAccountId() != account_id) {
            throw new InvalidAccountException("No account found with id " + account_id + "to fetch");
        } else {
            fetchedAccount.setBeneficiaries(null);
            fetchedAccount.setCustomers(null);
            fetchedAccount.setNominees(null);
            return fetchedAccount;
        }
    }

    @Override
    public Set<Account> viewAccounts(long customerId) throws DetailsNotFoundException {
        List<Account> customerAccountsList = new ArrayList<Account>();
        customerAccountsList = accountRepository.viewAccounts(customerId);
        if (customerAccountsList.isEmpty()) {
            throw new DetailsNotFoundException("No accounts found for customer with id " + customerId + "to view");
        } else {
            Set<Account> customerAccountsSet = new HashSet<Account>();
            customerAccountsSet.addAll(customerAccountsList);
            for(Account account:customerAccountsSet) {
                account.setBeneficiaries(null);
                account.setCustomers(null);
                account.setNominees(null);
            }
            return customerAccountsSet;
        }
    }

    @Override
    public List<SavingsAccount> viewSavingAcc(long customerId) throws DetailsNotFoundException {
        Customer fetchedCustomer = customerRepository.findById(customerId).orElse(new Customer());
        if (fetchedCustomer.getCustomerId() != customerId) {
            throw new DetailsNotFoundException("Invalid customer. Customer with " + customerId + " is not registered");
        } else {
            List<SavingsAccount> fecthedSavingsAccountList=accountRepository.viewSavingAcc(customerId);
            
            /*SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setAccounId(fecthedSavingsAccount.getAccountId());
            savingsAccount.setBalance(fecthedSavingsAccount.getBalance());
            savingsAccount.setDateOfOpening(fecthedSavingsAccount.getDateOfOpening());
            savingsAccount.setMinBalance(fecthedSavingsAccount.getMinBalance());
            savingsAccount.setFine(fecthedSavingsAccount.getFine());
            return savingsAccount;*/
            if(fecthedSavingsAccountList.isEmpty()) {
                throw new DetailsNotFoundException("Customer with Id "+customerId+" is not having any Savings Accounts");
            }else {
                for(SavingsAccount saving:fecthedSavingsAccountList) {
                    saving.setBeneficiaries(null);
                    saving.setCustomers(null);
                    saving.setNominees(null);
                }
                return fecthedSavingsAccountList;
            }   
        }
    }

    @Override
    public List<TermAccount> viewTermAcc(long customerId) throws DetailsNotFoundException {
        Customer fetchedCustomer = customerRepository.findById(customerId).orElse(new Customer());
        if (fetchedCustomer.getCustomerId() != customerId) {
            throw new DetailsNotFoundException("Invalid customer. Customer with " + customerId + " is not registered");
        } else {
            List<TermAccount> fecthedTermAccountList = accountRepository.viewTermAcc(customerId);
            if(fecthedTermAccountList.isEmpty()) {
                throw new DetailsNotFoundException("Customer with Id "+customerId+" is not having Term Accounts");
            }else {
                for(TermAccount term:fecthedTermAccountList) {
                    term.setBeneficiaries(null);
                    term.setCustomers(null);
                    term.setNominees(null);
                }
                return fecthedTermAccountList;
            }
        }
    }

}
