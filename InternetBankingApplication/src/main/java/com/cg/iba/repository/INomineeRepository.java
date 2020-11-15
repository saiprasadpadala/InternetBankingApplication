package com.cg.iba.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Nominee;

public interface INomineeRepository extends JpaRepository<Nominee, Long> {
    /*
     * this method takes account id as parameter and fetches all the nominees for
     * the given account id and returns them as Set, if no nominees found then
     * returns empty list
     */
    @Query("select n from Nominee n where account_id=?1")
    public Set<Nominee> listAllNominees(long accountid);

}
