package ru.vsu.cs.lysenko.kinder.data_access.signIn;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vsu.cs.lysenko.kinder.data.entities.Session;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;
import ru.vsu.cs.lysenko.kinder.data.repos.UserRepository;
import ru.vsu.cs.lysenko.kinder.utils.ResponseUtils;

public class DataBaseSignInner implements SignInner {

    private final int SESSION_HASH_LENGTH = 21;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private PasswordEncoder pwEncoder;

    @Override
    public JSONObject signIn(User user) {
        JSONObject response = ResponseUtils.prepareResponse();
        if (!userRepo.checkIfUserInRepositoryByUsername(user.getUsername())) {
            response.put("status", "error");
            response.put("message", "Wrong username");
            return response;
        }
        if (!pwEncoder.encode(user.getPassword()).equals(userRepo.getPasswordByUsername(user.getUsername()))) {
            response.put("status", "error");
            response.put("message", "Wrong password");
            return response;
        }
        return response;
    }

//    private void createSession(Long userId) {
//        Session session = new Session();
//        session.setUserId(userId);
//        session.
//    }
}
