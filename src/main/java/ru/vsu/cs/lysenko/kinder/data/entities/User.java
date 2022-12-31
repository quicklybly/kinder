package ru.vsu.cs.lysenko.kinder.data.entities;

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

    @Column("first_name")
    private String name;

    @Column("second_name")
    private String surname;

    @Column("date_of_birth")
    private Date dateOfBirth;

    @Column("gender")
    private Long gender;
}
