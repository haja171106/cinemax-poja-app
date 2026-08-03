create table if not exists seat
(
    id      uuid    not null constraint seat_pk primary key default gen_random_uuid(),
    number  varchar not null,
    room_id uuid    not null constraint seat_room_fk references room (id)
);
