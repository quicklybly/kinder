CREATE TABLE IF NOT EXISTS comments
(
    user_id  bigint  not null,
    photo_id bigint  not null,
    text     varchar not null
);

ALTER TABLE comments
    ADD PRIMARY KEY (user_id, photo_id);

ALTER TABLE comments
    ADD FOREIGN KEY ("photo_id") REFERENCES photos (photo_id);

ALTER TABLE comments
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);
