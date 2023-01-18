package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.lysenko.kinder.data.repos.FriendsRepository;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FriendListService {
    private final FriendsRepository friendsRepo;
    private final UserMapper mapper;

    public List<UserDTO> getFriends(UserDTO user) {
        return friendsRepo.getFriends(user.getId()).stream().map(mapper::userToUserDTO).toList();
    }
}
