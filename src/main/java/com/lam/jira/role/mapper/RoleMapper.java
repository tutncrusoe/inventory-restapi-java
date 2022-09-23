package com.lam.jira.role.mapper;

import com.lam.jira.role.dto.RoleDTO;
import com.lam.jira.role.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role mapToEntity(RoleDTO dto);

    RoleDTO toRoleDTO(Role role);
}
