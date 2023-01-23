package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.FriendsRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.ImagesRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.dto.ProfileDTO;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.mappers.ImageMapper;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final FriendsRepository friendsRepo;
    private final ImagesRepository imagesRepo;
    private final UserMapper userMapper;
    private final ImageMapper imageMapper;
    private final PasswordEncoder pwEncoder;

    public void signUp(SignUpDTO signUpDTO) throws AppException {
        Optional<User> optionalUser = userRepo.findByUsername(signUpDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.CONFLICT);
        }
        User user = userMapper.signUpToUser(signUpDTO);
        user.setPassword(pwEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public ProfileDTO getProfile(Long userId) {
        return ProfileDTO.builder()
                .user(userMapper.userToUserDTO(userRepo.findById(userId)
                        .orElseThrow(() -> new AppException("No such user", HttpStatus.BAD_REQUEST))))
                .friends(friendsRepo.getFriends(userId).
                        stream().map(userMapper::userToUserDTO).toList())
                .profilePicture(imageMapper
                        .imageToImageDTO(imagesRepo.getProfilePicture(userId).orElse(null)))
                .images(imagesRepo.getAllByUserId(userId)
                        .stream().map(imageMapper::imageToImageDTO).toList())
                .build();
    }
}
