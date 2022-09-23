package com.lam.jira.role.repository;

import com.lam.jira.role.model.ERole;
import com.lam.jira.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByCode(String code);
    Optional<Role> findByName(ERole name);

    @Modifying
    @Query("DELETE FROM G_ROLE r WHERE r.code = ?1")
    int deleteByCode(String code);
}
