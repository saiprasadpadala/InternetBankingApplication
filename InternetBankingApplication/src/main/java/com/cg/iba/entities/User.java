package com.cg.iba.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        super();
    }

    // parameterized constructor
    public User(long userId, String password, Role role) {
        super();
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    // setters and getters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // tostring method
    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", role=" + role + "]";
    }

}
