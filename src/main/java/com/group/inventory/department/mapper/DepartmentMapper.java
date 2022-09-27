package com.group.inventory.department.mapper;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public interface DepartmentMapper extends BaseMapper<Department, DepartmentDTO> {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
}
