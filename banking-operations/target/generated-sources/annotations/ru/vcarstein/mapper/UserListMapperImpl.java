package ru.vcarstein.mapper;

import dto.UserDTO;
import dto.UserResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vcarstein.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T06:25:30+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class UserListMapperImpl implements UserListMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> toModelList(List<UserDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtos.size() );
        for ( UserDTO userDTO : dtos ) {
            list.add( userMapper.fromDTOtoEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDTOList(List<User> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entities.size() );
        for ( User user : entities ) {
            list.add( userMapper.fromEntityToDTO( user ) );
        }

        return list;
    }

    @Override
    public List<UserResponseDTO> toUserResponse(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserResponseDTO> list = new ArrayList<UserResponseDTO>( user.size() );
        for ( User user1 : user ) {
            list.add( userMapper.toUserResponse( user1 ) );
        }

        return list;
    }
}
