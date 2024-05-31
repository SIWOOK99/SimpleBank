drop table if exists board CASCADE;
drop table if exists users CASCADE;

create table users (
       user_id bigint not null auto_increment,
        login_id varchar(100) unique,
        password varchar(100),
        name varchar(100),
        age int,
        primary key (user_id)
    ) engine=InnoDB default charset=utf8mb4;

create table board (
       board_id bigint not null auto_increment,
        title varchar(255),
        content text,
        user_id bigint,
        register_date datetime,
        primary key (board_id),
        constraint fk_user_board
        foreign key (user_id)
        references users (user_id) on update cascade
    ) engine=InnoDB default charset=utf8mb4;