package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.iba.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	/*public  User addNewUser(User user) throws InvalidDetailsException;
	public User signIn(User user)throws InvalidDetailsException;
	public User signOut(User user) throws InvalidDetailsException;
	public User updateUserInfo(User user) throws InvalidDetailsException;
	public boolean deleteUserInfo(long userId)throws DetailsNotFoundException;*/
    @Query("Select u from User u where u.userId=?1 and u.password = ?2")
    public User signIn(long username, String password);
}
