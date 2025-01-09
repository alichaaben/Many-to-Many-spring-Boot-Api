package com.Bioaqua.global.service.impl;


import com.Bioaqua.global.Exceptions.ResourceNotFoundException;
import com.Bioaqua.global.entity.Roles;
import com.Bioaqua.global.repository.RolesRepo;
import com.Bioaqua.global.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepo rolesRepo;

    @Override
    public Roles findById(Long id) {
        return rolesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found with ID: " + id));
    }

    @Override
    public List<Roles> findAll() {
        return rolesRepo.findAll();
    }

    @Override
    public Roles findByRoleName(String roleName) {
        return rolesRepo.findByRoleName(roleName)
        .orElseThrow(() -> new RuntimeException("role not found with Role Name: " + roleName));

    }

    @Override
    public Roles insert(Roles entity) {
        if (entity.getRoleName() == null || entity.getRoleName().isBlank()) {
            throw new IllegalArgumentException("Role name cannot be null or empty.");
        }

        String roleName = "ROLE_" + entity.getRoleName().trim();
        entity.setRoleName(roleName);

        return rolesRepo.save(entity);
    }

    @Override
    public Roles update(Roles entity) {
        Roles currentRole = rolesRepo.findById(entity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found with ID: " + entity.getId()));

        if (entity.getRoleName() != null && !entity.getRoleName().isBlank()) {
            currentRole.setRoleName("ROLE_" + entity.getRoleName().trim());
        }

        return rolesRepo.save(currentRole);
    }

    @Override
public void deleteById(Long id) {
    Roles role = rolesRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Roles not found with ID: " + id));

    if (!role.getUsers().isEmpty()) {
        throw new IllegalStateException("Cannot delete role with ID " + id + " because it has associated users.");
    }

    rolesRepo.delete(role);
}



}
