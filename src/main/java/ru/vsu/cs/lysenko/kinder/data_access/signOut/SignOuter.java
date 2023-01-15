package ru.vsu.cs.lysenko.kinder.data_access.signOut;

import org.springframework.stereotype.Component;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;

@Component
public class SignOuter {
    private final SessionRepository sessionRepo;

    public SignOuter(SessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    public void signOut(String sessionHash) {
        sessionRepo.deleteByHash(sessionHash);
    }
}
