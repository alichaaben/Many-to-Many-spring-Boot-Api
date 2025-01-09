package com.Bioaqua.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Bioaqua.global.entity.AppUsers;

@Repository
public interface AppUsersRepo extends JpaRepository<AppUsers,Long>{
    AppUsers findByUserName(String userName);

}
