package com.group.inventory.parts.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.parts.dto.PartDetailsWithSessionDTO;
import com.group.inventory.parts.dto.PartSessionDTO;
import com.group.inventory.parts.dto.PartSessionWithCategoryAndDetailsDTO;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.model.PartSession;
import com.group.inventory.parts.repository.PartSessionRepository;
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

public interface PartSessionService extends GenericService<PartSession, PartSessionDTO, UUID> {
    PartSessionWithCategoryAndDetailsDTO addPartDetails(UUID partSessionUUID, List<UUID> partDetailsUUIDs);

    PartSessionWithCategoryAndDetailsDTO removePartDetails(UUID partSessionUUID, List<UUID> partDetailsUUIDs);

    List<PartSessionWithCategoryAndDetailsDTO> findAllIncludeCategoryAndDetails();

    PartSessionWithCategoryAndDetailsDTO findByInIncludeCategoryAndDetails(UUID id);
}

@Service
@Transactional
class PartSessionServiceImpl implements PartSessionService {
    private final PartSessionRepository partSessionRepository;

    private final PartDetailsService partDetailsService;

    private final BaseMapper baseMapper;

    PartSessionServiceImpl(PartSessionRepository partSessionRepository, PartDetailsService partDetailsService, BaseMapper baseMapper) {
        this.partSessionRepository = partSessionRepository;
        this.partDetailsService = partDetailsService;
        this.baseMapper = baseMapper;
    }

    @Override
    public JpaRepository<PartSession, UUID> getRepository() {
        return this.partSessionRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.baseMapper;
    }

    @Override
    @Cacheable(value = "PartSessions")
    @Transactional(readOnly = true)
    public List<PartSessionWithCategoryAndDetailsDTO> findAllIncludeCategoryAndDetails() {
        return partSessionRepository.findAllIncludeCategoryAndDetails()
                .stream()
                .map(partSession -> getModelMapper().map(partSession, PartSessionWithCategoryAndDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "PartSession", key = "#id")
    @Transactional(readOnly = true)
    public PartSessionWithCategoryAndDetailsDTO findByInIncludeCategoryAndDetails(UUID id) {
        PartSession partSession = findById(id);
        return getModelMapper().map(partSession, PartSessionWithCategoryAndDetailsDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartSession", key = "#partSessionUUID"),
            @CacheEvict(value = "PartSessions", allEntries = true) }
    )
    public PartSessionWithCategoryAndDetailsDTO addPartDetails(UUID partSessionUUID, List<UUID> partDetailsUUIDs) {
        PartSession partSession = findById(partSessionUUID);

        List<PartDetails> partDetails = partDetailsService.findAllIds(partDetailsUUIDs);

        if (partDetails.size() > 0) {
            partDetails.forEach(partSession::addPartDetails);
        }

        return getModelMapper().map(partSession, PartSessionWithCategoryAndDetailsDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartSession", key = "#partSessionUUID"),
            @CacheEvict(value = "PartSessions", allEntries = true) }
    )
    public PartSessionWithCategoryAndDetailsDTO removePartDetails(UUID partSessionUUID, List<UUID> partDetailsUUIDs) {
        PartSession partSession = findById(partSessionUUID);

        List<PartDetails> partDetails = partDetailsService.findAllIds(partDetailsUUIDs);

        if (partDetails.size() > 0) {
            partDetails.forEach(partSession::removePartDetails);
        }

        return getModelMapper().map(partSession, PartSessionWithCategoryAndDetailsDTO.class);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartSessions", allEntries = true) },
            put = { @CachePut(value = "PartSession", key = "#id") }
    )
    public PartSessionDTO update(PartSessionDTO dto, UUID id, Class<PartSessionDTO> dtoClazz) {
        PartSession partSession = findById(id);

        if (!Objects.equals(partSession.getName(), dto.getName())) {
            throw new InventoryBusinessException("Part session name does not equals!");
        }

        if (!Objects.equals(partSession.getPartNumber(), dto.getPartNumber())) {
            throw new InventoryBusinessException("Part session number does not equals!");
        }

        partSession.setDescription(dto.getDescription());
        return getModelMapper().map(partSession, dtoClazz);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "PartSessions", allEntries = true) })
    public PartSessionDTO save(PartSessionDTO dto, Class<PartSession> clazz, Class<PartSessionDTO> dtoClazz) {
        return PartSessionService.super.save(dto, clazz, dtoClazz);
    }
}