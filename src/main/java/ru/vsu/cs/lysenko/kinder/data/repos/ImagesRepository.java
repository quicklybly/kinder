package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.Image;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends CrudRepository<Image, Long> {
    List<Image> getAllByUserId(Long userId);

    @Query("select * " +
            "from photos p " +
            "join profile_pics pp on p.photo_id = pp.photo_id " +
            "where p.photo_id = pp.photo_id and pp.user_id = :userId")
    Optional<Image> getProfilePicture(Long userId);

    @Modifying
    @Query("insert into profile_pics (user_id, photo_id) VALUES (:userId, :imageId) " +
            "on conflict (user_id) do update set photo_id = :imageId")
    void addProfilePicture(Long userId, Long imageId);

    @Modifying
    @Query("delete from profile_pics where photo_id = :imageId")
    void deleteProfilePicture(Long imageId);
}
