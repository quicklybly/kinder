package ru.vsu.cs.lysenko.kinder.data.repos;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.lysenko.kinder.data.entities.Image;

public interface ImagesRepository extends CrudRepository<Image, Long> {

}