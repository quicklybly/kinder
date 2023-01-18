package ru.vsu.cs.lysenko.kinder.mappers;

import org.mapstruct.Mapper;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User signUpToUser(SignUpDTO signUpDTO);

    User userDTOToUser(UserDTO userDTO);
}
