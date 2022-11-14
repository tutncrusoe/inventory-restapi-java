package com.group.inventory.storage.repository;

import com.group.inventory.storage.model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    Optional<Warehouse> findByName(String name);

    Page<Warehouse> findAll(Pageable pageable);

    @Query(value = "select distinct warehouse from Warehouse warehouse left join fetch warehouse.locations",
            countQuery = "select count(warehouse) from Warehouse warehouse")
    Page<Warehouse> findAllWithLocations(Pageable pageable);
}
