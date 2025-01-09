package com.Bioaqua.global.service.impl;

import com.Bioaqua.global.dto.AppUsersDto;
import com.Bioaqua.global.entity.AppUsers;
import com.Bioaqua.global.entity.Roles;
import com.Bioaqua.global.mapper.AppUsersMapper;
import com.Bioaqua.global.repository.AppUsersRepo;
import com.Bioaqua.global.repository.RolesRepo;
import com.Bioaqua.global.service.AppUsersService;
import com.Bioaqua.global.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUsersServiceImpl implements AppUsersService {

    private final AppUsersRepo appUsersRepo;
    private final RolesRepo rolesRepo;
    private final AppUsersMapper usersMapper;

    @Override
    public AppUsers findById(Long id) {
        return appUsersRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<AppUsers> findAll() {
        return appUsersRepo.findAll();
    }

    @Override
    public AppUsers findByUserName(String userName) {
        return appUsersRepo.findByUserName(userName);
    }

    @Override
    public AppUsers insert(AppUsers entity) {
        return appUsersRepo.save(entity);
    }

    @Override
    public AppUsers update(AppUsers entity) {
        AppUsers currentUser = appUsersRepo.findById(entity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + entity.getId()));

        currentUser.setUserName(entity.getUserName());
        currentUser.setEmail(entity.getEmail());
        currentUser.setTelephone(entity.getTelephone());
        currentUser.setPassword(entity.getPassword());

        return appUsersRepo.save(currentUser);
    }

    @Override
    public void deleteById(Long id) {
        if (!appUsersRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        appUsersRepo.deleteById(id);
    }

    @Override
    public AppUsers createUserWithRoles(AppUsersDto usersDto) {
        // Convertir le DTO en entité
        AppUsers user = usersMapper.unMap(usersDto);

        // Vérifier si roleNames est null
        Set<String> roleNames = usersDto.getRoleNames();
        if (roleNames == null) {
            roleNames = new HashSet<>(); // Initialiser avec une collection vide
        }

        // Trouver les rôles correspondants aux noms de rôles dans le DTO
        Set<Roles> roles = roleNames.stream()
                .map(roleName -> rolesRepo.findByRoleName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName)))
                .collect(Collectors.toSet());

        // Associer les rôles à l'utilisateur
        user.setRoles(roles);

        // Sauvegarder l'utilisateur
        return appUsersRepo.save(user);
    }

    @Override
    public AppUsers updateUserWithRoles(AppUsersDto usersDto) {
        // Trouver l'utilisateur existant
        AppUsers existingUser = appUsersRepo.findById(usersDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + usersDto.getId()));

        // Mettre à jour les champs de base
        existingUser.setUserName(usersDto.getUserName());
        existingUser.setEmail(usersDto.getEmail());
        existingUser.setTelephone(usersDto.getTelephone());

        // Vérifier si roleNames est null
        Set<String> roleNames = usersDto.getRoleNames();
        if (roleNames != null) {
            // Mettre à jour les rôles si nécessaire
            Set<Roles> roles = roleNames.stream()
                    .map(roleName -> rolesRepo.findByRoleName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName)))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        // Mettre à jour le mot de passe si nécessaire
        if (usersDto.getPassword() != null && !usersDto.getPassword().isEmpty()) {
            existingUser.setPassword(usersDto.getPassword());
        }

        // Sauvegarder les modifications
        return appUsersRepo.save(existingUser);
    }
}