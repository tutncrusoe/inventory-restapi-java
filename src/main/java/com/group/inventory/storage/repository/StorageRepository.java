package com.group.inventory.storage.repository;

import com.group.inventory.storage.model.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageRepository extends JpaRepository<Storage, UUID> {
    Optional<Storage> findByName(String name);

    Optional<Storage> findByCode(String name);

    @Query("select storage from Storage storage left join fetch storage.partDetails where storage.id = ?1")
    Storage findStorageWithPartsById(UUID id);

    @Query(value = "select distinct storage from Storage storage left join fetch storage.partDetails",
            countQuery = "select count(storage.id) from Storage storage")
    Page<Storage> findAllWithParts(Pageable pageable);
}
