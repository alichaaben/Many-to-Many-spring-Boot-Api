package com.Bioaqua.global.service;

import java.util.List;

import com.Bioaqua.global.entity.Roles;

public interface RolesService {
    Roles findById(Long id);

    List<Roles> findAll();

    Roles findByRoleName(String roleName);

    Roles insert(Roles Entity);

    Roles update(Roles Entity);

    void deleteById(Long id);
}
