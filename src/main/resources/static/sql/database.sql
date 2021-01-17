create table if not exists course
(
    course_id int auto_increment
    primary key,
    name varchar(20) not null,
    course_num int not null,
    intro varchar(400) not null,
    upload_time datetime not null,
    teacher_id int not null,
    prefer_id int not null,
    course_pic mediumblob not null,
    course_status int default 0 not null,
    need_vip tinyint(1) default 0 not null,
    major_id int not null,
    course_avg_mark decimal(1,1) null,
    course_watches int null
    );

create index course_prefer_prefer_id_fk
	on course (prefer_id);

create index teacher_id
	on course (teacher_id);

create table if not exists course_chapter
(
    course_id int not null,
    chapter_id int not null,
    chapter_intro varchar(400) not null,
    primary key (course_id, chapter_id)
    );

create table if not exists course_chapter_video
(
    course_id int not null,
    chapter_id int not null,
    video_url varchar(40) null,
    video_id int not null,
    video_name varchar(20) null,
    primary key (chapter_id, course_id, video_id),
    constraint course_chapter_video_course_chapter_course_id_chapter_id_fk
    foreign key (course_id, chapter_id) references course_chapter (course_id, chapter_id)
    );

create table if not exists course_watch_record
(
    id int auto_increment
    primary key,
    course_id int not null,
    student_id int not null,
    watch_time datetime not null,
    chapter_id int null,
    video_id int not null,
    time datetime not null
);

create table if not exists major
(
    major_id int auto_increment
    primary key,
    major_content varchar(10) not null
    );

create table if not exists prefer
(
    prefer_id int auto_increment
    primary key,
    prefer_content varchar(10) not null,
    major_id int not null,
    constraint prefer_ibfk_1
    foreign key (major_id) references major (major_id)
    );

create index mid
	on prefer (major_id);

create table if not exists students
(
    user_id int auto_increment
    primary key,
    wechat_id varchar(20) null,
    nickname varchar(20) null,
    phone_id varchar(12) not null,
    sex varchar(4) null,
    school varchar(20) null,
    grade int null,
    password varchar(20) not null,
    student_pic mediumblob null,
    is_vip tinyint(1) default 0 not null,
    major_id int null,
    constraint students_phone_id_uindex
    unique (phone_id),
    constraint students_major_major_id_fk
    foreign key (major_id) references major (major_id)
    );

create table if not exists course_comment
(
    comment_id int auto_increment
    primary key,
    likes int default 0 not null,
    content varchar(100) not null,
    time datetime not null,
    student_id int not null,
    course_id int not null,
    comment_mark int not null,
    constraint course_comment_ibfk_1
    foreign key (student_id) references students (user_id),
    constraint course_comment_ibfk_2
    foreign key (course_id) references course (course_id)
    );

create index cid
	on course_comment (course_id);

create index student_id
	on course_comment (student_id);

create table if not exists relationship_stu_prefer
(
    student_id int not null,
    prefer_id int not null,
    primary key (student_id, prefer_id),
    constraint relationship_stu_prefer_ibfk_1
    foreign key (student_id) references students (user_id),
    constraint relationship_stu_prefer_ibfk_2
    foreign key (prefer_id) references prefer (prefer_id)
    );

create index pre_id
	on relationship_stu_prefer (prefer_id);

create table if not exists task
(
    task_id int auto_increment
    primary key,
    course_id int not null,
    content varchar(200) not null,
    task_name varchar(20) not null,
    chapter_id int not null,
    start_time datetime not null,
    end_time datetime not null,
    constraint task_course_chapter_course_id_chapter_id_fk
    foreign key (course_id, chapter_id) references course_chapter (course_id, chapter_id)
    );

create table if not exists homework
(
    homework_id int auto_increment
    primary key,
    content varchar(400) not null,
    likes int default 0 not null,
    commit_time datetime not null,
    student_id int not null,
    task_id int not null,
    course_id int null,
    chapte_id int null,
    constraint homework_course_chapter_course_id_chapter_id_fk
    foreign key (course_id, chapte_id) references course_chapter (course_id, chapter_id),
    constraint homework_ibfk_1
    foreign key (student_id) references students (user_id),
    constraint homework_task_task_id_fk
    foreign key (task_id) references task (task_id)
    );

create index student_id
	on homework (student_id);

create table if not exists homework_file
(
    homework_file_id int auto_increment
    primary key,
    homework_id int not null,
    task_content mediumblob not null,
    constraint homework_file_homework_homework_id_fk
    foreign key (homework_id) references homework (homework_id)
    );

create table if not exists task_file
(
    task_file_id int auto_increment
    primary key,
    file_content mediumblob not null,
    task_id int not null,
    constraint task_file_task_task_id_fk
    foreign key (task_id) references task (task_id)
    );

create table if not exists teacher_comment
(
    comment_id int auto_increment
    primary key,
    likes int default 0 not null,
    content varchar(100) not null,
    time date not null,
    student_id int not null,
    course_id int not null,
    comment_mark int not null,
    constraint comment_ibfk_1
    foreign key (student_id) references students (user_id)
    );

create table if not exists teachers
(
    user_id int auto_increment
    primary key,
    wechat_id varchar(20) null,
    name varchar(20) null,
    phone_id varchar(20) not null,
    password varchar(20) not null,
    sex varchar(4) null,
    school varchar(20) null,
    major_id int null,
    teacher_pic mediumblob null,
    teacher_status tinyint(1) default 0 null,
    constraint teachers_phone_id_uindex
    unique (phone_id),
    constraint teachers_major_major_id_fk
    foreign key (major_id) references major (major_id)
    );

create table if not exists live
(
    live_id int auto_increment
    primary key,
    live_name varchar(20) not null,
    live_intro varchar(100) not null,
    live_start_time datetime not null,
    live_address varchar(100) not null,
    teacher_id int not null,
    constraint live_teachers_user_id_fk
    foreign key (teacher_id) references teachers (user_id)
    );

