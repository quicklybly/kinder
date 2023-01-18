package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.config.CookieAuthenticationFilter;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.AuthenticationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LoginController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign-in")
    public ResponseEntity<UserDTO> signIn(@AuthenticationPrincipal UserDTO user, HttpServletResponse response) {
        Cookie sessionTokenCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME,
                authenticationService.createSessionToken(user));
        sessionTokenCookie.setHttpOnly(true);
        sessionTokenCookie.setPath("/");
        sessionTokenCookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(sessionTokenCookie);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDTO user, @CookieValue(name = "session") String sessionToken) {
        authenticationService.clearSession(sessionToken);
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
