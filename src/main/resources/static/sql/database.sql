create table admin
(
    user_id  int auto_increment
        primary key,
    phone_id varchar(30) not null,
    password varchar(30) not null,
    constraint admin_phone_id_uindex
        unique (phone_id)
);

create table comment_analysis_result
(
    id             int auto_increment
        primary key,
    course_id      int  null,
    comment_result text null,
    constraint comment_analysis_result_course_id_uindex
        unique (course_id)
);

create table course
(
    course_id       int auto_increment
        primary key,
    name            varchar(20)                          not null,
    intro           text                                 null,
    upload_time     datetime   default CURRENT_TIMESTAMP null,
    teacher_id      int                                  not null,
    prefer_id       int                                  not null,
    course_pic      varchar(150)                         null,
    course_status   int        default 0                 not null,
    need_vip        tinyint(1) default 0                 not null,
    course_avg_mark decimal(2, 1)                        null,
    course_watches  int        default 0                 null
);

create index course_prefer_prefer_id_fk
    on course (prefer_id);

create index teacher_id
    on course (teacher_id);

create table course_chapter
(
    course_id     int          not null,
    chapter_id    int          not null,
    chapter_intro varchar(400) not null,
    primary key (course_id, chapter_id)
);

create table course_chapter_video
(
    course_id  int          not null,
    chapter_id int          not null,
    video_url  varchar(200) null,
    video_id   int          not null,
    video_name varchar(20)  null,
    primary key (chapter_id, course_id, video_id)
);

create index course_chapter_video_course_chapter_course_id_chapter_id_fk
    on course_chapter_video (course_id, chapter_id);

create table course_comment
(
    comment_id   int auto_increment
        primary key,
    likes        int      default 0                 not null,
    content      varchar(100)                       null,
    time         datetime default CURRENT_TIMESTAMP null,
    student_id   int                                not null,
    course_id    int                                not null,
    comment_mark int      default 0                 null
);

create index cid
    on course_comment (course_id);

create index student_id
    on course_comment (student_id);

create table course_watch_record
(
    id         int auto_increment
        primary key,
    course_id  int                                not null,
    student_id int                                not null,
    watch_time datetime default CURRENT_TIMESTAMP null,
    chapter_id int                                null,
    video_id   int                                not null,
    deleted    int      default 0                 null
);

create table homework
(
    homework_id int auto_increment
        primary key,
    content     text                               null,
    likes       int      default 0                 null,
    commit_time datetime default CURRENT_TIMESTAMP null,
    student_id  int                                not null,
    task_id     int                                not null,
    mark        int      default 0                 null,
    reply       varchar(200)                       null,
    status      int      default 0                 null
);

create index homework_task_task_id_fk
    on homework (task_id);

create index student_id
    on homework (student_id);

create table homework_file
(
    homework_file_id int auto_increment
        primary key,
    homework_id      int          not null,
    file_url         varchar(150) null,
    file_name        varchar(300) null
);

create index homework_file_homework_homework_id_fk
    on homework_file (homework_id);

create table item_based_cf
(
    id         int auto_increment
        primary key,
    student_id int    null,
    course_id  int    null,
    cf_mark    double null
);

create index item_based_cf_course_id_index
    on item_based_cf (course_id);

create index item_based_cf_student_id_index
    on item_based_cf (student_id);

create table item_based_cf_result
(
    id         int auto_increment
        primary key,
    student_id int  null,
    result     text null,
    constraint item_based_cf_result_student_id_uindex
        unique (student_id)
);

create table live
(
    live_id         int auto_increment
        primary key,
    live_name       varchar(20)  not null,
    live_intro      text         not null,
    live_date       date         not null,
    live_address_id int          not null,
    teacher_id      int          not null,
    live_pic_url    varchar(300) null,
    live_arrange    int          not null
);

create index live_teacher_id_index
    on live (teacher_id);

create table live_address
(
    live_address_id int auto_increment
        primary key,
    live_address    varchar(100) not null
);

create table major
(
    major_id      int auto_increment
        primary key,
    major_content varchar(10) not null
);

create table prefer
(
    prefer_id      int auto_increment
        primary key,
    prefer_content varchar(10) not null,
    major_id       int         not null
);

create index mid
    on prefer (major_id);

create table relationship_stu_prefer
(
    student_id int not null,
    prefer_id  int not null,
    primary key (student_id, prefer_id)
);

create index pre_id
    on relationship_stu_prefer (prefer_id);

create table stu_like_comment
(
    id         int auto_increment
        primary key,
    student_id int                                null,
    comment_id int                                null,
    time       datetime default CURRENT_TIMESTAMP null
);

create index stu_like_comment_student_id_index
    on stu_like_comment (student_id);

create table stu_like_course
(
    id         int auto_increment
        primary key,
    student_id int                                null,
    course_id  int                                null,
    time       datetime default CURRENT_TIMESTAMP null
);

create table students
(
    user_id         int auto_increment
        primary key,
    nickname        varchar(20)                            null,
    phone_id        varchar(12)                            not null,
    sex             varchar(4)                             null,
    school          varchar(20)                            null,
    grade           varchar(20)                            null,
    password        varchar(20)                            not null,
    student_pic_url varchar(150)                           null,
    major_id        int                                    null,
    vip_date        datetime default '2000-01-01 00:00:00' null
);

create index students_major_major_id_fk
    on students (major_id);

create table task
(
    task_id    int auto_increment
        primary key,
    course_id  int         not null,
    content    text        not null,
    task_name  varchar(20) not null,
    chapter_id int         not null,
    start_time datetime    not null,
    end_time   datetime    not null
);

create index task_course_chapter_course_id_chapter_id_fk
    on task (course_id, chapter_id);

create table task_file
(
    task_file_id int auto_increment
        primary key,
    file_url     varchar(300) null,
    task_id      int          not null,
    file_name    varchar(300) null
);

create index task_file_task_task_id_fk
    on task_file (task_id);

create table teachers
(
    user_id         int auto_increment
        primary key,
    name            varchar(20)          null,
    phone_id        varchar(20)          not null,
    password        varchar(20)          not null,
    sex             varchar(4)           null,
    school          varchar(20)          null,
    major_id        int                  null,
    teacher_pic_url varchar(150)         null,
    teacher_status  tinyint(1) default 0 null,
    intro           text                 null
);

create index teachers_major_major_id_fk
    on teachers (major_id);

