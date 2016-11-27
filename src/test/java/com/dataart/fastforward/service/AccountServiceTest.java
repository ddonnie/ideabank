package com.dataart.fastforward.service;

import com.dataart.fastforward.app.dto.NewAccountDTO;
import com.dataart.fastforward.app.services.AccountService;
import com.dataart.fastforward.config.root.DbConfig;
import com.dataart.fastforward.config.servlet.WebMvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

/**
 * Created by logariett on 25.11.2016.*/


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={DbConfig.class, WebMvcConfig.class})
public class AccountServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    AccountService accountService;

    @Test
    public void testGetAccountById() {
        System.out.println(accountService.getAccountById(1));
    }

    @Test
    public void testDataSource(){
        System.out.println(dataSource);
    }

    @Test
    public void testCreateAccount() {
        NewAccountDTO newAccountDTO = new NewAccountDTO();
        newAccountDTO.setFirstName("TestFirstName");
        newAccountDTO.setLastName("TestLastName");
        newAccountDTO.setLogin("TestLogin");
        newAccountDTO.setPassword("TestPassword");
        accountService.createAccount(newAccountDTO);
        System.out.println(accountService.getAccountByLogin(newAccountDTO.getLogin()));
    }
}
