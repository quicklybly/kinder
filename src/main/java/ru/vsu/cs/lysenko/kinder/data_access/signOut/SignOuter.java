package ru.vsu.cs.lysenko.kinder.data_access.signOut;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.cs.lysenko.kinder.data.repos.SessionRepository;


public class SignOuter {
    @Autowired
    private SessionRepository sessionRepo;

    public void signOut(String sessionHash) {
        sessionRepo.deleteByHash(sessionHash);
    }
}
