package com.Bioaqua.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUsersDto {

    private Long id;
    private String userName;
    private String email;
    private String telephone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//pour masque PW lors du la serialisation
    private String password;
    
    private Set<String> roleNames = new HashSet<>();
}