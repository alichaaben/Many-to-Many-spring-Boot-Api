package com.Bioaqua.global.controller;

import com.Bioaqua.global.dto.AppUsersDto;
import com.Bioaqua.global.entity.AppUsers;
import com.Bioaqua.global.mapper.AppUsersMapper;
import com.Bioaqua.global.service.AppUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsersController {

    private final AppUsersService usersService;
    private final AppUsersMapper usersMapper;

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
        AppUsers savedUser = usersService.createUserWithRoles(usersDto);
        return ResponseEntity.ok(usersMapper.map(savedUser));
    }

    @PutMapping
    public ResponseEntity<AppUsersDto> updateUser(@RequestBody AppUsersDto usersDto) {
        AppUsers updatedUser = usersService.updateUserWithRoles(usersDto);
        return ResponseEntity.ok(usersMapper.map(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        usersService.deleteById(id);
        return ResponseEntity.ok("User with ID " + id + " successfully deleted.");
    }

}