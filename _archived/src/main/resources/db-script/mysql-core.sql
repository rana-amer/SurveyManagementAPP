drop schema if exists user_schema;
create schema if not exists user_schema;
use user_schema;

create table user_role
(
    id    int         not null,
    title varchar(50) not null,
    constraint ph_user_role primary key (id)
);

create table user
(
    id        int auto_increment not null,
    full_name varchar(50)        not null,
    username  varchar(50)        not null,
    role_id   int                not null,
    constraint pk_user primary key (id),
    constraint fk_user_role foreign key (role_id) references user_role (id)
);