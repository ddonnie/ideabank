package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.UserDetail;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface UserService {

    List<UserDetail> findAll();
}
