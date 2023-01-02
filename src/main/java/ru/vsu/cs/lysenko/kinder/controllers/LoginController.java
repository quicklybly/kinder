package ru.vsu.cs.lysenko.kinder.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data_access.signIn.SignInner;

@Controller
public class LoginController {

    @Autowired
    private SignInner signInner;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public String signIn(@RequestBody String payload) {
        User user = parsePayloadToUser(payload);
        return signInner.signIn(user).toString();
    }

    private User parsePayloadToUser(String payload) {
        JSONObject userJson = new JSONObject(payload);
        return User.builder().username(userJson.getString("username"))
                .password(userJson.getString("password")).build();
    }
}
