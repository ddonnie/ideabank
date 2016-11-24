package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.model.Account;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface AccountService {

    Account add(String firstName, String lastName, String login, String password);
    void delete(long userId);
    Account getAccountById(long userId);
    Account edit(Account account);
    List<Account> getAll();
}
