package com.group.inventory.storage.repository;

import com.group.inventory.storage.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    @Query(value = "select location from Location location left join fetch location.storages",
            countQuery = "select count(location) from Location location")
    Page<Location> findAllWithStorages(Pageable pageable);

    @Query(value = "select location from Location location left join fetch location.storages where location.id = ?1")
    Location findLocationWithStoragesById(UUID id);

    Optional<Location> findByName(String name);
}
