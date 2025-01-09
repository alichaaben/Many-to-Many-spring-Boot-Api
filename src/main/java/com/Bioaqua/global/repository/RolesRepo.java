package com.Bioaqua.global.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Bioaqua.global.entity.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleName(String roleName);
}
