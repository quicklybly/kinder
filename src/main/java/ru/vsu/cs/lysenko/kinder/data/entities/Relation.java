package ru.vsu.cs.lysenko.kinder.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("relations")
@NoArgsConstructor
public class Relation {
    @Id
    @Column("relation_id")
    private Long relationId;
    @Column("left_user_id")
    private Long leftUserId;
    @Column("right_user_id")
    private Long rightUserId;
    @Column("status_id")
    private Long statusId;
}
