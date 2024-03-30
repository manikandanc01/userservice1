package com.example.userservice.service;

import com.example.userservice.models.Role;
import com.example.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name)
    {
        Role role=new Role();
        role.setRole(name);

       return roleRepository.save(role);
    }
}
