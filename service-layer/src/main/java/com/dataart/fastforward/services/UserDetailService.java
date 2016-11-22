package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.UserDetail;

import java.util.List;

/**
 * Created by logariett on 19.11.2016.
 */
public interface UserDetailService {

    UserDetail addUserDetail(UserDetail userDetail);
    void delete(long userId);
    UserDetail getByUserId(long userId);
    UserDetail editUserDetail(UserDetail userDetail);
    List<UserDetail> getAll();
}
