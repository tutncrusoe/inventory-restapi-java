package com.group.inventory.user.repository;

import com.group.inventory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u left join fetch u.roles")
    List<User> findUserWithRoles();

    @Query(value = "select u from User u left join u.roles where u.id = ?1")
    Optional<User> findUserWithRolesById(@Param("id") UUID id);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
}
