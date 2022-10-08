package com.group.inventory.common.service;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.common.model.BaseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends BaseEntity, D, I> {

    // 1. Attributes
    JpaRepository<T, I> getRepository();

    BaseMapper<T, D> getMapper();

    // 2. FindAll T
    default List<T> findAll() {
        return getRepository().findAll();
    }

    default List<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable)
                .stream()
                .toList();
    }

    // 3. FindAll D
    default List<D> findAllDTO() {
        return getRepository().findAll()
                .stream()
                .map(getMapper()::mapToDTO)
                .toList();
    }

    default List<D> findAllDTO(Pageable pageable) {
        return getRepository().findAll(pageable)
                .stream()
                .map(getMapper()::mapToDTO)
                .toList();
    }

    // 4. FindBy
    default D findById(I id) {
        Optional<T> entityOpt = getRepository().findById(id);

        if (entityOpt.isEmpty()) {
            return null;
        }

        return getMapper().mapToDTO(entityOpt.get());
    }

    default List<T> findByIds(List<I> ids) {
        return getRepository().findAllById(ids);
    }

    // 5. save
    default D save(D dto) {
        T entity = getMapper().mapToEntity(dto);
        T newEntity = getRepository().save(entity);
        return getMapper().mapToDTO(newEntity);
    }

    // 6. update
    default D update(D dto, I id) {
        T entity = getMapper().mapToEntity(dto);
        T newEntity = getRepository().save(entity);
        return getMapper().mapToDTO(newEntity);
    }

    // 7. delete
    default void deleteById(I id) {
        getRepository().deleteById(id);
    }


}
