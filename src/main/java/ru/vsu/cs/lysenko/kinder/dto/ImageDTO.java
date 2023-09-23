package ru.vsu.cs.lysenko.kinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Long id;
    private Long userId;
    private String path;
}
