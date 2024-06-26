package ru.vcarstein.mapper;

import ru.vcarstein.dto.UserDTO;
import org.mapstruct.Mapper;
import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.entity.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<User> toModelList(List<UserDTO> dtos);
    List<UserDTO> toDTOList(List<User> entities);
    List<UserResponseDTO> toUserResponse(List<User> user);
}
