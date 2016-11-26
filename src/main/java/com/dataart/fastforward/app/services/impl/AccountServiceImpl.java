package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AccountRepository;
import com.dataart.fastforward.app.dto.NewAccountDTO;
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
    public void createAccount(NewAccountDTO newAccountDTO) {
        Account account = new Account();
        account.setFirstName(newAccountDTO.getFirstName());
        account.setLastName(newAccountDTO.getLastName());
        account.setLogin(newAccountDTO.getLogin());
        account.setPassword(newAccountDTO.getPassword());
        account.setRole(roleService.getRoleById(2));
        accountRepository.saveAndFlush(account);
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
    public Account getAccountByLogin(String login) { return accountRepository.findOne(login); }

    @Override
    public Account edit(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

}
