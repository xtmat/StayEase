package com.takehome.stayease.services;

import com.takehome.stayease.security.entity.Users;

import java.util.List;

public interface UserService {

    Users getUserById(Long id);

    Users aboutMe();
    
    List<Users> getUsers();
}
