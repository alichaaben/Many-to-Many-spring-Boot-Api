package com.Bioaqua.global.service;

import com.Bioaqua.global.dto.AppUsersDto;
import com.Bioaqua.global.entity.AppUsers;

import java.util.List;

public interface AppUsersService {

    AppUsers findById(Long id);

    List<AppUsers> findAll();

    AppUsers findByUserName(String userName);

    AppUsers insert(AppUsers entity);

    AppUsers update(AppUsers entity);

    void deleteById(Long id);

    // Nouvelle méthode pour créer un utilisateur avec des rôles
    AppUsers createUserWithRoles(AppUsersDto usersDto);

    // Nouvelle méthode pour mettre à jour un utilisateur avec des rôles
    AppUsers updateUserWithRoles(AppUsersDto usersDto);
}