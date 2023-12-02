CREATE TABLE IF NOT EXISTS photos
(
    user_id  bigint             not null,
    photo_id serial primary key not null,
    location varchar            not null
);

ALTER TABLE photos
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);
