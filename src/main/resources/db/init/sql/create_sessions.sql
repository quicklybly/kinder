CREATE TABLE IF NOT EXISTS sessions
(
    session_id serial primary key not null,
    user_id    bigint             not null,
    hash       varchar            not null
);

ALTER TABLE sessions
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);
