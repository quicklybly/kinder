package ru.vsu.cs.lysenko.kinder.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

@Controller
public class LoginController {

    public static final String SESSION_COOKIE_NAME = "session";

    @Autowired
    private SignInner signInner;
    @Autowired
    private SignOuter signOuter;

    @PostMapping("/sign-in")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> signIn(@RequestBody String payload, HttpServletResponse response) {
        User user = parsePayloadToUser(payload);
        JSONObject responseBody = new JSONObject();
        try {
            Cookie cookie = new Cookie(SESSION_COOKIE_NAME, signInner.signIn(user).getHash());
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            /*
            TODO deploy only
            cookie.setSecure(true);
             */
            response.addCookie(cookie);
            return ResponseEntity.ok("");
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(responseBody.put("cause", e.getMessage()).toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-out")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<Void> signOut(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> sessionHash = readCookie(request, SESSION_COOKIE_NAME);
        sessionHash.ifPresent(s -> signOuter.signOut(s));
        Cookie sessionCookieToDelete = new Cookie(SESSION_COOKIE_NAME, null);
        sessionCookieToDelete.setMaxAge(0);
        response.addCookie(sessionCookieToDelete);
        return ResponseEntity.noContent().build();
    }

    private User parsePayloadToUser(String payload) {
        JSONObject userJson = new JSONObject(payload);
        return User.builder().username(userJson.getString("username"))
                .password(userJson.getString("password")).build();
    }

    public Optional<String> readCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
