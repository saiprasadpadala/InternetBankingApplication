package com.cg.iba.repository;


import com.cg.iba.entities.User;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;

public interface IUserRepository {
	public  User addNewUser(User user) throws InvalidDetailsException;
	public User signIn(User user)throws InvalidDetailsException;
	public User signOut(User user) throws InvalidDetailsException;
	public User updateUserInfo(User user) throws InvalidDetailsException;
	public boolean deleteUserInfo(long userId)throws DetailsNotFoundException;
}