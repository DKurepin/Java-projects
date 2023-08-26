ALTER TABLE MySQL.tasks
add author varchar(255);

create table if not exists comments
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    content varchar(255),
    author varchar(255),
    creation_date date,
    task_name varchar(255)
)
