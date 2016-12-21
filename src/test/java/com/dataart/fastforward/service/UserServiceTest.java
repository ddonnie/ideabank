package com.dataart.fastforward.service;

import com.dataart.fastforward.app.dto.NewUserDTO;
import com.dataart.fastforward.app.services.UserService;
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
public class UserServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    UserService userService;

    @Test
    public void testGetAccountById() {
//        System.out.println(userService.getUserById(1));
    }

    @Test
    public void testGetUserByUsername() {
//        System.out.println(userService.getUserByUsername("Patty"));
    }

    @Test
    public void testDataSource(){
//        System.out.println(dataSource);
    }

    @Test
    public void testCreateAccount() {
       /* NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setFirstName("TestFirstName");
        newUserDTO.setLastName("TestLastName");
        newUserDTO.setUsername("TestLogin");
        newUserDTO.setPassword("TestPassword");
        userService.createUser(newUserDTO);
        System.out.println(userService.getUserByUsername(newUserDTO.getUsername()));*/
    }

 /*    @Test
   public void getBookmarksTest() {
        System.out.println(userService.getBookmarks(userService.getUserById(1)));
    }*/

    @Test
    public void findNonExistLoginTest() {
/*        if (userService.getUserByUsername("ASS")==null) {
            System.out.println("Not found");
        }
        else {
            System.out.println("Found.");
        }*/
    }
}
