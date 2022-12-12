package com.group.inventory.role.boundary;

import com.group.inventory.common.dto.BaseResponse;
import com.group.inventory.common.util.DateTimeUtils;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.service.RoleService;
import com.group.inventory.role.service.RoleServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleRestResourceTest {

    @Mock
    private RoleService roleService;
    @InjectMocks
    private RoleRestResource controller;
    MockedStatic<ResponseHelper> helperMocked;

    private final ERole name = ERole.ROLE_ADMIN;
    private final String description = "Admin role";

    @BeforeAll
    void init() {
        helperMocked = Mockito.mockStatic(ResponseHelper.class);
        roleService = Mockito.mock(RoleService.class);
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = Mockito.mock(RoleServiceImpl.class);
        controller = new RoleRestResource(roleService);
    }

    @Test
    void shouldGetAllRoleWorkCorrectly() {
//        Given
        List<RoleDTO> roles = new ArrayList<>();

        roles.add(RoleDTO.builder()
                .name(ERole.ROLE_ADMIN)
                .description("Admin")
                .build());

        roles.add(RoleDTO.builder()
                .name(ERole.ROLE_ADMIN)
                .description("Admin")
                .build());

//        When
        when(roleService.findAllDTO(RoleDTO.class)).thenReturn(roles);

        String currentTime = DateTimeUtils.now();

        helperMocked.when(() -> ResponseHelper.getResponse(anyList(), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(roles)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(currentTime)
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));
        roles.forEach(System.out::println);

//        Then
        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findAllRoles();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(200);
        assertThat(response.getBody().getTimestamp()).isEqualTo(currentTime);
        assertThat(response.getBody().getContent()).isInstanceOf(List.class);
        assertThat(response.getBody().getContent()).isEqualTo(roles);
        assertThat(new ArrayList<>((Collection<?>) response.getBody().getContent())).hasSize(2);
        assertThat(response.getBody().isHasError()).isFalse();
        assertThat(response.getBody().getErrors()).isEmpty();
    }

    @Test
    void shouldFindRoleByIdWorkCorrectly() {
        UUID id = UUID.randomUUID();
        RoleDTO roleDTO = RoleDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        String currentTime = DateTimeUtils.now();
        helperMocked.when(() -> ResponseHelper
                .getResponse(
                        any(Object.class),
                        any(HttpStatus.class)
                )).thenReturn(new ResponseEntity<>(
                BaseResponse.builder()
                        .content(roleDTO)
                        .hasError(false)
                        .errors(Collections.emptyList())
                        .timestamp(currentTime)
                        .status(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findAllRoles();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isInstanceOf(RoleDTO.class);
        assertThat((RoleDTO) response.getBody().getContent()).isEqualTo(roleDTO);
        assertThat(((RoleDTO) response.getBody().getContent()).getId()).isEqualTo(id);
        assertThat(response.getBody().getTimestamp()).isEqualTo(currentTime);
        assertThat(response.getBody().isHasError()).isFalse();
        assertThat(response.getBody().getErrors()).isEmpty();
    }

    @Test
    void shouldSaveRoleWorkCorrectly() {
        RoleDTO roleDTO = RoleDTO.builder()
                .name(name)
                .description(description)
                .build();

        when(roleService.save(roleDTO, Role.class, RoleDTO.class))
                .thenReturn(roleDTO);

        String currentTime = DateTimeUtils.now();
        helperMocked.when(() -> ResponseHelper
                .getResponse(
                        any(Object.class),
                        any(HttpStatus.class)
                )).thenReturn(new ResponseEntity<>(
                BaseResponse.builder()
                        .content(roleDTO)
                        .hasError(false)
                        .errors(Collections.emptyList())
                        .timestamp(currentTime)
                        .status(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK)
        );

        ResponseEntity<BaseResponse> result = (ResponseEntity<BaseResponse>) controller.saveRole(roleDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BaseResponse response = result.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent()).isInstanceOf(RoleDTO.class);
        assertThat(((RoleDTO) response.getContent()).getName()).isEqualTo(roleDTO.getName());
        assertThat(((RoleDTO) response.getContent()).getDescription()).isEqualTo(roleDTO.getDescription());
        assertThat(response.getTimestamp()).isEqualTo(currentTime);
        assertThat(response.isHasError()).isFalse();
        assertThat(response.getErrors()).isEmpty();
    }

    @Test
    void shouldUpdateWorkCorrectly() {
        UUID id = UUID.randomUUID();
        RoleDTO roleDTO = RoleDTO.builder()
                .name(name)
                .description(description)
                .build();

        when(roleService.update(roleDTO, id, RoleDTO.class))
                .thenReturn(roleDTO);

        String currentTime = DateTimeUtils.now();
        helperMocked.when(() -> ResponseHelper
                .getResponse(
                        any(Object.class),
                        any(HttpStatus.class)
                )).thenReturn(new ResponseEntity<>(
                BaseResponse.builder()
                        .content(roleDTO)
                        .hasError(false)
                        .errors(Collections.emptyList())
                        .timestamp(currentTime)
                        .status(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK)
        );

        ResponseEntity<BaseResponse> result = (ResponseEntity<BaseResponse>) controller.updateRole(id, roleDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BaseResponse response = result.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent()).isInstanceOf(RoleDTO.class);
        assertThat(((RoleDTO) response.getContent()).getName()).isEqualTo(roleDTO.getName());
        assertThat(((RoleDTO) response.getContent()).getDescription()).isEqualTo(roleDTO.getDescription());
        assertThat(response.getTimestamp()).isEqualTo(currentTime);
        assertThat(response.isHasError()).isFalse();
        assertThat(response.getErrors()).isEmpty();
    }
}