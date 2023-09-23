package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.FriendsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/friends")
    public List<UserDTO> getFriends(@AuthenticationPrincipal UserDTO user) {
        return friendsService.getFriends(user);
    }

    @GetMapping("/requests")
    public List<UserDTO> getRequest(@AuthenticationPrincipal UserDTO user,
                                    @RequestParam(value = "type") String requestType) {
        return friendsService.getRequests(user, requestType);
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
    public List<UserDTO> searchFriends(@AuthenticationPrincipal UserDTO user,
                                       @RequestParam(value = "query") String query) {
        return friendsService.searchForFriends(user, query);
    }

    //TODO bad design endpoint think over it
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/search/{newFriendId}")
    public void addFriend(@AuthenticationPrincipal UserDTO user,
                                                   @PathVariable Long newFriendId) {
        friendsService.addFriend(user, newFriendId);
    }
}
