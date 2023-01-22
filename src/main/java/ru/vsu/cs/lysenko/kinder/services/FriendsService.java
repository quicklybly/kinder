package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.lysenko.kinder.data.entities.RequestAnswer;
import ru.vsu.cs.lysenko.kinder.data.entities.UserRelationsStatuses;
import ru.vsu.cs.lysenko.kinder.data.repos.FriendsRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.RelationRepository;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FriendsService {
    private final FriendsRepository friendsRepo;

    private final RelationRepository relationRepo;

    private final UserMapper mapper;

    public List<UserDTO> getFriends(UserDTO user) {
        return friendsRepo.getFriends(user.getId()).
                stream().map(mapper::userToUserDTO).toList();
    }

    public List<UserDTO> getRequests(UserDTO user, String requestType) {
        try {
            return friendsRepo.getRelatedUsersByStatus(user.getId(),
                            UserRelationsStatuses.valueOf(requestType.toUpperCase()).name())
                    .stream().map(mapper::userToUserDTO).toList();
        } catch (IllegalArgumentException e) {
            throw new AppException("Illegal request type", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteFriend(UserDTO user, Long friendId) {
        relationRepo.deleteByLeftUserIdAndRightUserId(user.getId(), friendId);
    }

    public void answerFriendRequest(UserDTO user, Long friendId, String answer) {
        try {
            switch (RequestAnswer.valueOf(answer.toUpperCase())) {
                case ACCEPT -> relationRepo.acceptFriendRequest(user.getId(), friendId);
                case DECLINE -> relationRepo.deleteByLeftUserIdAndRightUserId(user.getId(), friendId);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException("Illegal request type", HttpStatus.BAD_REQUEST);
        }
    }

    public List<UserDTO> searchForFriends(UserDTO user, String query) {
        return friendsRepo.searchForFriends(user.getId(), query).stream()
                .map(mapper::userToUserDTO).toList();
    }

    @Transactional
    public void addFriend(UserDTO user, Long newFriendId) {
        relationRepo.createRelation(user.getId(), newFriendId, UserRelationsStatuses.SENT.name());
        relationRepo.createRelation(newFriendId, user.getId(), UserRelationsStatuses.RECEIVED.name());
    }
}