package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.services.UserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<String> registration(@RequestBody SignUpDTO signUpDTO) {
        JSONObject responseBody = new JSONObject();
        try {
            userService.signUp(signUpDTO);
            return ResponseEntity.ok(responseBody.toString());
        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(responseBody.put("cause", e.getMessage()).toString());
        }
    }
}
