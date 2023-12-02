CREATE TABLE IF NOT EXISTS relation_statuses
(
    status_id   serial primary key not null,
    description varchar            not null
);

INSERT INTO relation_statuses (description)
VALUES ('ACCEPTED');

INSERT INTO relation_statuses (description)
VALUES ('RECEIVED');

INSERT INTO relation_statuses (description)
VALUES ('SENT');
