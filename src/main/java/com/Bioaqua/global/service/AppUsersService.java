package com.Bioaqua.global.service;

import java.util.List;

import com.Bioaqua.global.entity.AppUsers;

public interface AppUsersService {

    AppUsers findById(Long id);

    List<AppUsers> findAll();

    AppUsers findByUserName(String userName);

    AppUsers insert(AppUsers Entity);

    AppUsers update(AppUsers Entity);

    void deleteById(Long id);
    
}
