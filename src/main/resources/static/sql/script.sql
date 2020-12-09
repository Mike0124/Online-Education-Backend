create table major
(
    major_id      int auto_increment
        primary key,
    major_content varchar(10) not null
)
    charset = utf8mb4;

create table prefer
(
    prefer_id      int auto_increment
        primary key,
    prefer_content varchar(10) not null,
    major_id       int         not null,
    constraint prefer_ibfk_1
        foreign key (major_id) references major (major_id)
)
    charset = utf8mb4;

create index mid
    on prefer (major_id);

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
    charset = utf8mb4;

create table homework
(
    homework_id int auto_increment
        primary key,
    content     varchar(400)  not null,
    likes       int default 0 not null,
    commit_time date          not null,
    student_id  int           not null,
    task_id     int           not null,
    constraint homework_ibfk_1
        foreign key (student_id) references students (user_id)
)
    charset = utf8mb4;

create index student_id
    on homework (student_id);

create table relationship_stu_prefer
(
    student_id     int         not null,
    prefer_id      int         not null,
    prefer_content varchar(20) null,
    primary key (student_id, prefer_id),
    constraint relationship_stu_prefer_ibfk_1
        foreign key (student_id) references students (user_id),
    constraint relationship_stu_prefer_ibfk_2
        foreign key (prefer_id) references prefer (prefer_id)
)
    charset = utf8mb4;

create index pre_id
    on relationship_stu_prefer (prefer_id);

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
    charset = utf8mb4;

create table class
(
    class_id    int auto_increment
        primary key,
    name        varchar(20)  not null,
    class_num   int          not null,
    intro       varchar(400) not null,
    upload_time date         not null,
    teacher_id  int          not null,
    prefer_id   int          not null,
    constraint class_ibfk_1
        foreign key (teacher_id) references teachers (user_id)
)
    charset = utf8mb4;

create index teacher_id
    on class (teacher_id);

create table comment
(
    comment_id int auto_increment
        primary key,
    likes      int default 0 not null,
    content    varchar(100)  not null,
    time       date          not null,
    student_id int           not null,
    class_id   int           not null,
    father_cid int           null,
    constraint father_cid
        unique (father_cid),
    constraint comment_ibfk_1
        foreign key (student_id) references students (user_id),
    constraint comment_ibfk_2
        foreign key (class_id) references class (class_id)
)
    charset = utf8mb4;

create index cid
    on comment (class_id);

create index student_id
    on comment (student_id);

create table relationship_stu_class_enroll
(
    student_id int not null,
    class_id   int not null,
    primary key (student_id, class_id),
    constraint relationship_stu_class_enroll_ibfk_1
        foreign key (student_id) references students (user_id),
    constraint relationship_stu_class_enroll_ibfk_2
        foreign key (class_id) references class (class_id)
)
    charset = utf8mb4;

create index cla_id
    on relationship_stu_class_enroll (class_id);

create table relationship_stu_class_evaluate
(
    student_id int not null,
    class_id   int not null,
    class_mark int not null,
    primary key (student_id, class_id),
    constraint relationship_stu_class_evaluate_ibfk_1
        foreign key (student_id) references students (user_id),
    constraint relationship_stu_class_evaluate_ibfk_2
        foreign key (class_id) references class (class_id)
)
    charset = utf8mb4;

create index cla_id
    on relationship_stu_class_evaluate (class_id);

create table task
(
    task_id  int auto_increment
        primary key,
    class_id int          not null,
    content  varchar(200) not null,
    constraint task_class_class_id_fk
        foreign key (class_id) references class (class_id)
);


