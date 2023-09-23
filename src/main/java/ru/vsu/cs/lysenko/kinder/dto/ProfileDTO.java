package ru.vsu.cs.lysenko.kinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {
    private UserDTO user;
    private List<UserDTO> friends;
    private ImageDTO profilePicture;
    private List<ImageDTO> images;
}
