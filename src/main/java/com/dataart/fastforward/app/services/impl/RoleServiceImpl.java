package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.RoleRepository;
import com.dataart.fastforward.app.model.Role;
import com.dataart.fastforward.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by logariett on 23.11.2016.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(long roleId) {
        return roleRepository.getOne(roleId);
    }
}
