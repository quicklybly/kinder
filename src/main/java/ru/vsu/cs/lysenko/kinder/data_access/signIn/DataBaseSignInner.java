package ru.vsu.cs.lysenko.kinder.data_access.signIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vsu.cs.lysenko.kinder.data.entities.Session;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.exceptions.AuthenticationException;
import ru.vsu.cs.lysenko.kinder.exceptions.WrongPasswordException;
import ru.vsu.cs.lysenko.kinder.exceptions.WrongUserNameException;
import ru.vsu.cs.lysenko.kinder.utils.HashUtils;

import java.util.Optional;

public class DataBaseSignInner implements SignInner {

    private static final int SESSION_HASH_LENGTH = 41;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private PasswordEncoder pwEncoder;

    @Override
    public Session signIn(User user) throws AuthenticationException {
        Optional<Boolean> isUserInDatabase = userRepo.checkIfUserInRepositoryByUsername(user.getUsername());
        if (!isUserInDatabase.orElse(false)) {
            throw new WrongUserNameException();
        }
        if (!pwEncoder.matches(user.getPassword(), userRepo.getPasswordByUsername(user.getUsername()))) {
            throw new WrongPasswordException();
        }
        user.setId(userRepo.getIdByUsername(user.getUsername()));
        Session session = createSession(user.getId());
        //TODO fix duplicates sessionHash
        sessionRepo.save(session);
        return session;
    }

    private Session createSession(Long userId) {
        Session session = new Session();
        session.setUserId(userId);
        session.setHash(HashUtils.getRandomString(SESSION_HASH_LENGTH));
        return session;
    }
}
