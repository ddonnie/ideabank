package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AccountRepository;
import com.dataart.fastforward.app.model.Account;
import com.dataart.fastforward.app.services.AccountService;
import com.dataart.fastforward.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    RoleService roleService;

    @Override
    public Account add(String firstName, String lastName, String login, String password) {
        Account newAccount = new Account();
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setLogin(login);
        newAccount.setPassword(password);
        newAccount.setRole(roleService.getRoleById(2));
        accountRepository.saveAndFlush(newAccount);

        return newAccount;
    }

    @Override
    public void delete(long userId) {
        accountRepository.delete(userId);
    }

    @Override
    public Account getAccountById(long userId) {
        return accountRepository.findOne(userId);
    }

    @Override
    public Account edit(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

}
