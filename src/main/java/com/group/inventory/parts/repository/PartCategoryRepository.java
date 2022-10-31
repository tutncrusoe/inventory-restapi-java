package com.group.inventory.parts.repository;

import com.group.inventory.parts.model.PartCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PartCategoryRepository extends JpaRepository<PartCategory, UUID> {
    Optional<PartCategory> findByName(String name);

    @Query("select pc from PartCategory pc left join fetch pc.partSessions")
    Set<PartCategory> findAllIncludeSessions();
}
