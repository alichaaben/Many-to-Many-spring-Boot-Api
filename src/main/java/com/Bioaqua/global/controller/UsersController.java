package com.Bioaqua.global.controller;

import com.Bioaqua.global.dto.AppUsersDto;
import com.Bioaqua.global.entity.Roles;
import com.Bioaqua.global.entity.AppUsers;
import com.Bioaqua.global.mapper.AppUsersMapper;
import com.Bioaqua.global.repository.RolesRepo;
import com.Bioaqua.global.service.AppUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Bioaqua.global.Exceptions.ResourceNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsersController {

    private final AppUsersService usersService;
    private final AppUsersMapper usersMapper;
    private final RolesRepo rolesRepo;

    @GetMapping("/{id}")
    public ResponseEntity<AppUsersDto> findById(@PathVariable Long id) {
        AppUsers user = usersService.findById(id);
        return ResponseEntity.ok(usersMapper.map(user));
    }

    @GetMapping
    public ResponseEntity<List<AppUsersDto>> findAll() {
        List<AppUsers> users = usersService.findAll();
        return ResponseEntity.ok(usersMapper.map(users));
    }

    @PostMapping
    public ResponseEntity<AppUsersDto> createUser(@RequestBody AppUsersDto usersDto) {
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
        AppUsers savedUser = usersService.insert(user);

        // Convertir l'entité sauvegardée en DTO
        AppUsersDto savedUsersDto = usersMapper.map(savedUser);

        return ResponseEntity.ok(savedUsersDto);
    }

    @PutMapping
    public ResponseEntity<AppUsersDto> updateUser(@RequestBody AppUsersDto usersDto) {
        // Trouver l'utilisateur existant
        AppUsers existingUser = usersService.findById(usersDto.getId());

        // Mettre à jour les champs de base
        existingUser.setUserName(usersDto.getUserName());
        existingUser.setEmail(usersDto.getEmail());

        // Vérifier si roleNames est null
        Set<String> roleNames = usersDto.getRoleNames();
        if (roleNames != null) {
            // Mettre à jour les rôles si nécessaire
            Set<Roles> roles = roleNames.stream()
                // .map(roleName -> rolesRepo.findByRoleName("ROLE_" + roleName)
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
        AppUsers updatedUser = usersService.update(existingUser);

        // Convertir l'entité mise à jour en DTO
        AppUsersDto updatedUsersDto = usersMapper.map(updatedUser);
        return ResponseEntity.ok(updatedUsersDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}