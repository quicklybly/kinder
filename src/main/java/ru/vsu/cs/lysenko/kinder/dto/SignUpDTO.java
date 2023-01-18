package ru.vsu.cs.lysenko.kinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
