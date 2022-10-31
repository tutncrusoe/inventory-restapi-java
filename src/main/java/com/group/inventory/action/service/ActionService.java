package com.group.inventory.action.service;

import com.group.inventory.action.dto.ActionDTO;
import com.group.inventory.action.dto.ActionDTOWithFileRequest;
import com.group.inventory.action.model.Action;
import com.group.inventory.action.model.EActionStatus;
import com.group.inventory.action.repository.ActionRepository;
import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.parts.dto.PartDetailsDTO;
import com.group.inventory.parts.model.PartDetails;
import com.group.inventory.parts.service.PartDetailsService;
import com.group.inventory.user.model.User;
import com.group.inventory.user.service.ImageService;
import com.group.inventory.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ActionService extends GenericService<Action, ActionDTO, UUID> {
    List<ActionDTO> findAllDTO(HttpServletRequest request);

    ActionDTO findByIdDTO(UUID id, HttpServletRequest request);

    ActionDTO updatePhoto(UUID actionId, MultipartFile file, HttpServletRequest request);

    String createAction(ActionDTOWithFileRequest dto, HttpServletRequest request);
}

@Service
@Transactional
class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;

    private final BaseMapper baseMapper;

    private final ImageService imageService;

    private final UserService userService;

    private final PartDetailsService partDetailsService;

    ActionServiceImpl(ActionRepository actionRepository,
                      BaseMapper baseMapper,
                      ImageService imageService,
                      UserService userService,
                      PartDetailsService partDetailsService) {
        this.actionRepository = actionRepository;
        this.baseMapper = baseMapper;
        this.imageService = imageService;
        this.userService = userService;
        this.partDetailsService = partDetailsService;
    }

    @Override
    public JpaRepository<Action, UUID> getRepository() {
        return actionRepository;
    }

    @Override
    public ModelMapper getModelMapper() {
        return baseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActionDTO> findAllDTO(HttpServletRequest request) {
        return actionRepository.findAll()
                .stream()
                .map(action -> createActionDTO(action, request))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ActionDTO findByIdDTO(UUID id, HttpServletRequest request) {
        Action action = actionRepository.findAllIncludeUserAndPartDetails(id)
                .orElseThrow(() -> new InventoryBusinessException("Action is not existed."));
        return createActionDTO(action, request);
    }

    @Override
    public ActionDTO updatePhoto(UUID actionId, MultipartFile file, HttpServletRequest request) {
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new InventoryBusinessException("Action is not existed."));

        String photoCode = imageService.save(file);

        action.setPhoto(photoCode);

        return createActionDTO(action, request);
    }

    @Override
    public String createAction(ActionDTOWithFileRequest dto, HttpServletRequest request) {
        PartDetails partDetails = partDetailsService.findById(dto.getPartDetailsId());

        // Check part status is free or not and part action status is not equals to current action status.
        if (!Objects.equals(partDetails.getActionStatus(), EActionStatus.FREE)
                && !Objects.equals(dto.getName(), EActionStatus.FREE)
                || Objects.equals(partDetails.getActionStatus(), dto.getName())) {
            throw new InventoryBusinessException("Can not create action!");
        }

        // Check part details is special
        if (!partDetails.isSpecial() && dto.getQuantityUsed() > 0) {
            throw new InventoryBusinessException("Can not add quantity used into not special part details!");
        }

        // Make sure quantity used does not larger than part details quantity
        if (partDetails.getQuantity() < dto.getQuantityUsed()) {
            throw new InventoryBusinessException("Quantity used can not larger than part quantity");
        }

        User user = userService.findById(dto.getUserId());

        // Save photo
        MultipartFile file = dto.getFile();
        String photoCode = imageService.save(file);

        // Save new action
        Action action = new Action();
        System.out.println(action.isSpecialPart());
        action.setName(dto.getName());
        action.setDescription(dto.getDescription());
        action.setPhoto(photoCode);
        action.setPartStatus(dto.getPartStatus());
        action.setQuantityUsed(dto.getQuantityUsed());
        action.setSpecialPart(partDetails.isSpecial());
        Action newAction = actionRepository.save(action);

        // Set user who make the action
        newAction.setUser(user);

        // Add action to part details
        partDetails.addAction(newAction);

        // Update part details
        PartDetailsDTO partDetailsDTO = getModelMapper().map(partDetails, PartDetailsDTO.class);
        partDetailsDTO.setPartStatus(dto.getPartStatus());
        partDetailsDTO.setActionStatus(action.getName());
        partDetailsDTO.setQuantity(partDetails.getQuantity() - dto.getQuantityUsed());
        partDetailsService.update(partDetailsDTO, partDetailsDTO.getId(), PartDetailsDTO.class);

        return "Create action successfully!";
    }

    public ActionDTO createActionDTO(Action action, HttpServletRequest request) {
        ActionDTO actionDTO;
        String photoCode = action.getPhoto();
        try {
            if (photoCode != null) {
                String imageUrl = imageService.generateImgUrl(photoCode, request);
                actionDTO = getModelMapper().map(action, ActionDTO.class);
                actionDTO.setPhoto(imageUrl);
            } else {
                actionDTO = getModelMapper().map(action, ActionDTO.class);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        };
        return actionDTO;
    }
}