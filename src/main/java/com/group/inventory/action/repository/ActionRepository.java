package com.group.inventory.action.repository;

import com.group.inventory.action.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<Action, UUID> {
    @Query("select ac from Action ac left join fetch ac.user left join ac.partDetails where ac.id = ?1")
    Optional<Action> findAllIncludeUserAndPartDetails(@Param("id") UUID id);
}
