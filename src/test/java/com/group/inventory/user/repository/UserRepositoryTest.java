package com.group.inventory.user.repository;

import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.user.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    
    @BeforeAll
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveUserWorkCorrectly() {
        User user = User.builder()
                .username("username")
                .password("password")
                .email("email")
                .build();

        User saved = userRepository.save(user);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("username");
        assertThat(saved.getPassword()).isEqualTo("password");
        assertThat(saved.getEmail()).isEqualTo("email");
    }

    @Test
    void shouldFindUserByIdWorkCorrectly() {
        User user = User.builder()
                .username("username")
                .password("password")
                .email("email")
                .build();

        User saved = userRepository.save(user);
        UUID id = saved.getId();

        Optional<User> found = userRepository.findById(id);

        assertThat(found).isPresent();

        assertThat(found.get().getUsername()).isEqualTo("username");
        assertThat(found.get().getPassword()).isEqualTo("password");
        assertThat(found.get().getEmail()).isEqualTo("email");
        assertThat(found.get().getId()).isEqualTo(id);
    }

    @Test
    void shouldFindUserByEmailWorkCorrectly() {
        User user = User.builder()
                .username("username")
                .password("password")
                .email("email")
                .build();

        User saved = userRepository.save(user);
        String email = saved.getEmail();

        Optional<User> found = userRepository.findByEmail("email");

        assertThat(found).isPresent();

        assertThat(found.get().getUsername()).isEqualTo("username");
        assertThat(found.get().getPassword()).isEqualTo("password");
        assertThat(found.get().getEmail()).isEqualTo(email);
    }

    @Test
    void shouldFindUserWithRoleWorkCorrectly() {
        Role role1 = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .description("description")
                .users(new LinkedHashSet<>())
                .build();

        Role role2 = Role.builder()
                .name(ERole.ROLE_USER)
                .description("description")
                .users(new LinkedHashSet<>())
                .build();

        User user1 = User.builder()
                .username("user 1")
                .password("password")
                .email("email1")
                .roles(Set.of(role1, role2))
                .build();

        User user2 = User.builder()
                .username("user 2")
                .password("password")
                .email("email2")
                .roles(Set.of(role1))
                .build();

        User user3 = User.builder()
                .username("user 3")
                .password("password")
                .email("email3")
                .roles(Set.of(role2))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> users = new ArrayList<>(userRepository.findUserWithRoles());

        Optional<User> found = users.stream()
                .filter(user -> user.getUsername().equals("user 1"))
                .findFirst();


        assertThat(users).hasSize(3);
        assertThat(users.get(0).getRoles()).isNotEmpty();
        assertThat(users.get(1).getRoles()).isNotEmpty();
        assertThat(users.get(2).getRoles()).isNotEmpty();

        assertThat(found).isPresent();
        assertThat(found.get().getRoles()).hasSize(2);
    }

    @Test
    void shouldFindUserWithRolesByIdWorkCorrectly() {
        Role role1 = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .description("description")
                .users(new LinkedHashSet<>())
                .build();

        Role role2 = Role.builder()
                .name(ERole.ROLE_USER)
                .description("description")
                .users(new LinkedHashSet<>())
                .build();

        User user = User.builder()
                .username("Username")
                .password("password")
                .email("email")
                .roles(Set.of(role1, role2))
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> found = userRepository.findUserWithRolesById(savedUser.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isNotNull();
        assertThat(found.get().getRoles()).hasSize(2);
        assertThat(found.get().getId()).isEqualTo(savedUser.getId());

    }

    @Test
    void shouldFindAllUsersWorkCorrectly() {

        User user1 = userRepository.save(User.builder()
                .username("user 1")
                .password("password")
                .email("user1@email.com")
                .build());

        User user2 = userRepository.save(User.builder()
                .username("user 2")
                .password("password")
                .email("user2@email.com")
                .build());

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }

    @Test
    void shouldCheckUserExistedByUsernameWorkCorrectly() {
        String username = "username";
        User user = User.builder()
                .username(username)
                .password("password")
                .email("email")
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByUsername(username)).isTrue();
        assertThat(userRepository.existsByUsername("not found")).isFalse();
    }
}