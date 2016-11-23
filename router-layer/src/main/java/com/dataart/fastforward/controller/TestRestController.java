package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.Idea;
import com.dataart.fastforward.entity.UserDetail;
import com.dataart.fastforward.repository.RoleRepository;
import com.dataart.fastforward.repository.UserDetailRepository;
import com.dataart.fastforward.services.IdeaService;
import com.dataart.fastforward.services.RoleService;
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
    RoleService roleService;
    @Autowired
    IdeaService ideaService;

    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    public List<UserDetail> testMethod() {
        return userDetailService.getAll();
    }

    @GetMapping("/users/{userId}")
    public UserDetail anotherMethod(@PathVariable long userId) {return userDetailService.getUserById(userId);}

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
        newUserDetail.setRole(roleService.getRoleById(2));
        userDetailService.addUserDetail(newUserDetail);
        return "/users/" + newUserDetail.getUserId();
    }

    @GetMapping("/ideas")
    public List<Idea> getAllIdeas() {
        return ideaService.getAll();
    }
}
