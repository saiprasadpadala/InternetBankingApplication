package com.cg.iba.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.iba.entities.Account;
import com.cg.iba.entities.Nominee;
import com.cg.iba.entities.Relation;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.INomineeRepository;

@ExtendWith(MockitoExtension.class)
class NomineeServiceImplementationTest {

    @InjectMocks
    private NomineeServiceImplementation nomineeServiceImplementation;
    
    @Mock
    private INomineeRepository nomineeRepository;
    
    Account account = new Account(1, 3.4, 20000.0, LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));
    Nominee nominee = new Nominee(1, "pavithra", "123", "aadhar", "9876243413", Relation.SISTER, account);
    Nominee nominee1 = new Nominee(2, "megha", "456", "pan", "8976524324", Relation.SISTER, account);
    
    @Test
    void testAddNominee() throws InvalidDetailsException {
        Account createAccount = new Account();
        createAccount.setAccountId(1);
        createAccount.setInterestRate(3.4);
        createAccount.setBalance(20000.0);
        createAccount.setDateOfOpening(LocalDate.parse("2010-01-25", DateTimeFormatter.ofPattern("yyyy-MM-d")));
        Nominee createdNominee = new Nominee();
        createdNominee.setNomineeId(1);
        createdNominee.setName("pavithra");
        createdNominee.setGovtId("123");
        createdNominee.setGovtIdType("aadhar");
        createdNominee.setPhoneNo("9876243413");
        createdNominee.setRelation(Relation.SISTER);
        createdNominee.setBankAccount(createAccount);
        when(nomineeRepository.save(nominee)).thenReturn(nominee);
        Nominee addedNominee = null;
        addedNominee = nomineeServiceImplementation.addNominee(nominee);
        assertNotNull(addedNominee);
        assertEquals(addedNominee.getNomineeId(), createdNominee.getNomineeId());
        assertEquals(addedNominee.getName(), createdNominee.getName());
        assertEquals(addedNominee.getGovtId(), createdNominee.getGovtId());
        assertEquals(addedNominee.getGovtIdType(), createdNominee.getGovtIdType());
        assertEquals(addedNominee.getPhoneNo(), createdNominee.getPhoneNo());
        assertEquals(addedNominee.getRelation(), createdNominee.getRelation());
        assertEquals(createdNominee.getBankAccount().getAccountId(), addedNominee.getBankAccount().getAccountId());
        assertEquals(createdNominee.getBankAccount().getBalance(), addedNominee.getBankAccount().getBalance());
        assertEquals(createdNominee.getBankAccount().getDateOfOpening(), addedNominee.getBankAccount().getDateOfOpening());
        assertEquals(createdNominee.getBankAccount().getInterestRate(), addedNominee.getBankAccount().getInterestRate());
    }
    
    @Test
    public void testAddNomineeThrowsInvalidDetailsException() {
        Nominee nominee = new Nominee(1, "", "123", "aadhar", "9876243413", Relation.SISTER, account);
        Assertions.assertThrows(InvalidDetailsException.class, () -> {
            nomineeServiceImplementation.addNominee(nominee);
        });
    }
    
    @Test
    void testUpdateNominee() throws InvalidDetailsException {
        when(nomineeRepository.findById((long) 1)).thenReturn(Optional.of(nominee));
        when(nomineeRepository.save(nominee)).thenReturn(nominee);
        Nominee updatedNominee = nomineeServiceImplementation.updateNominee(nominee);
        assertNotNull(updatedNominee);
        assertEquals(nominee, updatedNominee);
    }
    
    @Test
    public void testUpdateNomineeThrowsInvalidDetailsException() {
        Nominee nominee = new Nominee(10, "pavithra", "123", "aadhar", "9876243413", Relation.SISTER, account);
        //when(nomineeRepository.findById((long) 11)).thenReturn(Optional.of(new Nominee()));
        Assertions.assertThrows(InvalidDetailsException.class, () -> {
            nomineeServiceImplementation.updateNominee(nominee);
        });
    }

    @Test
    void testDeleteNominee() {
        when(nomineeRepository.findById((long) 1)).thenReturn(Optional.of(nominee));
        boolean deleteNominee = nomineeServiceImplementation.deleteNominee(1);
        verify(nomineeRepository, times(1)).deleteById((long) 1);
        assertNotNull(deleteNominee);
        assertEquals(true, deleteNominee);
    }
    
    @Test
    public void testDeleteNomineeThrowsDetailsNotFoundException() {
        Assertions.assertThrows(DetailsNotFoundException.class, () -> {
            nomineeServiceImplementation.deleteNominee(4);
        });
    }
    @Test
    void testFindNomineeById() {
        when(nomineeRepository.findById((long) 1)).thenReturn(Optional.of(nominee));
        Nominee fetchedNominee = nomineeServiceImplementation.findNomineeById(1);
        assertNotNull(fetchedNominee);
        assertEquals(nominee, fetchedNominee);
    }
    
    @Test
    public void testFindNomineeByIdThrowsDetailsNotFoundException() {
        Assertions.assertThrows(DetailsNotFoundException.class, () -> {
            nomineeServiceImplementation.findNomineeById(4);
        });

    }

    @Test
    void testListAllNominees() {
        Set<Nominee> allNominees = new HashSet<Nominee>();
        allNominees.add(nominee);
        allNominees.add(nominee1);
        when(nomineeRepository.listAllNominees(1)).thenReturn(allNominees);
        Set<Nominee> allNomineesSet = nomineeServiceImplementation.listAllNominees(1);
        assertNotNull(allNomineesSet);
        assertEquals(allNominees, allNomineesSet);
    }

    @Test
    public void testListAllNomineesThrowsEmptyListException() {
        Assertions.assertThrows(EmptyListException.class, () -> {
            nomineeServiceImplementation.listAllNominees(1);
        });
    }
}
