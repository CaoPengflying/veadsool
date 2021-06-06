-- auto-generated definition
create table grade
(
    id          int auto_increment
        primary key,
    grade_name  varchar(32)                         null comment '班级名称',
    grade_no    varchar(32)                         null comment '班级编号',
    create_user int       default 30                not null comment '创建人',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user int                                 null comment '修改人',
    update_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '班级' collate = utf8mb4_bin;


-- auto-generated definition
create table grade_files
(
    id          int auto_increment
        primary key,
    grade_id    int                                 null comment '班级标识',
    ave_score   decimal(12, 2)                      null comment '平均分',
    max_score   decimal(12, 2)                      not null comment '最高分',
    min_score   decimal(12, 2)                      not null comment '最低分',
    great_count int                                 not null comment '优秀人数',
    bad_count   int                                 not null comment '不及格人数',
    create_user int                                 not null comment '创建人',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user int                                 null comment '修改人',
    update_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '班级档案' collate = utf8mb4_bin;

-- auto-generated definition
create table rules
(
    id            int auto_increment
        primary key,
    rule_name     varchar(100)                        null comment '规则名称',
    rule_flag     tinyint                             null comment '1:加分  0 减分',
    rule_type     smallint                            not null comment '1:考勤规则  2:奖惩规则',
    change_points decimal(12, 1)                      null comment '分值',
    create_user   int       default 30                not null comment '创建人',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user   int                                 null comment '修改人',
    update_time   timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '基础规则' collate = utf8mb4_bin;

-- auto-generated definition
create table student
(
    id           int auto_increment
        primary key,
    student_name varchar(32)                         null comment '学生姓名',
    student_no   varchar(32)                         null comment '学生学号',
    grade_id     int                                 null comment '所属班级',
    create_user  int       default 30                not null comment '创建人',
    create_time  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user  int                                 null comment '修改人',
    update_time  timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '学生' collate = utf8mb4_bin;

-- auto-generated definition
create table student_credits_flow
(
    id          int auto_increment
        primary key,
    rule_id     int                                 null comment '规则',
    student_id  int                                 null comment '学生标识',
    create_user int       default 30                not null comment '创建人',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user int                                 null comment '修改人',
    update_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '学分变动流水' collate = utf8mb4_bin;

-- auto-generated definition
create table student_files
(
    id                     int auto_increment
        primary key,
    student_id             int                                 null comment '学生标识',
    cultural_subject_score int                                 null comment '文化课成绩',
    attendance_score       decimal(12, 1)                      not null comment '考勤成绩',
    other_score            decimal(12, 1)                      not null comment '其他成绩(奖惩)',
    real_score             decimal(12, 1)                      not null comment '总得分',
    create_user            int       default 30                not null comment '创建人',
    create_time            timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user            int                                 null comment '修改人',
    update_time            timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '学生档案' collate = utf8mb4_bin;

