package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SessionExistenceController {
    @GetMapping("/exists")
    public ResponseEntity<UserDTO> exists(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok().body(user);
    }
}
