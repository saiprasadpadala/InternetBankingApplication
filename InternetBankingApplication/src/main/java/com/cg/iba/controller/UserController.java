package com.cg.iba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.entities.User;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.UserServiceImplementation;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    public UserServiceImplementation userServiceImplementation;

    /**
     * addUser
     * <p>
     * Adding User details to database
     * </p>
     * 
     * @param user
     * @return ResponseEntity<User>
     * @throws InvalidDetailsException
     */

    @PostMapping("/addUser")
    public ResponseEntity<User> addUserDetails(@RequestBody User user) throws InvalidDetailsException {
        User addedCustomer = userServiceImplementation.addNewUser(user);
        return new ResponseEntity<User>(addedCustomer, HttpStatus.OK);
    }
    
    /**
     * deleteUser
     * <p>
     * deleting User details to database
     * </p>
     * 
     * @param user
     * @return ResponseEntity<User>
     * @throws DetailsNotFoundException
     */

    @DeleteMapping("/deleteUser/{UserId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long UserId) throws DetailsNotFoundException {
        boolean isDeleted = false;
        isDeleted = userServiceImplementation.deleteUserInfo(UserId);
        return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
    }
    
    /**
     * updateUser
     * <p>
     * Updating User details to database
     * </p>
     * 
     * @param user
     * @return ResponseEntity<User>
     * @throws InvalidDetailsException
     */

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws InvalidDetailsException {
        User updatedUser = userServiceImplementation.updateUserInfo(user);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }
    
    /**
     * signInUser
     * <p>
     * signIn User details to database if details are valid then it return the user details or else it throws the invalid details exception
     * </p>
     * 
     * @param user
     * @return ResponseEntity<User>
     * @throws InvalidDetailsException
     */

    @PostMapping("/signIn")
    public ResponseEntity<User> signIn(@RequestBody User user) throws InvalidDetailsException {
        User signedUser = userServiceImplementation.signIn(user);
        return new ResponseEntity<User>(signedUser, HttpStatus.OK);
    }
    
    /**
     * addUser
     * <p>
     * signOut User details to database
     * </p>
     * 
     * @param user
     * @return ResponseEntity<User>
     * @throws InvalidDetailsException
     */

    @GetMapping("/signOut")
    public ResponseEntity<User> signOut(@RequestBody User user) throws InvalidDetailsException {
        User userSignOut = userServiceImplementation.signOut(user);
        return new ResponseEntity<User>(userSignOut, HttpStatus.OK);
    }

}
