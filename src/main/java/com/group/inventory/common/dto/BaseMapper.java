package com.group.inventory.common.dto;

import com.group.inventory.common.model.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseMapper<T extends BaseEntity, D> {
    T mapToEntity(D dto);

    D mapToDTO(T entity);
}
