package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.iba.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
