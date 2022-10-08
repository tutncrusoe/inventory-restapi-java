package com.group.inventory.user.service;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.common.service.GenericService;
import com.group.inventory.department.model.Department;
import com.group.inventory.department.repository.DepartmentRepository;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.repository.RoleRepository;
import com.group.inventory.user.dto.RequestUserDTO;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.mapper.RoleMapper;
import com.group.inventory.user.dto.UserDTO;
import com.group.inventory.user.mapper.UserMapper;
import com.group.inventory.user.model.User;
import com.group.inventory.user.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserService extends GenericService<User, UserDTO, UUID> {
    UserDTO createNewUser(RequestUserDTO dto);

    List<UserDTO> findAllUser(HttpServletRequest request);

    UserDTO findUserById(String id, HttpServletRequest request);

    User findByEmail(String email);

    List<RoleDTO> getRolesFromUser(UUID id);

    UserDTO findUserWithRolesById(String id);

    UserDTO updateAvatar(String id, MultipartFile file);

    UserDTO addRole(String userId, String roleId);

    UserDTO removeRole(String userId, String roleId);

    UserDTO addDepartment(String userId, String departmentId);

    UserDTO removeDepartment(String userId, String departmentId);

    UserDTO save(UserDTO userDTO);

    UserDTO update(String id, UserDTO dto);

    void delete(String id);
}

@Service
@Transactional
class UserServiceImpl implements UserService {

    // 1. Attributes
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final DepartmentRepository departmentRepository;

    private final ImageService imageService;

    private final PasswordEncoder encoder;

    private final UserMapper userMapper;

    // 2. Constructors
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           DepartmentRepository departmentRepository,
                           ImageService imageService,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.imageService = imageService;
        this.encoder = encoder;
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    // 3. Methods
    @Override
    public JpaRepository<User, UUID> getRepository() {
        return this.userRepository;
    }

    @Override
    public BaseMapper<User, UserDTO> getMapper() {
        return this.userMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUser(HttpServletRequest request) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                            UserDTO userDTO;
                            try {
                                String imageUrl = imageService.generateImgUrl(user.getAvatar(), request);
                                userDTO = userMapper.toUserDTO(user);
                                userDTO.setAvatar(imageUrl);
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            ;
                            return userDTO;
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserById(String id, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findById(UUID.fromString(id));
        if (userOpt.isEmpty()) {
            return null;
        }
        return userMapper.toUserDTO(userOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserWithRolesById(String id) {
        Optional<User> userOpt = userRepository.findUserWithRolesById(UUID.fromString(id));
        if (userOpt.isEmpty()) {
            return null;
        }
        return userMapper.toUserDTO(userOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null;
        }
        return userOpt.get();
    }

    @Override
    public UserDTO createNewUser(RequestUserDTO dto) {
        User user = userMapper.mapToEntity(dto);

        user.setPassword(encoder.encode(dto.getPassword()));

        User newUser = userRepository.save(user);

        newUser.setPassword("");

        dto.getRoleIdList().forEach(roleId -> {
            addRole(newUser.getId().toString(), roleId);
        });

        UserDTO userDTO = addDepartment(newUser.getId().toString(), dto.getDepartmentId());

        return userDTO;
    }

    @Override
    public UserDTO update(String id, UserDTO dto) {
        User user = userMapper.mapToEntity(dto);

        Optional<User> curUserOpt = userRepository.findById(UUID.fromString(id));

        if (curUserOpt.isEmpty()) {
            return null;
        }

        User curUser = curUserOpt.get();

        if (curUser.getId().equals(user.getId())) {
            User newUser = userRepository.save(user);
            return userMapper.toUserDTO(newUser);
        }
        return null;
    }

    public UserDTO updateAvatar(String id, MultipartFile file) {
        Optional<User> userOpt = userRepository.findById(UUID.fromString(id));

        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();

        String imageCode = imageService.save(file);

        user.setAvatar(imageCode);

        User newUser = userRepository.save(user);

        return userMapper.toUserDTO(newUser);
    }

    @Override
    public UserDTO addRole(String userId, String roleId) {
        Optional<User> userOpt;
        Optional<Role> roleOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            roleOpt = roleRepository.findById(UUID.fromString(roleId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
//            user.addRole(role);

            User modifiedUser = userRepository.save(user);
            return userMapper.toUserDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public UserDTO removeRole(String userId, String roleId) {
        Optional<User> userOpt;
        Optional<Role> roleOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            roleOpt = roleRepository.findById(UUID.fromString(roleId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
//            user.removeRole(role);

            User modifiedUser = userRepository.save(user);
            return userMapper.toUserDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public UserDTO addDepartment(String userId, String departmentId) {
        Optional<User> userOpt;
        Optional<Department> departmentOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            departmentOpt = departmentRepository.findById(UUID.fromString(departmentId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && departmentOpt.isPresent()) {
            User user = userOpt.get();
            Department department = departmentOpt.get();
//            user.addDepartment(department);

            User modifiedUser = userRepository.save(user);
            return userMapper.toUserDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public UserDTO removeDepartment(String userId, String departmentId) {
        Optional<User> userOpt;
        Optional<Department> departmentOpt;

        try {
            userOpt = userRepository.findById(UUID.fromString(userId));
            departmentOpt = departmentRepository.findById(UUID.fromString(departmentId));
        } catch (EntityNotFoundException e) {
            return null;
        }

        if (userOpt.isPresent() && departmentOpt.isPresent()) {
            User user = userOpt.get();
            Department department = departmentOpt.get();
//            user.removeDepartment(department);

            User modifiedUser = userRepository.save(user);
            return userMapper.toUserDTO(modifiedUser);
        }

        return null;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.mapToDTO(savedUser);
    }

    @Override
    public List<RoleDTO> getRolesFromUser(UUID uuid) {
        return null;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}