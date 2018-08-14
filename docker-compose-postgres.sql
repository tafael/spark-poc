create table public.user (
    id character varying (255) NOT NULL,
    name character varying (255) NOT NULL,
    constraint user_pkey primary key (id)
);

insert into public.user(id, name) values
('user-1', 'user-1'),
('user-2', 'user-2'),
('user-3', 'user-3'),
('user-4', 'user-4'),
('user-5', 'user-5');

