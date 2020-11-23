package com.cg.iba.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.Admin;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAdminRepository;

@Service
public class AdminServiceImplementation implements IAdminService {

    @Autowired
    private IAdminRepository adminRepository;

    /**
     * addAdmin
     * <p>
     * To adding admin details in database if details are invalid throws exception
     * </P>
     * 
     * @param admin
     * @return Admin
     * @throws InvalidDetailsException
     */
    @Override
    public Admin addAdmin(Admin admin) throws InvalidDetailsException {
        if (admin.getAdminName().length()<=1) {
            throw new InvalidDetailsException("Invalid details. Adding admin to database failed.");
        } else {
            return adminRepository.save(admin);
        }
    }

    /**
     * findAdminById
     * <p>
     * To find admin details in database by using adminId as parameter and return
     * admin object if details are invalid throws exception
     * </P>
     * 
     * @param adminId
     * @return Admin
     * @throws DetailsNotFoundException
     */
    @Override
    public Admin findAdminById(long adminId) throws DetailsNotFoundException {
        Admin admin = adminRepository.findById(adminId).orElse(new Admin());
        if (admin.getAdminId()!=adminId) {
            throw new DetailsNotFoundException("Admin with id "+adminId+" not found to fetch.");
        }
        else {
            return admin;
        }
    }

    /**
     * updateAdmin
     * <p>
     * To update admin details in database by using adminId as parameter and return
     * admin object if details are invalid throws exception
     * </P>
     * 
     * @param admin
     * @return Admin
     * @throws InvalidDetailsException
     */
    @Override
    public Admin updateAdmin(Admin admin) throws InvalidDetailsException {
        Admin admin1 = adminRepository.findById(admin.getAdminId()).orElse(new Admin());
        if (admin1.getAdminId() != admin.getAdminId()) {
            throw new InvalidDetailsException("Admin with Id "+admin.getAdminId()+" not found to update.");
        } else {
            return adminRepository.save(admin);
        }

    }

    /**
     * removeAdmin
     * <p>
     * To remove admin details in database by using adminId as parameter and return
     * admin object if details are invalid throws exception
     * </P>
     * 
     * @param admin
     * @return boolean
     * @throws DetailsNotFoundException
     */

    @Override
    public boolean removeAdmin(long adminId) throws DetailsNotFoundException {
        boolean isDeleted = false;
        Admin admin1 = adminRepository.findById(adminId).orElse(new Admin());
        if (admin1.getAdminId() != adminId) {
            throw new DetailsNotFoundException("Admin with Id "+adminId+" not found to delete.");
        } else {
            adminRepository.deleteById(adminId);
            isDeleted = true;
        }
        return isDeleted;
    }

    /**
     * listAllAdmins
     * <p>
     * To listAlladmin details in database by using adminId as parameter and return
     * admin object if details are invalid throws exception
     * </P>
     * 
     * @param admin
     * @return Set<Admin>
     * @throws EmptyListException
     */
    @Override
    public Set<Admin> listAllAdmins() throws EmptyListException {
        List<Admin> adminsList = new ArrayList<Admin>();

        adminsList = adminRepository.findAll();
        if (adminsList.isEmpty()) {
            throw new EmptyListException("No Admins found. Empty list");
        } else {
            Set<Admin> adminsSet = new HashSet<Admin>();

            adminsSet.addAll(adminsList);
            return adminsSet;
        }
    }
}
