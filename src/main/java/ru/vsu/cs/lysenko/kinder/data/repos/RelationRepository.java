package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.Relation;

public interface RelationRepository extends CrudRepository<Relation, Long> {
    @Modifying
    @Query("delete from relations where (left_user_id=:leftUserId and right_user_id=:rightUserId)" +
            "or (left_user_id=:rightUserId and right_user_id=:leftUserId)")
    void deleteByLeftUserIdAndRightUserId(Long leftUserId, Long rightUserId);

    @Modifying
    @Query("update relations\n" +
            "set status_id = (select status_id from relation_statuses where description = 'ACCEPTED')" +
            "where (left_user_id = :leftUserId and right_user_id = :rightUserId) " +
            "or (left_user_id = :rightUserId and right_user_id = :leftUserId)")
    void acceptFriendRequest(Long leftUserId, Long rightUserId);
}
