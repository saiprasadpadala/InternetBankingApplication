package com.cg.iba.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IBeneficiaryRepository;

@Service
public class BeneficiaryServiceImplementation implements IBeneficiaryService {

    @Autowired
    IBeneficiaryRepository beneficiaryRepository;
    @Autowired
    IAccountRepository accountRepository;

    public Beneficiary addBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException {
        /*
         * storing beneficiary details into database if beneficiary details are valid
         * then added and it return beneficiary object or else throw
         * InvalidDetailsException
         */
        if (beneficiary.getBeneficiaryAccNo() <= 0) {
            throw new InvalidDetailsException("Adding beneficiary to database failed.");
        } else {
            Beneficiary addedBeneficiary = beneficiaryRepository.save(beneficiary);
            addedBeneficiary.getBankAccount().setBeneficiaries(null);
            addedBeneficiary.getBankAccount().setCustomers(null);
            addedBeneficiary.getBankAccount().setNominees(null);
            return addedBeneficiary;
        }
    }

    public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException {
        /*
         * updating beneficiary details if valid details are provided it will update,
         * else throw Invalid Details exception
         *
         */
        Beneficiary existingBeneficiary = beneficiaryRepository.findById(beneficiary.getBeneficiaryId()).orElse(new Beneficiary());
        if (existingBeneficiary.getBeneficiaryId() != beneficiary.getBeneficiaryId()) {
            throw new InvalidDetailsException("Updation failed. Beneficiary with id " + beneficiary.getBeneficiaryId() + " not found.");
        } else {
            Beneficiary updatedBeneficiary = beneficiaryRepository.save(beneficiary);
            updatedBeneficiary.getBankAccount().setBeneficiaries(null);
            updatedBeneficiary.getBankAccount().setCustomers(null);
            updatedBeneficiary.getBankAccount().setNominees(null);
            return updatedBeneficiary;
        }
    }

    public Beneficiary findBeneficiaryById(long beneficiaryId) throws DetailsNotFoundException {
        /*
         * fetch beneficiary details by beneficiaryId and return beneficiary details if
         * found else throw exception
         *
         */
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(new Beneficiary());
        if (beneficiary.getBeneficiaryId() != beneficiaryId) {
            throw new DetailsNotFoundException("No beneficiary found with id " + beneficiaryId + " to fetch");
        } else {
            beneficiary.getBankAccount().setBeneficiaries(null);
            beneficiary.getBankAccount().setCustomers(null);
            beneficiary.getBankAccount().setNominees(null);
            return beneficiary;
        }
    }

    public boolean deleteBeneficiary(long beneficiaryId) throws DetailsNotFoundException {
        /*
         * deleting beneficiary details based on beneficiaryId If details deleted it
         * returns true , else throw exception
         *
         */
        boolean isDeleted = false;
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(new Beneficiary());
        if (beneficiary.getBeneficiaryId() != beneficiaryId) {
            throw new DetailsNotFoundException("Invalid beneficiary details deletion failed");
        } else {
            beneficiaryRepository.delete(beneficiary);
            isDeleted = true;
        }
        return isDeleted;

    }

    public Set<Beneficiary> listAllBeneficiaries(long accountId) throws InvalidAccountException, EmptyListException {
        Account fetchedAccount = accountRepository.findById(accountId).orElse(new Account());
        if(fetchedAccount.getAccountId()!=accountId) {
            throw new InvalidAccountException("Invalid account id. No account found with id " + accountId);
        }else {
            List<Beneficiary> allBeneficiariesList = new ArrayList<Beneficiary>();
            allBeneficiariesList = beneficiaryRepository.listAllBeneficiaries(accountId);
            if (allBeneficiariesList.isEmpty()) {
                throw new EmptyListException("No beneficiaries found for this accountId " + accountId);
            } else {
                Set<Beneficiary> allBeneficiarySet = new HashSet<Beneficiary>();
                allBeneficiarySet.addAll(allBeneficiariesList);
                for (Beneficiary beneficiary : allBeneficiarySet) {
                    beneficiary.getBankAccount().setBeneficiaries(null);
                    beneficiary.getBankAccount().setCustomers(null);
                    beneficiary.getBankAccount().setNominees(null);
                }
                return allBeneficiarySet;
            }
        }
    }
}
