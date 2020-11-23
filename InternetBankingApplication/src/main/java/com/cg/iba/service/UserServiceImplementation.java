package com.cg.iba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entities.User;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IUserRepository;

@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    IUserRepository userRepository;

    /**
     * addNewUser
     * <p>
     * Adding User details to database
     * </p>
     * 
     * @param user
     * @return User
     * @throws InvalidDetailsException
     */

    @Override
    public User addNewUser(User user) throws InvalidDetailsException {
        if (user.getPassword().length() == 0) {
            throw new InvalidDetailsException("Invalid details. Adding new user failed.");
        } else {
            return userRepository.save(user);
        }
    }

    /**
     * signInUser
     * <p>
     * signIn User details to database
     * </p>
     * 
     * @param user
     * @return User
     * @throws InvalidDetailsException
     */

    @Override
    public User signIn(User user) throws InvalidDetailsException {
        User existingUser = userRepository.signIn(user.getUserId(), user.getPassword());

        // if (existingUser.getUserId()==user.getUserId() &&
        // existingUser.getPassword().equals(user.getPassword())) {
        if (existingUser != null) {
            return existingUser;
        } else {
            throw new InvalidDetailsException("Invalid details signin failed. Check and your UserName and Password");
        }
    }

    /**
     * signOutUser
     * <p>
     * signOut User details to database
     * </p>
     * 
     * @param user
     * @return User
     * @throws InvalidDetailsException
     */

    @Override // implementation is not correct. need to change
    public User signOut(User user) throws InvalidDetailsException {
        /*
         * if (user == null) { throw new InvalidDetailsException("Invalid UserId!"); }
         * return userRepository.save(user);
         */
        return null;
    }

    /**
     * updateUser
     * <p>
     * updating User details to database
     * </p>
     * 
     * @param user
     * @return User
     * @throws InvalidDetailsException
     */

    @Override
    public User updateUserInfo(User user) throws InvalidDetailsException {
        User existingUser = userRepository.findById(user.getUserId()).orElse(new User());

        if (existingUser.getUserId() != user.getUserId()) {
            throw new InvalidDetailsException("Updation failed. No user found with id " + user.getUserId());
        } else {
            return userRepository.save(user);
        }
    }

    /**
     * deleteUser
     * <p>
     * deleting User details to database
     * </p>
     * 
     * @param user
     * @return User
     * @throws DetailsNotFoundException
     */

    @Override
    public boolean deleteUserInfo(long userId) throws DetailsNotFoundException {
        boolean isDeleted = false;
        User user = userRepository.findById(userId).orElse(new User());
        if (user.getUserId() != userId) {
            throw new DetailsNotFoundException("Invalid user id. No user found with id " + userId + " to delete");
        } else {
            userRepository.delete(user);
            isDeleted = true;
        }
        return isDeleted;
    }
}
