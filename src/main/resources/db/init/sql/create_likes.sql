CREATE TABLE IF NOT EXISTS likes
(
    user_id  bigint not null,
    photo_id bigint not null
);

ALTER TABLE likes
    ADD PRIMARY KEY (user_id, photo_id);

ALTER TABLE likes
    ADD FOREIGN KEY ("photo_id") REFERENCES photos (photo_id);

ALTER TABLE likes
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);
