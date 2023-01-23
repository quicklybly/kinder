package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.lysenko.kinder.data.entities.Image;
import ru.vsu.cs.lysenko.kinder.data.repos.ImagesRepository;
import ru.vsu.cs.lysenko.kinder.dto.ImageDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.mappers.ImageMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MediaService {
    @Value("${app.nfs.path}")
    private String IMAGES_PATH;
    private final ImagesRepository imagesRepo;

    private final ImageMapper mapper;

    public byte[] getImage(Long imageId) {
        ImageDTO image = mapper.imageToImageDTO(imagesRepo.findById(imageId)
                .orElseThrow(() -> new AppException("No such image", HttpStatus.NOT_FOUND)));
        Path path = Paths.get(image.getPath());
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new AppException("No such image", HttpStatus.NOT_FOUND);
        }
    }

    public ImageDTO postImage(UserDTO user, MultipartFile file) {
        String basePath = IMAGES_PATH + "/" + user.getId().toString();
        Path directoryPath = Paths.get(basePath);
        try {
            if (Files.notExists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            Image image = imagesRepo.save(Image.builder()
                    .userId(user.getId())
                    .path(generatePath(basePath, file.getOriginalFilename()))
                    .build());
            file.transferTo(Paths.get(image.getPath()));
            return mapper.imageToImageDTO(image);
        } catch (IOException | IllegalStateException e) {
            throw new AppException("Something wrong with file system", HttpStatus.NOT_FOUND);
        }
    }

    private String generatePath(String basePath, String fileName) {
        return basePath + "/" + Instant.now().toString() + "-" + fileName;
    }
}
