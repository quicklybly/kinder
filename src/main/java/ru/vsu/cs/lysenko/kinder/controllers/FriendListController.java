package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.FriendListService;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendListController {

    private final FriendListService friendListService;
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok().body(friendListService.getFriends(user));
    }
}
