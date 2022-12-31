package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT count(user_id) FROM users WHERE username = :username")
    Boolean checkIfUserInRepositoryByUsername(String username);

    @Query("SELECT \"password_hash\" FROM users WHERE username = :username")
    String getPasswordByUsername(String username);
}
