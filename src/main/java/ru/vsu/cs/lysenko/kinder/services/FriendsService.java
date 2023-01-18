package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.lysenko.kinder.data.entities.UsersRelationStatuses;
import ru.vsu.cs.lysenko.kinder.data.repos.FriendsRepository;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FriendsService {
    private final FriendsRepository friendsRepo;
    private final UserMapper mapper;

    public List<UserDTO> getFriends(UserDTO user) {
        return friendsRepo.getFriends(user.getId(),
                UsersRelationStatuses.ACCEPTED.name()).stream().map(mapper::userToUserDTO).toList();
    }

    public List<UserDTO> getRequests(UserDTO user, String requestType) {
        try {
            return friendsRepo.getFriends(user.getId(),
                            UsersRelationStatuses.valueOf(requestType.toUpperCase()).name())
                    .stream().map(mapper::userToUserDTO).toList();
        } catch (IllegalArgumentException e) {
            throw new AppException("Illegal request type", HttpStatus.BAD_REQUEST);
        }
    }
}
