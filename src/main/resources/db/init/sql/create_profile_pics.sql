CREATE TABLE IF NOT EXISTS profile_pics
(
    user_id  bigint primary key not null,
    photo_id bigint not null
);

ALTER TABLE profile_pics
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);

ALTER TABLE profile_pics
    ADD FOREIGN KEY ("photo_id") REFERENCES photos (photo_id);
