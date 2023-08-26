USE MySQL;

create table if not exists employees
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    dob date
);

create table if not exists tasks
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    deadline date,
    description varchar(255),
    type varchar(255),
    employee_id bigint references employees(id)
);