package ru.vcarstein.service.impl;

import ru.vcarstein.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.vcarstein.enums.ContactType;
import ru.vcarstein.dto.UpdateContactDTO;
import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.entity.User;
import ru.vcarstein.exception.InvalidInputException;
import ru.vcarstein.mapper.UserListMapper;
import ru.vcarstein.mapper.UserMapper;
import ru.vcarstein.repository.UserRepository;
import ru.vcarstein.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserListMapper userListMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserListMapper userListMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userListMapper = userListMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return userListMapper.toUserResponse(userRepository.findAll());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO user) {
        validateUser(user);
        User userToSave = userMapper.fromDTOtoEntity(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userToSave);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> createUsers(List<UserDTO> users) {
        for (UserDTO user : users) {
            validateUser(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        List<User> usersToSave = userListMapper.toModelList(users);
        userRepository.saveAll(usersToSave);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("User with id=" + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public UserDTO updateUserContacts(String username, UpdateContactDTO updateContactDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidInputException("User not found"));

        if (updateContactDTO.getPhone() == null && updateContactDTO.getEmail() == null) {
            throw new InvalidInputException("At least one contact information (phone or email) is required");
        }

        if (updateContactDTO.getPhone() != null) {
            if (userRepository.existsByPhone(updateContactDTO.getPhone())) {
                throw new InvalidInputException("Phone is already taken");
            }
            user.setPhone(updateContactDTO.getPhone());
        }

        if (updateContactDTO.getEmail() != null) {
            if (userRepository.existsByEmail(updateContactDTO.getEmail())) {
                throw new InvalidInputException("Email is already taken");
            }
            user.setEmail(updateContactDTO.getEmail());
        }

        return userMapper.fromEntityToDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO removeUserContactDetail(String username, ContactType contactType) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        switch (contactType) {
            case PHONE:
                if (user.getEmail() == null) {
                    throw new InvalidInputException("Cannot remove the last contact detail.");
                }
                user.setPhone(null);
                break;
            case EMAIL:
                if (user.getPhone() == null) {
                    throw new InvalidInputException("Cannot remove the last contact detail.");
                }
                user.setEmail(null);
                break;
            default:
                throw new InvalidInputException("Invalid contact type.");
        }

        userRepository.save(user);
        return userMapper.fromEntityToDTO(user);
    }

    private void validateUser(UserDTO user) {
        if (user.getPhone() == null && user.getEmail() == null) {
            throw new InvalidInputException("At least one contact information (phone or email) is required");
        }
        if (user.getBankAccount() == null) {
            throw new InvalidInputException("Bank account is required");
        }
        if (user.getBankAccount().getBalance() < 0) {
            throw new InvalidInputException("Balance must be positive");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidInputException("Username is already taken: " + user.getUsername());
        }
        if (user.getPhone() != null && userRepository.existsByPhone(user.getPhone())) {
            throw new InvalidInputException("Phone number is already taken");
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidInputException("Email is already taken");
        }
    }
}
