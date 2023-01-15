package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT true FROM users WHERE username = :username")
    Optional<Boolean> checkIfUserInRepositoryByUsername(String username);

    @Query("SELECT \"password_hash\" FROM users WHERE username = :username")
    String getPasswordByUsername(String username);

    @Query("SELECT \"user_id\" FROM users WHERE username = :username")
    Long getIdByUsername(String username);
}
