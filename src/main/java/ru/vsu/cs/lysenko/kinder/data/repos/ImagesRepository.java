package ru.vsu.cs.lysenko.kinder.data.repos;

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
            "where p.photo_id = pp.photo_id")
    Optional<Image> getProfilePicture(Long userId);
}
