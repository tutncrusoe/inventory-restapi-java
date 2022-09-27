package com.group.inventory.common.service;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.common.model.BaseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends BaseEntity, D, I>{
    JpaRepository<T, I> getRepository();

    BaseMapper<T, D> getMapper();

    default List<D> findAll() {
        return getRepository().findAll().stream()
                .map(getMapper()::mapToDTO)
                .toList();
    };

    default List<D> findAll(Pageable pageable) {
        return getRepository().findAll(pageable)
                .stream()
                .map(getMapper()::mapToDTO)
                .toList();
    }

    default D findById(I id) {
        Optional<T> entityOpt = getRepository().findById(id);

        if (entityOpt.isEmpty()) {
            return null;
        }

        return getMapper().mapToDTO(entityOpt.get());
    }

    default List<D> findAllDTO(Pageable pageable) {
        return getRepository().findAll(pageable)
                .stream()
                .map(getMapper()::mapToDTO)
                .toList();
    }

    default D save(D dto) {
        T entity = getMapper().mapToEntity(dto);
        T newEntity = getRepository().save(entity);
        return getMapper().mapToDTO(newEntity);
    }

    default void deleteById(I id) {
        getRepository().deleteById(id);
    }

    default D update(D dto, I id) {
        T entity = getMapper().mapToEntity(dto);
        T newEntity = getRepository().save(entity);
        return getMapper().mapToDTO(newEntity);
    }
}
