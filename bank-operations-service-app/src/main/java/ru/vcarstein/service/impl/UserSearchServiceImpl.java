package ru.vcarstein.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.dto.UserSearchDTO;
import ru.vcarstein.entity.User;
import ru.vcarstein.mapper.UserMapper;
import ru.vcarstein.repository.UserRepository;
import ru.vcarstein.service.UserSearchService;
import ru.vcarstein.repository.specification.UserSpecification;

import java.util.List;


@Service
public class UserSearchServiceImpl implements UserSearchService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserSearchServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> searchUsers(UserSearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(),
                Sort.by(searchDTO.getSortDirection(), searchDTO.getSortBy()));

        Specification<User> spec = Specification.where(null);

        if (searchDTO.getFullName() != null) {
            spec = spec.and(UserSpecification.fullNameLike(searchDTO.getFullName()));
        }
        if (searchDTO.getPhone() != null) {
            spec = spec.and(UserSpecification.phoneEqual(searchDTO.getPhone()));
        }
        if (searchDTO.getEmail() != null) {
            spec = spec.and(UserSpecification.emailEqual(searchDTO.getEmail()));
        }
        if (searchDTO.getBirthDate() != null) {
            spec = spec.and(UserSpecification.birthDateGreaterThan(searchDTO.getBirthDate()));
        }

        List<User> users = userRepository.findAll(spec, pageable).getContent();

        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}
