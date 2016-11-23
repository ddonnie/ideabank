package com.dataart.fastforward.services.impl;

import com.dataart.fastforward.entity.Role;
import com.dataart.fastforward.repository.RoleRepository;
import com.dataart.fastforward.services.RoleService;
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
