create table if not exists room
(
    id       uuid    not null constraint room_pk primary key default gen_random_uuid(),
    number   varchar not null constraint room_number_unique unique,
    capacity int     not null
);
