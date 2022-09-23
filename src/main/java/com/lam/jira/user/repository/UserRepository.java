package com.lam.jira.user.repository;

import com.lam.jira.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM G_USER u JOIN u.roles WHERE id = ?1")
    Optional<User> findUserWithRolesById(UUID id);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Modifying
    @Query("DELETE FROM G_USER u WHERE u.email = ?1")
    int deleteByEmail(String email);
}
