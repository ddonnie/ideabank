package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.NewAccountDTO;
import com.dataart.fastforward.app.model.Account;
import com.dataart.fastforward.app.model.Idea;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface AccountService {

    void createAccount(NewAccountDTO newAccountDTO);
    Account edit(Account account);
    void delete(long userId);

    Account getAccountById(long userId);
    Account getAccountByLogin(String login);
    List<Account> getAll();

    List<Idea> getBookmarks(Account account);
}
