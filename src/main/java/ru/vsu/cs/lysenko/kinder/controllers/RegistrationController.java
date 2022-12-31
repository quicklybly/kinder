package ru.vsu.cs.lysenko.kinder.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data_access.signUp.SignUpper;

@Controller
public class RegistrationController {
    @Autowired
    private SignUpper signUpper;

    @Autowired
    private PasswordEncoder pwEncoder;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public String registration(@RequestBody String payload) {
        JSONObject response = signUpper.signUp(parsePayloadToUser(payload));
        return response.toString();
    }

    private User parsePayloadToUser(String payload) {
        JSONObject userJson = new JSONObject(payload);
        return User.builder().username(userJson.getString("username"))
                .password(pwEncoder.encode(userJson.getString("password")))
                .name(userJson.getString("firstName"))
                .surname(userJson.getString("lastName")).build();
    }

}
