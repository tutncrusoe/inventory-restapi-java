package com.group.inventory.role.repository;

import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    private ERole roleName;
    private String description;

    @BeforeAll
    void setUp() {
        roleName = ERole.ROLE_ADMIN;
        description = "Admin role";
        repository.deleteAll();
    }

    @Test
    void shouldFindByNameWorkCorrectly() {
        // Given
        repository.save(Role.builder()
                .name(roleName)
                .description(description)
                .build());

        // When
        Optional<Role> roleOptional = repository.findByName(roleName);

        // Then
        assertThat(roleOptional).isPresent();
        Role role = roleOptional.get();
        assertThat(role).isNotNull();
        assertThat(role.getName()).isEqualTo(roleName);
        assertThat(role.getDescription()).isEqualTo(description);
        assertThat(role.getUsers()).isNull();
    }


    @Test
    void shouldSaveRoleWorkCorrectly() {
        // Given
        Role role = Role.builder()
                .name(roleName)
                .description(description)
                .build();

        // When
        Role saved = repository.save(role);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isZero();
        assertThat(saved.getName()).isEqualTo(roleName);
        assertThat(saved.getDescription()).isEqualTo(description);
    }

    @Test
    void shouldGetByIdWorkCorrectly(){
        // Given
        Role saved = repository.save(Role.builder()
                .name(roleName)
                .description(description)
                .build());

        UUID savedId = saved.getId();
        // When
        Optional<Role> roleOptional = repository.findById(savedId);

        // Then
        assertThat(roleOptional).isPresent();
        Role role = roleOptional.get();
        assertThat(role.getId()).isEqualTo(savedId);
        assertThat(role.getName()).isEqualTo(saved.getName());
        assertThat(role.getUsers()).isNull();
    }
}