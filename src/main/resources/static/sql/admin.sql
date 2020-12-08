create database online_education;
use online_education;
create table students
(
    user_id   int auto_increment
        primary key,
    wechat_id varchar(20) null,
    nickname  varchar(20) null,
    phone_id  varchar(12) not null,
    sex       varchar(4)  null,
    school    varchar(20) null,
    major     varchar(20) null,
    grade     int         null,
    password  varchar(20) null,
    constraint students_phone_id_uindex
        unique (phone_id)
)

create table teachers
(
    user_id   int auto_increment
        primary key,
    wechat_id varchar(20) null,
    name      varchar(20) null,
    phone_id  varchar(20) not null,
    password  varchar(20) null,
    sex       varchar(4)  null,
    school    varchar(20) null,
    major     varchar(20) null,
    constraint teachers_phone_id_uindex
        unique (phone_id)
)
