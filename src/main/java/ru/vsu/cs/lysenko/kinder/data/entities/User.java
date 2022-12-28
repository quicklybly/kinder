package ru.vsu.cs.lysenko.kinder.data.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

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

}
