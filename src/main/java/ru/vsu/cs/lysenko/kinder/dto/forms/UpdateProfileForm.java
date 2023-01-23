package ru.vsu.cs.lysenko.kinder.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileForm {
    private UserDTO user;
    private MultipartFile profilePicture;
}
