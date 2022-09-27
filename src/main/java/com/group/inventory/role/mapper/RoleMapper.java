package com.group.inventory.role.mapper;

import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role mapToEntity(RoleDTO dto);

    RoleDTO toRoleDTO(Role role);
}
