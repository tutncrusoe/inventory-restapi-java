package com.group.inventory.parts.service;

import com.group.inventory.action.model.EActionStatus;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.parts.dto.PartDetailsDTO;
import com.group.inventory.parts.dto.PartDetailsWithSessionDTO;
import com.group.inventory.parts.model.EPartStatus;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.repository.PartDetailsRepository;
import com.group.inventory.user.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public interface PartDetailsService extends GenericService<PartDetails, PartDetailsDTO, UUID> {
    List<PartDetailsWithSessionDTO> findAllIncludeSession(HttpServletRequest request);

    PartDetailsWithSessionDTO findByIdDTOInclude(UUID id, HttpServletRequest request);

    PartDetailsDTO changeStatus(UUID partDetailsUUID, EPartStatus status, HttpServletRequest request);

    void changeActionStatus(UUID partDetailsUUID, EActionStatus status);
}

@Service
@Transactional
class PartDetailsServiceImpl implements PartDetailsService {
    private final PartDetailsRepository partDetailsRepository;

    private final BaseMapper baseMapper;

    private final ImageService imageService;

    PartDetailsServiceImpl(PartDetailsRepository partDetailsRepository, BaseMapper baseMapper, ImageService imageService) {
        this.partDetailsRepository = partDetailsRepository;
        this.baseMapper = baseMapper;
        this.imageService = imageService;
    }

    @Override
    public JpaRepository<PartDetails, UUID> getRepository() {
        return this.partDetailsRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return this.baseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartDetailsWithSessionDTO> findAllIncludeSession(HttpServletRequest request) {
        return partDetailsRepository.findAllIncludeSession()
                .stream()
                .map(partDetails -> createPartDetailsWithSessionDTO(partDetails, request))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PartDetailsWithSessionDTO findByIdDTOInclude(UUID id, HttpServletRequest request) {
        PartDetails partDetails = findById(id);
        return createPartDetailsWithSessionDTO(partDetails, request);
    }

    @Override
    public PartDetailsDTO save(PartDetailsDTO dto, Class<PartDetails> clazz, Class<PartDetailsDTO> dtoClazz) {
        if (!dto.isSpecial() && dto.getQuantity() > 0) {
            throw new InventoryBusinessException("Can not save part details!");
        }
        PartDetails partDetails = getModelMapper().map(dto, clazz);
        PartDetails savedPartDetails = getRepository().save(partDetails);
        return getModelMapper().map(savedPartDetails, dtoClazz);
    }

    @Override
    public PartDetailsDTO update(PartDetailsDTO dto, UUID id, Class<PartDetailsDTO> dtoClazz) {
        if (!dto.isSpecial() && dto.getQuantity() > 0) {
            throw new InventoryBusinessException("Can not update part details!");
        }

        PartDetails partDetails = findById(id);

        if (!Objects.equals(partDetails.getPartNumber(), dto.getPartNumber())) {
            throw new InventoryBusinessException("Part details number does not equals!");
        }

        partDetails.setName(dto.getName());
        partDetails.setDescription(dto.getDescription());
        partDetails.setPhoto(dto.getPhoto());
        partDetails.setMadeBy(dto.getMadeBy());
        partDetails.setSpecial(dto.isSpecial());
        partDetails.setPartStatus(dto.getPartStatus());
        partDetails.setActionStatus(dto.getActionStatus());

        if (!dto.isSpecial()) {
            partDetails.setQuantity(0);
        } else {
            partDetails.setQuantity(dto.getQuantity());
        }

        return getModelMapper().map(partDetailsRepository.save(partDetails),dtoClazz);
    }

    @Override
    public PartDetailsDTO changeStatus(UUID partDetailsUUID, EPartStatus status, HttpServletRequest request) {
        PartDetails partDetails = findById(partDetailsUUID);

        partDetails.setPartStatus(status);

        return getModelMapper().map(partDetails, PartDetailsDTO.class);
    }

    @Override
    public void changeActionStatus(UUID partDetailsUUID, EActionStatus status) {
        PartDetails partDetails = findById(partDetailsUUID);

        partDetails.setActionStatus(status);
    }

    public PartDetailsWithSessionDTO createPartDetailsWithSessionDTO(PartDetails partDetails, HttpServletRequest request) {
        PartDetailsWithSessionDTO partDetailsWithSessionDTO;
        String photoCode = partDetails.getPhoto();
        try {
            if (photoCode != null) {
                String imageUrl = imageService.generateImgUrl(photoCode, request);
                partDetailsWithSessionDTO = getModelMapper().map(partDetails, PartDetailsWithSessionDTO.class);
                partDetailsWithSessionDTO.setPhoto(imageUrl);
            } else {
                partDetailsWithSessionDTO = getModelMapper().map(partDetails, PartDetailsWithSessionDTO.class);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        };
        return partDetailsWithSessionDTO;
    }
}