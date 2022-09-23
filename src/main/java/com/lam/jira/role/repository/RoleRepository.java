package com.lam.jira.role.repository;

import com.lam.jira.role.model.ERole;
import com.lam.jira.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByCode(String code);
    Optional<Role> findByName(ERole name);

    @Modifying
    @Query(value = "DELETE FROM G_ROLE r WHERE r.code =:code", nativeQuery = true)
    int deleteByCode(@Param("code") String code);
}
