package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.NewUserDTO;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Orlov on 25.10.2016.
 */

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {return userService.getAll();}

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable long userId) {return userService.getUserById(userId);}

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody NewUserDTO newUserDTO) {userService.createUser(newUserDTO);}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
