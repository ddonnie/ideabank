package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AccountRepository;
import com.dataart.fastforward.app.dto.NewAccountDTO;
import com.dataart.fastforward.app.model.Account;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.AccountService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dataart.fastforward.app.services.validation.ValidationUtils.assertNotBlank;

/**
 * Created by logariett on 19.11.2016.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    RoleService roleService;
    @Autowired
    IdeaService ideaService;

    @Override
    public void createAccount(NewAccountDTO newAccountDTO) {
        assertNotBlank(newAccountDTO.getFirstName(), "First name cannot be empty.");
        assertNotBlank(newAccountDTO.getLastName(), "Last name cannot be empty.");
        assertNotBlank(newAccountDTO.getLogin(), "Login cannot be empty.");
        assertNotBlank(newAccountDTO.getPassword(), "Password cannot be empty.");

        if (getAccountByLogin(newAccountDTO.getLogin())!=null) {
            throw new IllegalArgumentException("The login is taken.");
        }
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
    public Account edit(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public Account getAccountById(long userId) {
        return accountRepository.findOne(userId);
    }

    @Override
    public Account getAccountByLogin(String login) { return accountRepository.findOne(login); }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Idea> getBookmarks(Account account) {
        return new ArrayList<Idea>(account.getBookmarkedIdeas());
    }
}
