package ru.vsu.cs.lysenko.kinder.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.lysenko.kinder.data.entities.Session;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.dto.CredentialsDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.mappers.UserMapper;
import ru.vsu.cs.lysenko.kinder.utils.HashUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public static final Integer SESSION_HASH_LENGTH = 42;
    public final PasswordEncoder pwEncoder;
    public final UserRepository userRepo;
    public final SessionRepository sessionRepo;
    public final UserMapper userMapper;

    @Transactional
    public UserDTO authenticateByCredentials(CredentialsDTO credentialsDTO) {
        User user = userRepo.findByUsername(credentialsDTO.getUsername())
                .orElseThrow(() -> new AuthenticationServiceException("Wrong username"));
        if (pwEncoder.matches(credentialsDTO.getPassword(), user.getPassword())) {
            return userMapper.userToUserDTO(user);
        }
        throw new AuthenticationServiceException("Wrong password");
    }

    public UserDTO findBySession(String sessionToken) {
        String[] sessionParts = sessionToken.split("&");
        Long userId = Long.valueOf(sessionParts[0]);
        Long sessionId = Long.valueOf(sessionParts[1]);
        String hash = sessionParts[2];

        UserDTO userDTO = userMapper.userToUserDTO(userRepo.findById(userId)
                .orElseThrow(() -> new AuthenticationServiceException("Wrong session value")));
        Optional<Session> session = sessionRepo.findById(sessionId);
        if (session.isPresent() && session.get().getHash().equals(hash)) {
            return userDTO;
        }
        throw new AuthenticationServiceException("Wrong session value");
    }

    public String createSessionToken(UserDTO user) {
        Session session = Session.builder().userId(user.getId()).hash(HashUtils.getRandomString(SESSION_HASH_LENGTH)).build();
        sessionRepo.save(session);
        //DUMMY
        session = sessionRepo.findByUserId(session.getUserId()).orElse(session);
        return String.join("&", session.getUserId().toString(), session.getId().toString(), session.getHash());
    }

    public void clearSession(String sessionToken) {
        sessionRepo.deleteById(Long.valueOf(sessionToken.split("&")[1]));
    }
}
