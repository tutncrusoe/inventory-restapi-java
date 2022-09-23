package com.lam.jira.role.service;

import com.lam.jira.role.dto.RoleDTO;
import com.lam.jira.role.mapper.RoleMapper;
import com.lam.jira.role.model.Role;
import com.lam.jira.role.repository.RoleRepository;
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

    int delete(String code);
}

@Service
@Transactional
class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

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
    public int delete(String code) {
        return roleRepository.deleteByCode(code);
    }
}
