package com.group.inventory.role.service;

import com.group.inventory.common.service.GenericService;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;

import java.util.UUID;

public interface RoleService extends GenericService<Role, RoleDTO, UUID> {
    Role findByName(ERole roleUser);
}

