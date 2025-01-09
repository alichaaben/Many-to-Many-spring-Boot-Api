package com.Bioaqua.global.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.Bioaqua.global.dto.AppUsersDto;
import com.Bioaqua.global.entity.AppUsers;
import com.Bioaqua.global.entity.Roles;

@Mapper(componentModel = "spring")
public interface AppUsersMapper {

    // Mapping AppUsers -> AppUsersDto
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "roleNames", expression = "java(mapRoles(entity.getRoles()))")
    AppUsersDto map(AppUsers entity);

    // Mapping List<AppUsers> -> List<AppUsersDto>
    List<AppUsersDto> map(List<AppUsers> entities);

    // Mapping AppUsersDto -> AppUsers
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "roles", ignore = true) // Ignorer les rôles lors de la création
    AppUsers unMap(AppUsersDto dto);

    // Mapping AppUsersDto -> AppUsers pour mise à jour
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "roles", ignore = true) // Ignorer les rôles lors de la mise à jour
    void updateEntityFromDto(@MappingTarget AppUsers entity, AppUsersDto dto);

    // Méthode pour mapper les rôles vers une liste de noms de rôles
    default Set<String> mapRoles(Set<Roles> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Roles::getRoleName)
                .collect(Collectors.toSet());
    }
}