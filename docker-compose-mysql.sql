USE test_mysql

create table sales (
    id character varying (255) NOT NULL,
    id_user character varying (255) NOT NULL,
    constraint sales_pkey primary key (id)
);

insert into sales(id, id_user) values
('sale-1', 'user-1'),
('sale-2', 'user-1'),
('sale-3', 'user-1'),
('sale-4', 'user-1'),
('sale-5', 'user-2'),
('sale-6', 'user-2'),
('sale-7', 'user-3'),
('sale-8', 'user-4'),
('sale-9', 'user-4'),
('sale-10', 'user-5');
