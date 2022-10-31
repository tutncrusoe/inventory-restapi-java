package com.group.inventory.parts.repository;

import com.group.inventory.parts.model.PartSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PartSessionRepository extends JpaRepository<PartSession, UUID> {
    Optional<PartSession> findByName(String name);

    @Query("select ps from PartSession ps left join fetch ps.partDetailsList left join ps.partCategory")
    Set<PartSession> findAllIncludeCategoryAndDetails();

    Optional<PartSession> findByPartNumber(String partNumber);
}

