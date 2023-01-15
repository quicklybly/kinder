package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data_access.signUp.SignUpper;
import ru.vsu.cs.lysenko.kinder.exceptions.AuthenticationException;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final SignUpper signUpper;
    @PostMapping("/sign-up")
    @CrossOrigin
    public ResponseEntity<String> registration(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        try {
            signUpper.signUp(user);
            return ResponseEntity.ok(responseBody.toString());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody.put("cause", e.getMessage()).toString());
        }
    }
}
