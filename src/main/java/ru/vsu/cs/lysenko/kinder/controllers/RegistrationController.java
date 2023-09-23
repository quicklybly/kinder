package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.services.UserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/sign-up")
    public void registration(@RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
    }
}
