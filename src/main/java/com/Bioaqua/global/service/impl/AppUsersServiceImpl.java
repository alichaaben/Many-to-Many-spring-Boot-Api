package com.Bioaqua.global.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Bioaqua.global.entity.AppUsers;
import com.Bioaqua.global.repository.AppUsersRepo;
import com.Bioaqua.global.service.AppUsersService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUsersServiceImpl implements AppUsersService {

    private final AppUsersRepo appUsersRepo;

    @Override
    public AppUsers findById(Long id) {
        return appUsersRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
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
    public AppUsers update(AppUsers Entity) {
        AppUsers currentUser = appUsersRepo.findById(Entity.getId())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        currentUser.setUserName(Entity.getUserName());
        currentUser.setEmail(Entity.getEmail());
        currentUser.setTelephone(Entity.getTelephone());
        currentUser.setPassword(Entity.getPassword());
        
        return appUsersRepo.save(currentUser);
    }

    @Override
    public void deleteById(Long id) {
        appUsersRepo.deleteById(id);
    }


}
