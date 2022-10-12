package com.group.inventory.role.service;

import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.role.repository.RoleRepository;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface RoleService extends GenericService<Role, RoleDTO, UUID> {

    // method
    List<RoleDTO> findAllDTO();

    RoleDTO findRoleById(String id);

    RoleDTO save(RoleDTO roleDTO);

    RoleDTO update(RoleDTO roleDTO, String id);

    void delete(String id);
}

@Service
@Transactional
class RoleServiceImpl implements RoleService {

    // 1. Attributes
    private final RoleRepository roleRepository;

    private final BaseMapper roleMapper;

    // 2. Constructors
    public RoleServiceImpl(RoleRepository roleRepository, BaseMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    // 3. Method
    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return this.roleRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.roleMapper;
    }

    // 4. FindAll
    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findAllDTO() {
        return getRepository().findAll()
                .stream()
                .map(entity -> getModelMapper().map(entity, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findRoleById(String id) {
        Optional<Role> roleOpt = roleRepository.findById(UUID.fromString(id));
        if (roleOpt.isEmpty()) {
            return null;
        }
        return roleMapper.map(roleOpt, RoleDTO.class);
    }

    // 5. save
    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role role = roleMapper.map(roleDTO, Role.class);
        Role savedRole = roleRepository.save(role);
        return roleMapper.map(savedRole, RoleDTO.class);
    }

    // 6. update
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
        return roleMapper.map(role, RoleDTO.class);
    }

    // 7. delete
    @Override
    public void delete(String id) {
        roleRepository.deleteById(UUID.fromString(id));
    }
}
