package com.dataart.fastforward.services.impl;

import com.dataart.fastforward.entity.Account;
import com.dataart.fastforward.repository.AccountRepository;
import com.dataart.fastforward.services.AccountService;
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

    @Override
    public Account addUserDetail(Account account) {
        Account savedAccount = accountRepository.saveAndFlush(account);

        return savedAccount;
    }

    @Override
    public void delete(long userId) {
        accountRepository.delete(userId);
    }

    @Override
    public Account getUserById(long userId) {
        return accountRepository.findOne(userId);
    }

    @Override
    public Account editUserDetail(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

}
