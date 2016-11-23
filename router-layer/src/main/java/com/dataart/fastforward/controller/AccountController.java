package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.Account;
import com.dataart.fastforward.repository.AccountRepository;
import com.dataart.fastforward.services.AccountService;
import com.dataart.fastforward.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Orlov on 25.10.2016.
 */

@RestController
@RequestMapping(value = "/users")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllUsers() {
        return accountService.getAll();
    }

    @GetMapping("/users/{userId}")
    public Account getUserById(@PathVariable long userId) {return accountService.getAccountById(userId);}

    @PostMapping
    public String createAccount(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String login,
                                @RequestParam String password) {
        Account newAccount = new Account();
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setLogin(login);
        newAccount.setPassword(password);
        newAccount.setRole(roleService.getRoleById(2));
        accountService.add(newAccount);
        return "/users/" + newAccount.getUserId();
    }

}
