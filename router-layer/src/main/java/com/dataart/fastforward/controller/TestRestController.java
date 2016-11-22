package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.UserDetail;
import com.dataart.fastforward.repository.UserDetailRepository;
import com.dataart.fastforward.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Orlov on 25.10.2016.
 */

@RestController
@RequestMapping(value = "/")
public class TestRestController {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserDetailRepository userDetailRepository;

    @GetMapping("/users")
    public List<UserDetail> testMethod() {
        return userDetailService.getAll();
    }

    @GetMapping("/users/{userId}")
    public UserDetail anotherMethod(@PathVariable long userId) {return userDetailService.getByUserId(userId);}

    @PostMapping("/users/")
    public String userDetailAdd(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String login,
                                @RequestParam String password) {
        UserDetail newUserDetail = new UserDetail();
        newUserDetail.setFirstName(firstName);
        newUserDetail.setLastName(lastName);
        newUserDetail.setLogin(login);
        newUserDetail.setPassword(password);
        newUserDetail.setRoleId(2);
        userDetailService.addUserDetail(newUserDetail);
        return "/users/" + newUserDetail.getUserId();
    }
}
