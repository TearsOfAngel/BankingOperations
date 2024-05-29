package ru.vcarstein.service;

import dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.vcarstein.enums.ContactType;
import ru.vcarstein.dto.UpdateContactDTO;
import dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    ResponseEntity<UserDTO> createUser(UserDTO user);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> findAll();

    @Transactional
    ResponseEntity<Void> createUsers(List<UserDTO> users);

    ResponseEntity<Object> deleteUser(Long id);

    @Transactional
    UserDTO updateUserContacts(String username, UpdateContactDTO updateContactDTO);

    @Transactional
    UserDTO removeUserContactDetail(String username, ContactType contactType);
}
