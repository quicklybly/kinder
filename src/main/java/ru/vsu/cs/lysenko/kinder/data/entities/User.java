package ru.vsu.cs.lysenko.kinder.data.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
@Builder
@Table("users")
public class User {
    @Id
    @Column("user_id")
    private Long id;

    @Column("username")
    private String username;

    @Column("password_hash")
    private String password;
    @JsonProperty("firstName")
    @Column("first_name")
    private String firstName;
    @JsonProperty("lastName")
    @Column("second_name")
    private String lastName;

    @Column("date_of_birth")
    private Date dateOfBirth;

    @Column("gender")
    private Long gender;
}
