create table if not exists "user"
(
    id         uuid        not null constraint user_pk primary key default gen_random_uuid(),
    first_name varchar     not null,
    last_name  varchar     not null,
    birthdate  date        not null,
    email      varchar     not null constraint user_email_unique unique,
    password   varchar     not null,
    phone      varchar,
    role       varchar     not null
);
