package com.cg.iba.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.Nominee;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;

import com.cg.iba.service.NomineeServiceImplementation;

@RestController
@RequestMapping("/Nominees")
public class NomineeController {
    
    @Autowired
    NomineeServiceImplementation nomineeServiceImplementation;

    /**
     * addNominee to database
     * <p>
     * Adding Nominee details to database this method takes nominee object as
     * parameter and returns nominee object after saving in database,if the nominee
     * details are not valid it throws exception
     * </p>
     * 
     * @param nominee
     * @return ResponseEntity<Nominee>
     * @throws InvalidDetailsException
     */
    @PostMapping("/addNominee")

    public ResponseEntity<Nominee> addNominee(@RequestBody Nominee nominee) throws InvalidDetailsException {
        Nominee nomineeAdded = nomineeServiceImplementation.addNominee(nominee);
        return new ResponseEntity<Nominee>(nomineeAdded, HttpStatus.OK);
    }

    /**
     * updateNominee to database
     * <p>
     * Updating Nominee Details in database this method takes nominee object as
     * parameter and returns nominee object after saving in database,if the nominee
     * details are not valid it throws exception
     * </p>
     * 
     * @param nominee
     * @return ResponseEntity<Nominee>
     * @throws InvalidDetailsException
     */
    @PutMapping("/updateNominee")
    public ResponseEntity<Nominee> updateNominee(@RequestBody Nominee nominee) throws InvalidDetailsException {
        Nominee nomineeUpdated = nomineeServiceImplementation.updateNominee(nominee);
        return new ResponseEntity<Nominee>(nomineeUpdated, HttpStatus.OK);
    }

    /**
     * deleteNominee based on nomineeId
     * <p>
     * Deleting Nominee based on the nomineeId this method takes nomineeId as
     * parameter and returns boolean, if the nomineeId is not present it throws
     * exception
     * </p>
     * 
     * @param nomineeId
     * @return ResponseEntity<Nominee>
     * @throws DetailsNotFoundException
     */

    @DeleteMapping("/deleteNominee/{nomineeId}")
    public ResponseEntity<String> deleteNominee(@PathVariable long nomineeId) throws DetailsNotFoundException {

        boolean isDeleted = nomineeServiceImplementation.deleteNominee(nomineeId);
        if(isDeleted) {
            return new ResponseEntity<String>("Nominee with id "+nomineeId+" deleted successfully",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Failed to detete nominee with id "+nomineeId,HttpStatus.OK);
        }
        
    }

    /**
     * findNominee based on nomineeId
     * <p>
     * Finding Nominee based on nomineeId this method takes nomineeId as parameter
     * and returns nominee object, if the nomineeId is not present it throws
     * exception
     * </p>
     * 
     * @param nomineeId
     * @return ResponseEntity<Nominee>
     * @throws DetailsNotFoundException
     */
    @GetMapping("/findNomineeById/{nomineeId}")
    public ResponseEntity<Nominee> findNomineeById(@PathVariable long nomineeId) throws DetailsNotFoundException {
        Nominee findNominee = null;
        findNominee = nomineeServiceImplementation.findNomineeById(nomineeId);
        return new ResponseEntity<Nominee>(findNominee, HttpStatus.OK);
    }

    /**
     * listAllNominees based on accountid
     * <p>
     * List of all Nominees based on accountid this method takes account id as
     * parameter and validates account id, if the account id is valid, searches for
     * list of nominees for the given account id, if found returns the list of
     * nominees else throws appropriate exceptions
     * </p>
     * 
     * @param accountid
     * @return Set<Nominee>
     * @throws EmptyListException
     * @throws InvalidAccountException
     */
    @GetMapping("/listAllNominees/{accountid}")
    public ResponseEntity<Set<Nominee>> listAllNominees(@PathVariable long accountid) throws EmptyListException, InvalidAccountException {
        Set<Nominee> allNominees = new HashSet<Nominee>();
        allNominees = nomineeServiceImplementation.listAllNominees(accountid);
        return new ResponseEntity<Set<Nominee>>(allNominees,HttpStatus.OK);
     }
}
