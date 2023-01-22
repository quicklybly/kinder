package ru.vsu.cs.lysenko.kinder.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("photos")
public class Image {
    @Id
    @Column("photo_id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("location")
    private String path;
}
