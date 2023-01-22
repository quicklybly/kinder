package ru.vsu.cs.lysenko.kinder.mappers;

import org.mapstruct.Mapper;
import ru.vsu.cs.lysenko.kinder.data.entities.Image;
import ru.vsu.cs.lysenko.kinder.dto.ImageDTO;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDTO imageToImageDTO(Image image);
}
