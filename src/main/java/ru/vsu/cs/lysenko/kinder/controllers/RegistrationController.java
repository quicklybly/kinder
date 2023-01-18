package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.services.UserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<Void> registration(@RequestBody SignUpDTO signUpDTO) {
            userService.signUp(signUpDTO);
        return ResponseEntity.noContent().build();
    }
}
