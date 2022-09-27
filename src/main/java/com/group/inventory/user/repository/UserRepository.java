package com.group.inventory.user.repository;

import com.group.inventory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT u FROM G_USER u JOIN u.roles WHERE id =:id", nativeQuery = true)
    Optional<User> findUserWithRolesById(@Param("id") UUID id);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM G_USER u WHERE u.email = ?1", nativeQuery = true)
    int deleteByEmail(String email);
}
