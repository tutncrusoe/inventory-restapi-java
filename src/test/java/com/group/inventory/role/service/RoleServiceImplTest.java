package com.group.inventory.role.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleServiceImplTest {

    @Mock
    private RoleRepository repository;
    @Mock
    private BaseMapper mapper;

    private RoleServiceImpl service;

    private final ERole roleName = ERole.ROLE_ADMIN;
    private final String description = "Admin role";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mapper = Mockito.mock(BaseMapper.class);
        repository = Mockito.mock(RoleRepository.class);
        service = new RoleServiceImpl(repository, mapper);
        repository.deleteAll();
    }

    @Test
    void shouldGetRepositoryWorkCorrectly() {
        assertThat(service.getRepository()).isNotNull();
    }

    @Test
    void shouldGetModelMapperWorkCorrectly() {
        assertThat(service.getModelMapper()).isNotNull();
    }

    @Test
    void shouldFindByNameWorkCorrectly() {
        Role role = Role.builder()
                .name(roleName)
                .build();

        when(repository.findByName(any(ERole.class)))
                .thenReturn(Optional.of(role));

        Role found = service.findByName(ERole.ROLE_ADMIN);

        assertThat(found).isNotNull();

    }

    @Test
    void shouldFindByNameThrowable() {
        when(repository.findByName(roleName))
                .thenThrow(InventoryBusinessException.class);

        assertThatThrownBy(() -> service.findByName(roleName))
                .isInstanceOf(InventoryBusinessException.class);
    }

    @Test
    void shouldUpdateThrowable() {
        RoleDTO dto = RoleDTO.builder()
                .name(roleName)
                .description(description)
                .build();

        when(repository.findById(any(UUID.class)))
                .thenThrow(InventoryBusinessException.class);

        assertThatThrownBy(() ->
                service.update(dto, UUID.randomUUID(), RoleDTO.class))
                .isInstanceOf(InventoryBusinessException.class);
    }


    @Test
    void shouldSaveRoleWorkCorrectly() {
        Role role = Role.builder()
                .name(roleName)
                .description(description)
                .build();

        RoleDTO dto = RoleDTO.builder()
                .name(roleName)
                .description(description)
                .build();

        when(repository.save(any(Role.class)))
                .thenReturn(role);

        when(mapper.map(role, RoleDTO.class))
                .thenReturn(dto);

        when(mapper.map(dto, Role.class))
                .thenReturn(role);


        RoleDTO saved = service.save(dto, Role.class, RoleDTO.class);

        assertThat(saved.getName()).isEqualTo(roleName);
        assertThat(saved.getDescription()).isEqualTo(description);

    }

    @Test
    void shouldUpdateRoleWorkCorrectly() {
        Role role = Role.builder()
                .name(roleName)
                .description(description)
                .build();

        RoleDTO dto = RoleDTO.builder()
                .name(roleName)
                .description(description)
                .build();

        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(role));

        when(mapper.map(role, RoleDTO.class))
                .thenReturn(dto);


        RoleDTO updated = service.update(dto, UUID.randomUUID(), RoleDTO.class);

        assertThat(updated.getName()).isEqualTo(roleName);
        assertThat(updated.getDescription()).isEqualTo(description);
    }
}