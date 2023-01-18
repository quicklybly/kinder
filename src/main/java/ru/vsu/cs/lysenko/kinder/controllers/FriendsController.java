package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
