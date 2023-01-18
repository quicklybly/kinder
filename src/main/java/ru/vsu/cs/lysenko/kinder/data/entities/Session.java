package ru.vsu.cs.lysenko.kinder.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("sessions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @Column("session_id")
    private Long id;

    @Column("hash")
    private String hash;

    @Column("user_id")
    private Long userId;
}
