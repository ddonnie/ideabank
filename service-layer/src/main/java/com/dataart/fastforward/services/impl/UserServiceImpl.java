package com.dataart.fastforward.services.impl;

import com.dataart.fastforward.entity.User;
import com.dataart.fastforward.repository.UserRepository;
import com.dataart.fastforward.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
