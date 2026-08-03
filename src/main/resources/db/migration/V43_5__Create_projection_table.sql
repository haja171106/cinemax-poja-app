create table if not exists projection
(
    id         uuid           not null constraint projection_pk primary key default gen_random_uuid(),
    datetime   timestamptz    not null,
    seat_price numeric(10, 2) not null,
    movie_id   uuid           not null constraint projection_movie_fk references movie (id),
    room_id    uuid           not null constraint projection_room_fk references room (id)
);
