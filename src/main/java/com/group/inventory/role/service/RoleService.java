package com.group.inventory.role.service;

import com.group.inventory.role.repository.RoleRepository;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.mapper.RoleMapper;
import com.group.inventory.role.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface RoleService {
    List<RoleDTO> findAll();

    RoleDTO findRoleById(String id);

    Role save(RoleDTO role);

    RoleDTO update(RoleDTO roleDTO, String id);

    void delete(String id);
}

@Service
@Transactional
class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleMapper.INSTANCE::toRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findRoleById(String id) {
        Optional<Role> roleOpt = roleRepository.findById(UUID.fromString(id));
        if (roleOpt.isEmpty()) {
            return null;
        }
        return RoleMapper.INSTANCE.toRoleDTO(roleOpt.get());
    }

    @Override
    public Role save(RoleDTO roleDTO) {
        Role role = RoleMapper.INSTANCE.mapToEntity(roleDTO);
        return roleRepository.save(role);
    }

    @Override
    public RoleDTO update(@Valid RoleDTO roleDTO, String id) {
        Optional<Role> curRoleOpt = roleRepository.findById(UUID.fromString(id));

        if (curRoleOpt.isEmpty()) {
            return null;
        }

        Role curRole = curRoleOpt.get();
        if (!curRole.getCode().equals(roleDTO.getCode())) {
            Optional<Role> existedRole = roleRepository.findByCode(roleDTO.getCode());
            if (existedRole.isPresent()) {
                return null;
            }

            curRole.setCode(roleDTO.getCode());
        }

        curRole.setDescription(roleDTO.getDescription());
        Role role = roleRepository.save(curRole);
        return RoleMapper.INSTANCE.toRoleDTO(role);
    }

    @Override
    public void delete(String id) {
         roleRepository.deleteById(UUID.fromString(id));
    }
}
