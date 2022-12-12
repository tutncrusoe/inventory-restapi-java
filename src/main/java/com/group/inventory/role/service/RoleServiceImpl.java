package com.group.inventory.role.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    //    -----------------------       ATTRIBUTES       ----------------------
    private final RoleRepository roleRepository;

    private final BaseMapper mapper;

    //    -----------------------       CONSTRUCTOR       ----------------------
    public RoleServiceImpl(RoleRepository roleRepository, BaseMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    //    -----------------------       METHODS       ----------------------
    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return this.roleRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    @Override
    public Role findByName(ERole roleUser) {
        return roleRepository.findByName(roleUser)
                .orElseThrow(() -> new InventoryBusinessException("Role is not existed."));
    }

    @Override
    public RoleDTO save(RoleDTO dto, Class<Role> clazz, Class<RoleDTO> dtoClazz) {
        Role role = mapper.map(dto, Role.class);
        Role savedRole = roleRepository.save(role);
        return mapper.map(savedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO update(RoleDTO dto, UUID id, Class<RoleDTO> dtoClazz) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new InventoryBusinessException("Role is not existed."));
        role.setDescription(dto.getDescription());
        return mapper.map(role, dtoClazz);
    }
}
