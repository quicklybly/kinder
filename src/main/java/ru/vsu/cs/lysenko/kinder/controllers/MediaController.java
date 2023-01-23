package ru.vsu.cs.lysenko.kinder.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.lysenko.kinder.dto.ImageDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.MediaService;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @GetMapping(value = "/images/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserImages(@PathVariable Long imageId) {
        return ResponseEntity.ok(mediaService.getImage(imageId));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> postImage(@AuthenticationPrincipal UserDTO user,
                                              @RequestParam(value = "image") MultipartFile file) {
        return ResponseEntity.ok(mediaService.postImage(user, file));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@AuthenticationPrincipal UserDTO user,
                                            @PathVariable Long imageId) {
        mediaService.deleteImage(user, imageId);
        return ResponseEntity.noContent().build();
    }
}