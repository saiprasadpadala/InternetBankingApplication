package com.cg.iba.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long adminId;
	private String adminName;
	private String adminContact;
	private String adminEmailId;
	
	public Admin() {
        super();
    }
	
    public Admin(long adminId, String adminName, String adminContact, String adminEmailId) {
        super();
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminContact = adminContact;
        this.adminEmailId = adminEmailId;
    }

    public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminContact() {
		return adminContact;
	}
	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}
	public String getAdminEmailId() {
		return adminEmailId;
	}
	public void setAdminEmailId(String adminEmailId) {
		this.adminEmailId = adminEmailId;
	}
}