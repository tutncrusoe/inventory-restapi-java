package com.group.inventory.user.mapper;

import com.group.inventory.common.dto.BaseMapper;
import com.group.inventory.user.dto.RequestUserDTO;
import com.group.inventory.user.dto.UserDTO;
import com.group.inventory.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<User, UserDTO> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToEntity(UserDTO userDTO);

    User mapToEntity(RequestUserDTO requestUserDTO);

    UserDTO toUserDTO(User user);
}
