package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.User;
import com.dataart.fastforward.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Orlov on 25.10.2016.
 */

@RestController
public class TestRestController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> testMethod() {
        return userService.findAll();
    }
}
