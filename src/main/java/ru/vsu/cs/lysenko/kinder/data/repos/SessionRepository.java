package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.Session;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
    void deleteByHash(String hash);

    Boolean existsByHash(String hash);

    Optional<Session> findByUserId(Long userId);

    Optional<Session> findByHash(String hash);
}
