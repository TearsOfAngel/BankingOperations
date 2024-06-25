package ru.vcarstein.mapper;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vcarstein.dto.UserDTO;
import ru.vcarstein.dto.UserResponseDTO;
import ru.vcarstein.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T20:43:05+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public User fromDTOtoEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDTO.getUsername() );
        user.setPassword( userDTO.getPassword() );
        user.setFullName( userDTO.getFullName() );
        user.setBirthDate( userDTO.getBirthDate() );
        user.setPhone( userDTO.getPhone() );
        user.setEmail( userDTO.getEmail() );
        user.setBankAccount( bankAccountMapper.fromDTOtoEntity( userDTO.getBankAccount() ) );

        return user;
    }

    @Override
    public UserDTO fromEntityToDTO(User userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserDTO userDTO1 = new UserDTO();

        userDTO1.setUsername( userDTO.getUsername() );
        userDTO1.setPassword( userDTO.getPassword() );
        userDTO1.setFullName( userDTO.getFullName() );
        userDTO1.setBirthDate( userDTO.getBirthDate() );
        userDTO1.setPhone( userDTO.getPhone() );
        userDTO1.setEmail( userDTO.getEmail() );
        userDTO1.setBankAccount( bankAccountMapper.fromEntityToDTO( userDTO.getBankAccount() ) );

        return userDTO1;
    }

    @Override
    public UserResponseDTO toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setFullName( user.getFullName() );
        userResponseDTO.setPhone( user.getPhone() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setBirthDate( user.getBirthDate() );
        userResponseDTO.setBankAccount( bankAccountMapper.fromEntityToDTO( user.getBankAccount() ) );

        return userResponseDTO;
    }
}
