package com.group.inventory.department.service;

import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.model.Department;
import com.group.inventory.department.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface DepartmentService extends GenericService<Department, DepartmentDTO, UUID> {
}

@Service
@Transactional
class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final BaseMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, BaseMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Department, UUID> getRepository() {
        return this.departmentRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    @Override
    public List<Department> findAll() {
        return DepartmentService.super.findAll();
    }
}