package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.dto.SignUpDTO;
import ru.vsu.cs.lysenko.kinder.exceptions.AppException;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder pwEncoder;

    public void signUp(SignUpDTO signUpDTO) throws AppException{
        Optional<User> optionalUser = userRepo.findByUsername(signUpDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.CONFLICT);
        }
        User user = userMapper.signUpToUser(signUpDTO);
        user.setPassword(pwEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}
