package com.group.inventory.parts.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.parts.dto.PartCategoryDTO;
import com.group.inventory.parts.dto.PartCategoryWithSessionDTO;
import com.group.inventory.parts.model.PartCategory;
import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.repository.PartCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public interface PartCategoryService extends GenericService<PartCategory, PartCategoryDTO, UUID> {
    PartCategoryWithSessionDTO addPartSession(UUID partCategoryUUID, List<UUID> partSessionUUIDs);

    PartCategoryWithSessionDTO removePartSession(UUID partCategoryUUID, List<UUID> partSessionUUIDs);

    List<PartCategoryWithSessionDTO> findAllIncludeSessions();

    PartCategoryWithSessionDTO findByIdWithSessionDTO(UUID id);
}

@Service
@Transactional
class PartCategoryServiceImpl implements PartCategoryService {
    private final PartCategoryRepository partCategoryRepository;

    private final PartSessionService partSessionService;

    private final BaseMapper baseMapper;

    PartCategoryServiceImpl(PartCategoryRepository partCategoryRepository, PartSessionService partSessionService, BaseMapper baseMapper) {
        this.partCategoryRepository = partCategoryRepository;
        this.partSessionService = partSessionService;
        this.baseMapper = baseMapper;
    }

    @Override
    public JpaRepository<PartCategory, UUID> getRepository() {
        return this.partCategoryRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.baseMapper;
    }

    @Override
    @Cacheable(value = "PartCategories")
    @Transactional(readOnly = true)
    public List<PartCategoryWithSessionDTO> findAllIncludeSessions() {
        return partCategoryRepository.findAllIncludeSessions()
                .stream()
                .map(partCategory -> getModelMapper().map(partCategory, PartCategoryWithSessionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "PartCategory", key = "#id")
    @Transactional(readOnly = true)
    public PartCategoryWithSessionDTO findByIdWithSessionDTO(UUID id) {
        PartCategory partCategory = findById(id);
        return getModelMapper().map(partCategory, PartCategoryWithSessionDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartCategory", key = "#partCategoryUUID"),
            @CacheEvict(value = "PartCategories", allEntries = true) }
    )
    public PartCategoryWithSessionDTO addPartSession(UUID partCategoryUUID ,List<UUID> partSessionUUIDs) {
        PartCategory partCategory = partCategoryRepository.findById(partCategoryUUID)
                .orElseThrow(
                        () -> new InventoryBusinessException("Part category id is not existed")
                );

        List<PartSession> partSessions = partSessionService.findAllIds(partSessionUUIDs);

        if (partSessions.size() > 0) {
            partSessions.forEach(partCategory::addPartSession);
        }

        return getModelMapper().map(partCategory, PartCategoryWithSessionDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartCategory", key = "#partCategoryUUID"),
            @CacheEvict(value = "PartCategories", allEntries = true) }
    )
    public PartCategoryWithSessionDTO removePartSession(UUID partCategoryUUID, List<UUID> partSessionUUIDs) {
        PartCategory partCategory = partCategoryRepository.findById(partCategoryUUID)
                .orElseThrow(
                        () -> new InventoryBusinessException("Part category id is not existed")
                );

        List<PartSession> partSessions = partSessionService.findAllIds(partSessionUUIDs);

        if (partSessions.size() > 0) {
            partSessions.forEach(partCategory::removePartSession);
        }

        return getModelMapper().map(partCategory, PartCategoryWithSessionDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartCategories", allEntries = true) },
            put = { @CachePut(value = "PartCategory", key = "#id") }
    )
    public PartCategoryDTO update(PartCategoryDTO dto, UUID id, Class<PartCategoryDTO> dtoClazz) {
        PartCategory partCategory = findById(id);

        if (!Objects.equals(partCategory.getName(), dto.getName())) {
            throw new InventoryBusinessException("Part category name does not equals!");
        }

        partCategory.setDescription(dto.getDescription());

        return getModelMapper().map(partCategory, PartCategoryDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartCategories", allEntries = true) })
    public PartCategoryDTO save(PartCategoryDTO dto, Class<PartCategory> clazz, Class<PartCategoryDTO> dtoClazz) {
        return PartCategoryService.super.save(dto, clazz, dtoClazz);
    }
}
