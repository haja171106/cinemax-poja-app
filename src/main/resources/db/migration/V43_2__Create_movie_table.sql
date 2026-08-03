create table if not exists movie
(
    id          uuid    not null constraint movie_pk primary key default gen_random_uuid(),
    title       varchar not null,
    genre       varchar not null,
    description text,
    duration    bigint  not null
);
