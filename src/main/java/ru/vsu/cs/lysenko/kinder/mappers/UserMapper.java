package ru.vsu.cs.lysenko.kinder.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User signUpToUser(SignUpDTO signUpDTO);

    User userDTOToUser(UserDTO userDTO);

    void updateUserFromUserDTO(@MappingTarget User target, UserDTO input);
}
