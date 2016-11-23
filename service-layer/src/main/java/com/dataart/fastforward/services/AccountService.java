package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.Account;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface AccountService {

    Account add(Account account);
    void delete(long userId);
    Account getAccountById(long userId);
    Account edit(Account account);
    List<Account> getAll();
}
