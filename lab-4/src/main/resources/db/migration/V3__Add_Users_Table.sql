create table if not exists users
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) unique,
    password varchar(255),
    role varchar(255) default 'USER',
    status varchar(255) default 'ACTIVE',
    employee_id bigint references employees(id)
);
