package ru.vcarstein.service;

import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.dto.UserSearchDTO;

import java.util.List;

public interface UserSearchService {
    List<UserResponseDTO> searchUsers(UserSearchDTO searchDTO);
}
