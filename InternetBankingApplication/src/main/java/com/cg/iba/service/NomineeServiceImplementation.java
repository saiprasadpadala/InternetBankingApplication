package com.cg.iba.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Nominee;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.INomineeRepository;
@Service
public class NomineeServiceImplementation implements INomineeService {
    
    @Autowired
    INomineeRepository nomineeRepository;

    /**
     * addNominee to database
     * <p>
     * this method takes nominee object as parameter and returns nominee object
     * after saving in database,if the nominee details are not valid it throws
     * exception
     * </p>
     * 
     * @param nominee
     * @return Nominee
     * @throws InvalidDetailsException
     */
    @Override
    public Nominee addNominee(Nominee nominee) throws InvalidDetailsException {
        if (nominee.getName().length() == 0) {
            throw new InvalidDetailsException("Adding Nominee failed, Invalid Nominee Details");
        } else {
            return nomineeRepository.save(nominee);
        }
    }

    /**
     * updateNominee to database
     * <p>
     * this method takes nominee object as parameter and returns nominee object
     * after saving in database,if the nominee details are not valid it throws
     * exception
     * </p>
     * 
     * @param nominee
     * @return Nominee
     * @throws InvalidDetailsException
     */
    @Override
    public Nominee updateNominee(Nominee nominee) throws InvalidDetailsException {
        Nominee fetchedNominee = nomineeRepository.findById(nominee.getNomineeId()).orElse(new Nominee());
        if (fetchedNominee.getNomineeId() != nominee.getNomineeId()) {
            throw new InvalidDetailsException("Updating Nominee failed, Invalid Nominee Details");
        } else {
            return nomineeRepository.save(nominee);
        }
    }

    /**
     * deleteNominee based on nomineeId
     * <p>
     * this method takes nomineeId as parameter and returns boolean, if the
     * nomineeId is not present it throws exception
     * </p>
     * 
     * @param nomineeId
     * @return boolean
     * @throws DetailsNotFoundException
     */

    @Override
    public boolean deleteNominee(long nomineeId) throws DetailsNotFoundException {
        Nominee searchNominee = nomineeRepository.findById(nomineeId).orElse(new Nominee());
        boolean isDeleted = false;
        if (searchNominee.getNomineeId() == nomineeId) {
            nomineeRepository.deleteById(nomineeId);
            isDeleted = true;

        } else {
            throw new DetailsNotFoundException("No nominee found with nominee id " + nomineeId + " to delete");
        }
        return isDeleted;

    }

    /**
     * findNominee based on nomineeId
     * <p>
     * this method takes nomineeId as parameter and returns nominee object, if the
     * nomineeId is not present it throws exception
     * </p>
     * 
     * @param nomineeId
     * @return Nominee
     * @throws DetailsNotFoundException
     */
    @Override
    public Nominee findNomineeById(long nomineeId) throws DetailsNotFoundException {
        Nominee obtainedNominee = nomineeRepository.findById(nomineeId).orElse(null);
        if (obtainedNominee == null) {
            throw new DetailsNotFoundException("Nominee not found with nominee id " + nomineeId + " to fetch");
        } else {
            return obtainedNominee;
        }
    }

    /**
     * listAllNominees based on accountid
     * <p>
     * this method takes account id as parameter and validates account id, if the
     * account id is valid, searches for list of nominees for the given account id,
     * if found returns the list of nominees else throws appropriate exceptions
     * </p>
     * 
     * @param accountid
     * @return Set<Nominee>
     * @throws EmptyListException
     * @throws InvalidAccountException
     */
    @Override
    public Set<Nominee> listAllNominees(long accountid) throws InvalidAccountException, EmptyListException {
        Set<Nominee> allNominees = new HashSet<Nominee>();
        allNominees = nomineeRepository.listAllNominees(accountid);
        if (allNominees.isEmpty()) {
            throw new EmptyListException("No nominee found for account with id " + accountid);
        } else {
            return allNominees;
        }
    }
}
