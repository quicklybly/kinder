package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.Session;

public interface SessionRepository extends CrudRepository<Session, Long> {
    void deleteByHash(String hash);

    Boolean existsByHash(String hash);
}
