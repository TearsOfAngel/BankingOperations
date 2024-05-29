package ru.vcarstein.service;

import dto.UserResponseDTO;
import ru.vcarstein.dto.UserSearchDTO;

import java.util.List;

public interface UserSearchService {
    List<UserResponseDTO> searchUsers(UserSearchDTO searchDTO);
}
