package com.group.inventory.user.service;

import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.config.ValidationConfiguration;
import com.group.inventory.department.service.DepartmentService;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.service.RoleService;
import com.group.inventory.user.dto.UserDTORequest;
import com.group.inventory.user.dto.UserDTOResponse;
import com.group.inventory.user.dto.UserWithRolesDTOResponse;
import com.group.inventory.user.model.User;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestConfiguration
@Import({ValidationConfiguration.class})
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ImageService imageService;

    @Mock
    private RoleService roleService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private BaseMapper mapper;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(UUID.randomUUID())
                .username("username")
                .email("email")
                .password("password")
                .status(UserStatus.ACTIVE)
                .roles(new LinkedHashSet<>())
                .build();
    }

    @Test
    void shouldGetRepositoryWorkCorrectly() {
        assertThat(userService.getRepository()).isNotNull();
    }

    @Test
    void shouldGetMapperWorkCorrectly() {
        assertThat(userService.getModelMapper()).isNotNull();
    }

    @Test
    void shouldFindAllUserWorkCorrectly() {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);

        when(repository.findAll())
                .thenReturn(users);

        when(userService.createUserDTOResponse(
                user, request))
                .thenReturn(UserDTOResponse.builder().build());

        when(userService.createUserDTOResponse(user, request))
                .thenReturn(UserDTOResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
                );

        List<UserDTOResponse> result = userService.findAllUser(request);

        assertThat(result).hasSize(users.size());
    }

    @Test
    void shouldFindUserByIdWorkCorrectly() {

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        when(mapper.map(user, UserDTOResponse.class))
                .thenReturn(new UserDTOResponse());

        when(userService.createUserDTOResponse(user, request))
                .thenReturn(UserDTOResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
                );

        UserDTOResponse result = userService.findUserById(user.getId(), request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void shouldFindUserWithRoleWorkCorrectly() {
        when(repository.findUserWithRoles()).thenReturn(List.of(user));

        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(new UserWithRolesDTOResponse());

        List<UserWithRolesDTOResponse> users = userService.findUserWithRoles(request);

        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();

    }

    @Test
    void shouldFindUserWithRoleByIdWorkCorrectly() {
        when(repository.findUserWithRolesById(user.getId())).thenReturn(Optional.of(user));

        UserWithRolesDTOResponse response = UserWithRolesDTOResponse.builder()
                .username(user.getUsername())
                .build();

        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(response);


        UserWithRolesDTOResponse userWithRolesDTOResponse = userService.findUserWithRolesById(user.getId(), request);
        assertThat(userWithRolesDTOResponse).isNotNull();
        assertThat(userWithRolesDTOResponse.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void shouldGetRolesFromUserWorkCorrectly() {
        // find user by id
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        // mapp user to user with role => get roles
        // create user with role dto
        UserWithRolesDTOResponse response = UserWithRolesDTOResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(new LinkedHashSet<>())
                .build();
        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(response);

        Set<RoleDTO> roleDTOS = userService.getRolesFromUser(user.getId());

        assertThat(roleDTOS).isNotNull();
        assertThat(roleDTOS).isEmpty();


    }

    @Test
    void shouldCreateNewUserWorkCorrectly() {
        UserDTORequest userRequest = UserDTORequest.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.getStatus())
                .departmentId(UUID.randomUUID())
                .build();
        // map to user
        when(mapper.map(userRequest, User.class)).thenReturn(user);

        // encode
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("Password encoded");

        // save user
        when(repository.save(any(User.class))).thenReturn(user);

        // find role by name
        when(roleService.findByName(ERole.ROLE_USER)).thenReturn(new Role());

        // create user with role dto
        UserWithRolesDTOResponse response = UserWithRolesDTOResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(new LinkedHashSet<>())
                .build();

        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(response);

        UserWithRolesDTOResponse saved = userService.createNewUser(userRequest, request);

        assertThat(saved).isNotNull();
        assertThat(saved.getUsername()).isEqualTo(response.getUsername());
    }

    @Test
    void shouldAddRolesWorkCorrectly() {
        // find user by id
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UUID roleId = UUID.randomUUID();
        List<Role> roles = List.of(Role.builder().id(roleId).users(new LinkedHashSet<>()).build());
        // find all roles by id
        when(roleService.findAllIds(List.of(roleId))).thenReturn(roles);

        // create user with role dto
        UserWithRolesDTOResponse response = UserWithRolesDTOResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(new LinkedHashSet<>())
                .build();

        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(response);

        UserWithRolesDTOResponse userWithRolesDTOResponse = userService.addRoles(user.getId(), List.of(roleId), request);

        assertThat(userWithRolesDTOResponse).isNotNull();
        assertThat(userWithRolesDTOResponse.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void shouldRemoveRolesWorkCorrectly() {
        // find user by id
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UUID roleId = UUID.randomUUID();
        List<Role> roles = List.of(Role.builder().id(roleId).users(new LinkedHashSet<>()).build());
        // find all roles by id
        when(roleService.findAllIds(List.of(roleId))).thenReturn(roles);

        // create user with role dto
        UserWithRolesDTOResponse response = UserWithRolesDTOResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(new LinkedHashSet<>())
                .build();

        when(mapper.map(user, UserWithRolesDTOResponse.class)).thenReturn(response);

        UserWithRolesDTOResponse userWithRolesDTOResponse = userService.removeRoles(user.getId(), List.of(roleId), request);

        assertThat(userWithRolesDTOResponse).isNotNull();
        assertThat(userWithRolesDTOResponse.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void shouldUpdateWorkCorrectly() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTOResponse response = UserDTOResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();

        when(mapper.map(user, UserDTOResponse.class))
                .thenReturn(response);

        UserDTORequest req = UserDTORequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();

        UserDTOResponse result = userService.update(user.getId(), req, request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(req.getUsername());
        assertThat(result.getEmail()).isEqualTo(req.getEmail());
        assertThat(result.getStatus()).isEqualTo(req.getStatus());
    }

    @Test
    void shouldChangeUsernameWorkCorrectly() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTOResponse response = UserDTOResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(UserStatus.BLOCKED)
                .build();

        when(mapper.map(user, UserDTOResponse.class))
                .thenReturn(response);

        UserDTOResponse updated = userService.changeStatus(user.getId(), UserStatus.BLOCKED, request);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(user.getId());
        assertThat(updated.getStatus()).isEqualTo(UserStatus.BLOCKED);
    }

}