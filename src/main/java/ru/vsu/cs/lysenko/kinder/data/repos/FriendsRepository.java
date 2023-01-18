package ru.vsu.cs.lysenko.kinder.data.repos;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.User;

import java.util.List;

public interface FriendsRepository extends CrudRepository<User, Long> {
    @Query("SELECT * FROM users AS u " +
            "JOIN relations r on u.user_id=r.right_user_id " +
            "JOIN relation_statuses rs on rs.status_id = r.status_id " +
            "WHERE r.left_user_id = :userId AND rs.description = 'ACCEPTED'")
    List<User> getFriends(Long userId);
}
