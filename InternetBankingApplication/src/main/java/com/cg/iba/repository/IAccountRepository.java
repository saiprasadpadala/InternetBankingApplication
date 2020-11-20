package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.iba.entities.Account;


public interface IAccountRepository extends JpaRepository<Account, Long> {
}
