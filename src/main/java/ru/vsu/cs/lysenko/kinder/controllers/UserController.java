package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.lysenko.kinder.dto.ImageDTO;
import ru.vsu.cs.lysenko.kinder.dto.ProfileDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.dto.forms.UpdateProfileForm;
import ru.vsu.cs.lysenko.kinder.services.UserService;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ProfileDTO getProfile(@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}")
    public void updateProfile(@AuthenticationPrincipal UserDTO user,
                              UpdateProfileForm form) {
        if (Objects.nonNull(form.getUser())) {
            userService.updateProfile(user, form.getUser());
        }

        if (Objects.nonNull(form.getProfilePicture())) {
            userService.updateProfilePicture(user, form.getProfilePicture());
        }
    }

    @GetMapping("/{userId}/avatar")
    public ImageDTO getProfilePicture(@PathVariable Long userId) {
        return userService.getAvatar(userId);
    }
}
