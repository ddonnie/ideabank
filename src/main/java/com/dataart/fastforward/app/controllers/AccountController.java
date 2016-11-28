package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.AccountRepository;
import com.dataart.fastforward.app.dto.NewAccountDTO;
import com.dataart.fastforward.app.model.Account;
import com.dataart.fastforward.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllUsers() {
        return accountService.getAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Account getUserById(@PathVariable long userId) {return accountService.getAccountById(userId);}

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createAccount(@RequestBody NewAccountDTO newAccount) {accountService.createAccount(newAccount);}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
