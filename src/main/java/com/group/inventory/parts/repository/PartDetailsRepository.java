package com.group.inventory.parts.repository;

import com.group.inventory.parts.model.PartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PartDetailsRepository extends JpaRepository<PartDetails, UUID> {

    @Query("select pd from PartDetails pd left join pd.partSession")
    Set<PartDetails> findAllIncludeSession();

    Optional<PartDetails> findByPartNumber(String partNumber);
}
