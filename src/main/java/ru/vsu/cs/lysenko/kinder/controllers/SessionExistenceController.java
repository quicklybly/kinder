package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SessionExistenceController {
    private final SessionRepository sessionRepo;

    @GetMapping("/exists")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8080")
    public ResponseEntity<Boolean> exists(@CookieValue(name = "session", required = false) String sessionHash) {
        return ResponseEntity.ok()
                .body(sessionRepo.existsByHash(sessionHash));
    }
}
