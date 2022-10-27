package com.group.inventory.department.service;

import com.group.inventory.common.constants.ExceptionMessageConstants;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.department.dto.CreateDepartmentDTO;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.dto.DepartmentWithUserDTO;
import com.group.inventory.department.model.Department;
import com.group.inventory.department.repository.DepartmentRepository;
import com.group.inventory.user.model.User;
import com.group.inventory.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public interface DepartmentService extends GenericService<Department, DepartmentDTO, UUID> {
    DepartmentDTO save(CreateDepartmentDTO dto);

    DepartmentWithUserDTO addUsersToDepartment(UUID id, List<UUID> userIds);

    DepartmentWithUserDTO removeUsersFromDepartment(UUID id, List<UUID> userIds);

    DepartmentWithUserDTO findDepartmentWithUsers(UUID id);

    List<DepartmentWithUserDTO> findAllDepartmentWithUsers();
}

@Service
@Transactional
class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    private final UserService userService;

    private final BaseMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository repository,
                                 UserService userService,
                                 BaseMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Department, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentWithUserDTO findDepartmentWithUsers(UUID id) {
        return mapper.map(
                repository.findDepartmentWithUsersById(id),
                DepartmentWithUserDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentWithUserDTO> findAllDepartmentWithUsers() {
        List<Department> departments = repository.findAllDepartmentWithUsers();
        return departments.stream()
                .map(department -> mapper.map(department, DepartmentWithUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO save(CreateDepartmentDTO dto) {

        Department department = repository.save(
                mapper.map(dto, Department.class)
        );

        return mapper.map(
                department,
                DepartmentDTO.class
        );
    }

    @Override
    public DepartmentDTO update(DepartmentDTO dto, UUID id, Class<DepartmentDTO> dtoClazz) {
        Department department = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(ExceptionMessageConstants.DEPARTMENT_NOT_FOUND)
                );

        if (isDepartmentDTOValid(dto, id))
            department.setName(dto.getName());

        department.setDescription(dto.getDescription());

        return mapper.map(
                repository.save(department),
                dtoClazz
        );
    }

    @Override
    public DepartmentWithUserDTO addUsersToDepartment(UUID id, List<UUID> userIds) {
        Department department = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(ExceptionMessageConstants.DEPARTMENT_NOT_FOUND)
                );

        List<User> users = userService.findAllIds(userIds);

        users.forEach(department::addUser);

        return mapper.map(
                repository.save(department),
                DepartmentWithUserDTO.class
        );
    }

    @Override
    public DepartmentWithUserDTO removeUsersFromDepartment(UUID id, List<UUID> userIds) {
        Department department = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(ExceptionMessageConstants.DEPARTMENT_NOT_FOUND)
                );

        List<User> users = userService.findAllIds(userIds);
        users.forEach(department::removeUser);

        return mapper.map(
                repository.save(department),
                DepartmentWithUserDTO.class
        );
    }

    private boolean isDepartmentDTOValid(DepartmentDTO dto, UUID id) {
        Department department = repository.findById(id)
                .orElseThrow(
                        () -> new InventoryBusinessException(ExceptionMessageConstants.DEPARTMENT_NOT_FOUND)
                );

        if (Objects.equals(dto.getName(), department.getName()))
            return true;

        return repository.findByName(dto.getName()).isEmpty();
    }
}
