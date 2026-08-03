create table if not exists reservation
(
    id            uuid        not null constraint reservation_pk primary key default gen_random_uuid(),
    created_at    timestamptz not null,
    user_id       uuid        not null constraint reservation_user_fk references "user" (id),
    projection_id uuid        not null constraint reservation_projection_fk references projection (id),
    seat_id       uuid        not null constraint reservation_seat_fk references seat (id),
    constraint reservation_projection_seat_unique unique (projection_id, seat_id)
);
