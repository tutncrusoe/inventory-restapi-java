package com.group.inventory.user.service;

import com.group.inventory.common.exception.InventoryBusinessException;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.common.util.BaseMapper;
import com.group.inventory.department.service.DepartmentService;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.ERole;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.service.RoleService;
import com.group.inventory.user.dto.UserDTO;
import com.group.inventory.user.dto.UserDTORequest;
import com.group.inventory.user.dto.UserDTOResponse;
import com.group.inventory.user.dto.UserWithRolesDTOResponse;
import com.group.inventory.user.model.User;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserService extends GenericService<User, UserDTO, UUID> {
    UserWithRolesDTOResponse createNewUser(UserDTORequest userDTORequest, HttpServletRequest request);

    //    -----------------------       GET       ----------------------
    List<UserDTOResponse> findAllUser(HttpServletRequest request);

    UserDTOResponse findUserById(UUID userId, HttpServletRequest request);

    List<UserWithRolesDTOResponse> findUserWithRoles(HttpServletRequest request);

    UserWithRolesDTOResponse findUserWithRolesById(UUID userId, HttpServletRequest request);

    UserDTOResponse updateAvatar(UUID userId, MultipartFile file, HttpServletRequest request);

    Set<RoleDTO> getRolesFromUser(UUID userId);

    //    -----------------------       POST       ----------------------

    UserWithRolesDTOResponse addRoles(UUID userId, List<UUID> uuids, HttpServletRequest request);

    UserWithRolesDTOResponse removeRoles(UUID userId, List<UUID> uuids, HttpServletRequest request);

    UserDTOResponse update(UUID userId, UserDTORequest userDTORequest, HttpServletRequest request);

    UserDTOResponse changeStatus(UUID userId, UserStatus userStatus, HttpServletRequest request);
}

@Service
@Transactional
class UserServiceImpl implements UserService {

    //    -----------------------       ATTRIBUTES       ----------------------
    private final UserRepository userRepository;

    private final ImageService imageService;

    private final PasswordEncoder encoder;

    private final BaseMapper mapper;

    private final RoleService roleService;

    private final DepartmentService departmentService;

    //    -----------------------       CONSTRUCTOR       ----------------------
    public UserServiceImpl(UserRepository userRepository,
                           ImageService imageService,
                           PasswordEncoder encoder,
                           BaseMapper mapper,
                           RoleService roleService,
                           @Lazy DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.encoder = encoder;
        this.mapper = mapper;
        this.roleService = roleService;
        this.departmentService = departmentService;
    }

    //    -----------------------       METHOD       ----------------------
    @Override
    public JpaRepository<User, UUID> getRepository() {
        return this.userRepository;
    }


    @Override
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    //    -----------------------       GET       ----------------------

    @Override
    @Transactional(readOnly = true)
    public List<UserDTOResponse> findAllUser(HttpServletRequest request) {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> createUserDTOResponse(user, request))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTOResponse findUserById(UUID userId, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        return createUserDTOResponse(user, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserWithRolesDTOResponse> findUserWithRoles(HttpServletRequest request) {

        return userRepository.findUserWithRoles()
                .stream()
                .map(user -> createUserWithRolesDTOResponse(user, request))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserWithRolesDTOResponse findUserWithRolesById(UUID userId, HttpServletRequest request) {
        User user = userRepository.findUserWithRolesById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        return createUserWithRolesDTOResponse(user, request);
    }

    @Override
    public Set<RoleDTO> getRolesFromUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        return mapper.map(user, UserWithRolesDTOResponse.class).getRoles();
    }


    //    -----------------------       POST       ----------------------
    @Override
    public UserWithRolesDTOResponse createNewUser(UserDTORequest userDTORequest, HttpServletRequest request) {
        User user = mapper.map(userDTORequest, User.class);

        user.setPassword(encoder.encode(userDTORequest.getPassword()));

        User savedUser = userRepository.save(user);

        departmentService.addUsersToDepartment(
                userDTORequest.getDepartmentId(),
                List.of(savedUser.getId())
        );

        Role role = roleService.findByName(ERole.ROLE_USER);

        savedUser.addRole(role);

        return createUserWithRolesDTOResponse(savedUser, request);
    }

    @Override
    public UserWithRolesDTOResponse addRoles(UUID userId, List<UUID> uuids, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        List<Role> roles = roleService.findAllIds(uuids);

        roles.forEach(user::addRole);

        return createUserWithRolesDTOResponse(user, request);
    }

    @Override
    public UserWithRolesDTOResponse removeRoles(UUID userId, List<UUID> uuids, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        List<Role> roles = roleService.findAllIds(uuids);

        roles.forEach(user::removeRole);

        return createUserWithRolesDTOResponse(user, request);
    }

    //    -----------------------       PUT       ----------------------
    @Override
    public UserDTOResponse update(UUID userId, UserDTORequest userDTORequest, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        user.setUsername(userDTORequest.getUsername());
        user.setStatus(userDTORequest.getStatus());
        if (userDTORequest.getPassword() != null) {
            user.setPassword(userDTORequest.getPassword());
        }
        return createUserDTOResponse(user, request);
    }

    public UserDTOResponse updateAvatar(UUID userId, MultipartFile file, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        String avatarCode = imageService.save(file);
        user.setAvatar(avatarCode);

        return createUserDTOResponse(user, request);
    }

    @Override
    public UserDTOResponse changeStatus(UUID userId, UserStatus userStatus, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InventoryBusinessException("User is not existed."));

        user.setStatus(userStatus);

        return createUserDTOResponse(user, request);
    }

    public UserDTOResponse createUserDTOResponse(User user, HttpServletRequest request) {
        UserDTOResponse userDTOResponse;
        String avatarCode = user.getAvatar();
        try {
            if (avatarCode != null) {
                String imageUrl = imageService.generateImgUrl(user.getAvatar(), request);
                userDTOResponse = getModelMapper().map(user, UserDTOResponse.class);
                userDTOResponse.setAvatar(imageUrl);
            } else {
                userDTOResponse = getModelMapper().map(user, UserDTOResponse.class);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ;
        return userDTOResponse;
    }

    public UserWithRolesDTOResponse createUserWithRolesDTOResponse(User user, HttpServletRequest request) {
        UserWithRolesDTOResponse userWithRolesDTOResponse;
        String avatarCode = user.getAvatar();
        try {
            if (avatarCode != null) {
                String imageUrl = imageService.generateImgUrl(user.getAvatar(), request);
                userWithRolesDTOResponse = getModelMapper().map(user, UserWithRolesDTOResponse.class);
                userWithRolesDTOResponse.setAvatar(imageUrl);
            } else {
                userWithRolesDTOResponse = getModelMapper().map(user, UserWithRolesDTOResponse.class);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ;
        return userWithRolesDTOResponse;
    }
}
