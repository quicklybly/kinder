package ru.vsu.cs.lysenko.kinder.data.repos;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.User;
import ru.vsu.cs.lysenko.kinder.data.entities.UserRelationsStatuses;

import java.util.List;

public interface FriendsRepository extends CrudRepository<User, Long> {
    @Query("SELECT * FROM users AS u " +
            "JOIN relations r on u.user_id=r.right_user_id " +
            "JOIN relation_statuses rs on rs.status_id = r.status_id " +
            "WHERE r.left_user_id = :userId AND rs.description = :status")
    List<User> getRelatedUsersByStatus(Long userId, String status);

    @Query("SELECT * FROM users AS u " +
            "JOIN relations r on u.user_id=r.right_user_id " +
            "JOIN relation_statuses rs on rs.status_id = r.status_id " +
            "WHERE r.left_user_id = :userId AND rs.description = :status " +
            "OFFSET :offset ROWS FETCH NEXT :limit ROWS only"
    )
    List<User> getRelatedUsersByStatusPageable(Long userId, String status, Integer offset, Integer limit);

    @Query("select * from users u" +
            "         where user_id != :userId and" +
            "               not exists(select 1 from relations where left_user_id = :userId and right_user_id = u.user_id)" +
            "order by levenshtein(concat(first_name, second_name), :query) " +
            "offset :offset rows fetch next :limit rows only"
    )
    List<User> searchForFriends(Long userId, String query, Integer offset, Integer limit);

    default List<User> getFriends(Long userId) {
        return getRelatedUsersByStatus(userId, UserRelationsStatuses.ACCEPTED.name());
    }

    default List<User> getFriendsPageable(Long userId, Integer offset, Integer limit) {
        return getRelatedUsersByStatusPageable(
                userId,
                UserRelationsStatuses.ACCEPTED.name(),
                offset,
                limit
        );
    }
}
