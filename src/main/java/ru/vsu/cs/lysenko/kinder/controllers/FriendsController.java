package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.FriendsService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RequiredArgsConstructor
@Validated
@RestController
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/friends")
    public Page<UserDTO> getFriends(
            @AuthenticationPrincipal UserDTO user,
            @RequestParam(name = "pageSize", required = false, defaultValue = "50") @Positive
            Integer pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") @PositiveOrZero
            Integer pageNumber
    ) {
        return friendsService.getFriends(user, pageSize, pageNumber);
    }

    @GetMapping("/requests")
    public Page<UserDTO> getRequest(
            @AuthenticationPrincipal UserDTO user,
            @RequestParam(value = "type") String requestType,
            @RequestParam(name = "pageSize", required = false, defaultValue = "50") @Positive
            Integer pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") @PositiveOrZero
            Integer pageNumber
    ) {
        return friendsService.getRequests(user, requestType, pageSize, pageNumber);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/friends/{friendId}")
    public void deleteFriend(@AuthenticationPrincipal UserDTO user, @PathVariable Long friendId) {
        friendsService.deleteFriend(user, friendId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/requests/{friendId}")
    public void answerFriendRequest(@AuthenticationPrincipal UserDTO user,
                                    @PathVariable Long friendId,
                                    @RequestParam(value = "answer") String answer) {
        friendsService.answerFriendRequest(user, friendId, answer);
    }

    @GetMapping("/search")
    public Page<UserDTO> searchFriends(
            @AuthenticationPrincipal UserDTO user,
            @RequestParam(value = "query") String query,
            @RequestParam(name = "pageSize", required = false, defaultValue = "50") @Positive
            Integer pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") @PositiveOrZero
            Integer pageNumber
    ) {
        return friendsService.searchForFriends(user, query, pageSize, pageNumber);
    }

    //TODO bad design endpoint think over it
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/search/{newFriendId}")
    public void addFriend(@AuthenticationPrincipal UserDTO user,
                          @PathVariable Long newFriendId) {
        friendsService.addFriend(user, newFriendId);
    }
}
