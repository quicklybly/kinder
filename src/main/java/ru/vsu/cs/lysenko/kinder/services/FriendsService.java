package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<UserDTO> getFriends(UserDTO user, Integer pageSize, Integer pageNumber) {
        // haha, overflow I just don't care
        Integer offset = pageSize * pageNumber;
        List<UserDTO> content = friendsRepo.getFriendsPageable(user.getId(), offset, pageSize).
                stream().map(mapper::userToUserDTO).toList();
        Long total = friendsRepo.countFriends(user.getId());
        return new PageImpl<>(content, getDefaultPageRequest(pageNumber, pageSize), total);
    }

    public Page<UserDTO> getRequests(
            UserDTO user,
            String requestType,
            Integer pageSize,
            Integer pageNumber
    ) {
        try {
            Integer offset = pageSize * pageNumber;
            String requestTypeString = UserRelationsStatuses.valueOf(requestType.toUpperCase()).name();
            List<UserDTO> content = friendsRepo.getRelatedUsersByStatusPageable(
                            user.getId(),
                            requestTypeString,
                            offset,
                            pageSize
                    )
                    .stream().map(mapper::userToUserDTO).toList();
            Long total = friendsRepo.countRelatedUsersByStatus(user.getId(), requestTypeString);
            return new PageImpl<>(content, getDefaultPageRequest(pageNumber, pageSize), total);
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

    public Page<UserDTO> searchForFriends(
            UserDTO user,
            String query,
            Integer pageSize, Integer pageNumber
    ) {
        Integer offset = pageSize * pageNumber;
        List<UserDTO> content = friendsRepo.searchForFriends(
                user.getId(),
                query,
                offset,
                pageSize
        ).stream().map(mapper::userToUserDTO).toList();
        long total = friendsRepo.count();
        return new PageImpl<>(content, getDefaultPageRequest(pageNumber, pageSize), total);
    }

    @Transactional
    public void addFriend(UserDTO user, Long newFriendId) {
        relationRepo.createRelation(user.getId(), newFriendId, UserRelationsStatuses.SENT.name());
        relationRepo.createRelation(newFriendId, user.getId(), UserRelationsStatuses.RECEIVED.name());
    }

    private PageRequest getDefaultPageRequest(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize, Sort.unsorted());
    }
}
