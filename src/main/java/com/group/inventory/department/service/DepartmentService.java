package com.group.inventory.department.service;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.mapper.DepartmentMapper;
import com.group.inventory.department.model.Department;
import com.group.inventory.department.repository.DepartmentRepository;
import org.springframework.data.domain.Pageable;
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

    private DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public JpaRepository<Department, UUID> getRepository() {
        return this.departmentRepository;
    }

    @Override
    public BaseMapper<Department, DepartmentDTO> getMapper() {
        return this.departmentMapper.INSTANCE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        return DepartmentService.super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll(Pageable pageable) {
        return DepartmentService.super.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO findById(UUID id) {
        return DepartmentService.super.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAllDTO(Pageable pageable) {
        return DepartmentService.super.findAllDTO(pageable);
    }
}