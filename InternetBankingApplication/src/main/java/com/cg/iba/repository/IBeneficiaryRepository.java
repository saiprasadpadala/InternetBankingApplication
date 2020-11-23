package com.cg.iba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.Beneficiary;

public interface IBeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
	
	@Query("select b from Beneficiary b where account_id = ?1" )
	public List<Beneficiary> listAllBeneficiaries(long accountId);

	
	
}
