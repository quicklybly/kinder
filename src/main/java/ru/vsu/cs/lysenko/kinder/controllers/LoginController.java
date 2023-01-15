package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data_access.signIn.SignInner;
import ru.vsu.cs.lysenko.kinder.data_access.signOut.SignOuter;
import ru.vsu.cs.lysenko.kinder.exceptions.AuthenticationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    public static final String SESSION_COOKIE_NAME = "session";

    private final SignInner signInner;
    private final SignOuter signOuter;

    @PostMapping("/sign-in")
    @CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        try {
            ResponseCookie springCookie = ResponseCookie.from(SESSION_COOKIE_NAME, signInner.signIn(user).getHash())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(999999)
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body("");
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(responseBody.put("cause", e.getMessage()).toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-out")
    @CrossOrigin
    public ResponseEntity<Void> signOut(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> sessionHash = readCookie(request, SESSION_COOKIE_NAME);
        sessionHash.ifPresent(signOuter::signOut);
        Cookie sessionCookieToDelete = new Cookie(SESSION_COOKIE_NAME, null);
        sessionCookieToDelete.setMaxAge(0);
        response.addCookie(sessionCookieToDelete);
        return ResponseEntity.noContent().build();
    }

    public Optional<String> readCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
