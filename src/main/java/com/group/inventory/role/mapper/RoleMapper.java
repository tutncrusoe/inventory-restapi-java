package com.group.inventory.role.mapper;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface RoleMapper extends BaseMapper<Role, RoleDTO> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role mapToEntity(RoleDTO dto);

    @InheritInverseConfiguration
    RoleDTO toRoleDTO(Role role);
}
