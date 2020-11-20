package com.cg.iba.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.iba.entities.Beneficiary;

public interface IBeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

	/*public Beneficiary addBeneficiary(Beneficiary beneficiary)throws InvalidDetailsException;
	public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException;
	public boolean deleteBeneficiary(long beneficiaryId) throws DetailsNotFoundException;
	public Beneficiary findBeneficiaryById(long beneficiaryId) throws DetailsNotFoundException;
	public Set<Beneficiary> listAllBeneficiaries(long accountid) throws InvalidAccountException,EmptyListException;*/
}
