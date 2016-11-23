package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.Account;
import com.dataart.fastforward.entity.Idea;
import com.dataart.fastforward.repository.RoleRepository;
import com.dataart.fastforward.repository.AccountRepository;
import com.dataart.fastforward.services.IdeaService;
import com.dataart.fastforward.services.RoleService;
import com.dataart.fastforward.services.AccountService;
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
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    IdeaService ideaService;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    public List<Account> testMethod() {
        return accountService.getAll();
    }

    @GetMapping("/users/{userId}")
    public Account anotherMethod(@PathVariable long userId) {return accountService.getUserById(userId);}

    @PostMapping("/users/")
    public String userDetailAdd(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String login,
                                @RequestParam String password) {
        Account newAccount = new Account();
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setLogin(login);
        newAccount.setPassword(password);
        newAccount.setRole(roleService.getRoleById(2));
        accountService.addUserDetail(newAccount);
        return "/users/" + newAccount.getUserId();
    }

    @GetMapping("/ideas")
    public List<Idea> getAllIdeas() {
        return ideaService.getAll();
    }
}
