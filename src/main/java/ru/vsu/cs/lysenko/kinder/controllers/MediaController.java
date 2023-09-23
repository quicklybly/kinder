package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.lysenko.kinder.dto.ImageDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.MediaService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class MediaController {
    private final MediaService mediaService;

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImages(@PathVariable Long imageId) {
        return mediaService.getImage(imageId);
    }

    @PostMapping
    public ImageDTO postImage(@AuthenticationPrincipal UserDTO user,
                              @RequestParam(value = "image") MultipartFile file) {
        return mediaService.postImage(user, file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{imageId}")
    public void deleteImage(@AuthenticationPrincipal UserDTO user,
                            @PathVariable Long imageId) {
        mediaService.deleteImage(user, imageId);
    }
}