package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.Account;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface AccountService {

    Account addUserDetail(Account account);
    void delete(long userId);
    Account getUserById(long userId);
    Account editUserDetail(Account account);
    List<Account> getAll();
}
