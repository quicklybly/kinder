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
import ru.vsu.cs.lysenko.kinder.exceptions.AuthenticationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    public static final String SESSION_COOKIE_NAME = "session";

    @Autowired
    private SignInner signInner;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> signIn(@RequestBody String payload, HttpServletResponse response) {
        User user = parsePayloadToUser(payload);
        JSONObject responseBody = new JSONObject();
        try {

            Cookie cookie = new Cookie(SESSION_COOKIE_NAME, signInner.signIn(user).getHash());
            cookie.setHttpOnly(true);
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

    private User parsePayloadToUser(String payload) {
        JSONObject userJson = new JSONObject(payload);
        return User.builder().username(userJson.getString("username"))
                .password(userJson.getString("password")).build();
    }
}
