package com.lam.jira.user.mapper;

import com.lam.jira.user.dto.RequestUserDTO;
import com.lam.jira.user.dto.UserDTO;
import com.lam.jira.user.dto.UserWithRoleDTO;
import com.lam.jira.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToEntity(UserDTO userDTO);

    User mapToEntity(RequestUserDTO requestUserDTO);

    User mapToEntityWithRoles(UserWithRoleDTO userWithRoleDTO);

    UserDTO toUserDTO(User user);

    UserWithRoleDTO toUserWithRoleDTO(User user);
}
