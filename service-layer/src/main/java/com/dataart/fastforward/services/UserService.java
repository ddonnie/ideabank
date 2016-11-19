package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface UserService {
    User addUser(User user);
    List<User> findAll();
}
