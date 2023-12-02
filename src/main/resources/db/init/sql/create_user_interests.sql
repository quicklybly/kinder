CREATE TABLE IF NOT EXISTS user_interest
(
    user_id     bigint not null,
    interest_id bigint not null
);

ALTER TABLE user_interest
    ADD PRIMARY KEY (user_id, interest_id);

ALTER TABLE user_interest
    ADD FOREIGN KEY ("user_id") REFERENCES users (user_id);

ALTER TABLE user_interest
    ADD FOREIGN KEY ("interest_id") REFERENCES interests (interest_id);
