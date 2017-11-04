CREATE KEYSPACE demo_test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

USE demo_test;

CREATE TABLE demo_test.person (
    user_id bigint PRIMARY KEY,
    date_of_birth date,
    first_name text,
    last_name text
);