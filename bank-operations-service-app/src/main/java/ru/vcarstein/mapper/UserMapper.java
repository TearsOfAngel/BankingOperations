package ru.vcarstein.mapper;

import ru.vcarstein.dto.UserDTO;
import org.mapstruct.Mapper;
import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.entity.User;

@Mapper(componentModel = "spring", uses = {BankAccountMapper.class})
public interface UserMapper {

    User fromDTOtoEntity(UserDTO userDTO);

    UserDTO fromEntityToDTO(User userDTO);

    UserResponseDTO toUserResponse(User user);
}
