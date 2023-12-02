CREATE TABLE IF NOT EXISTS relations
(
    left_user_id  bigint             not null,
    right_user_id bigint             not null,
    status_id     bigint             not null,
    relation_id   serial primary key not null
);

ALTER TABLE relations
    ADD FOREIGN KEY ("left_user_id") REFERENCES users (user_id);

ALTER TABLE relations
    ADD FOREIGN KEY (right_user_id) REFERENCES users (user_id);

ALTER TABLE relations
    ADD FOREIGN KEY ("status_id") REFERENCES relation_statuses (status_id);

