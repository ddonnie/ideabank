package com.dataart.fastforward.services.impl;

import com.dataart.fastforward.entity.UserDetail;
import com.dataart.fastforward.repository.UserDetailRepository;
import com.dataart.fastforward.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetail addUserDetail(UserDetail userDetail) {
        UserDetail savedUserDetail = userDetailRepository.saveAndFlush(userDetail);

        return savedUserDetail;
    }

    @Override
    public void delete(long userId) {
        userDetailRepository.delete(userId);
    }

    @Override
    public UserDetail getUserById(long userId) {
        return userDetailRepository.findOne(userId);
    }

    @Override
    public UserDetail editUserDetail(UserDetail userDetail) {
        return userDetailRepository.saveAndFlush(userDetail);
    }

    @Override
    public List<UserDetail> getAll() {
        return userDetailRepository.findAll();
    }

}
