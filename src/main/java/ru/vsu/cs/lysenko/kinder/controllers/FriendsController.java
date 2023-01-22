package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.FriendsService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/friends")
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok().body(friendsService.getFriends(user));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<UserDTO>> getRequest(@AuthenticationPrincipal UserDTO user,
                                                    @RequestParam(value = "type") String requestType) {
        return ResponseEntity.ok().body(friendsService.getRequests(user, requestType));
    }

    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@AuthenticationPrincipal UserDTO user, @PathVariable Long friendId) {
        friendsService.deleteFriend(user, friendId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/requests/{friendId}")
    public ResponseEntity<Void> answerFriendRequest(@AuthenticationPrincipal UserDTO user,
                                                    @PathVariable Long friendId,
                                                    @RequestParam(value = "answer") String answer) {
        friendsService.answerFriendRequest(user, friendId, answer);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchFriends(@AuthenticationPrincipal UserDTO user,
                                                       @RequestParam(value = "query") String query) {
        return ResponseEntity.ok().body(friendsService.searchForFriends(user, query));
    }

    //TODO bad design endpoint think over it
    @GetMapping("/search/{newFriendId}")
    public ResponseEntity<List<UserDTO>> addFriend(@AuthenticationPrincipal UserDTO user,
                                                   @PathVariable Long newFriendId) {
        friendsService.addFriend(user, newFriendId);
        return ResponseEntity.noContent().build();
    }
}
