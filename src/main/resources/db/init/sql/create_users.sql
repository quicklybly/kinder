CREATE TABLE IF NOT EXISTS users
(
    user_id       serial primary key not null,
    username      varchar unique     not null,
    password_hash varchar            not null,
    first_name    varchar            not null,
    second_name   varchar            not null,
    date_of_birth date,
    gender        bigint
);

ALTER TABLE users
    ADD FOREIGN KEY ("gender") REFERENCES "genders" (gender_id);
