package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.iba.entities.Admin;

public interface IAdminRepository extends JpaRepository<Admin, Long> {

	/*public Admin  addAdmin(Admin admin) throws InvalidDetailsException;
	public Admin findAdminById(long adminId)throws DetailsNotFoundException ;
	public Admin updateAdmin(Admin admin) throws InvalidDetailsException ;
	public boolean removeAdmin(long adminId)throws DetailsNotFoundException;
	public Set<Admin> listAllAdmins() throws EmptyListException;*/
}
