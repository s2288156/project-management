CREATE TABLE `t_module`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL,
    `update_time` timestamp   NULL ON UPDATE CURRENT_TIMESTAMP,
    `pid`         varchar(32) NULL COMMENT '项目id',
    `name`        varchar(64) NULL COMMENT '模块名称',
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_module_version`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL,
    `update_time` timestamp   NULL ON UPDATE CURRENT_TIMESTAMP,
    `mid`         varchar(32) NULL COMMENT 'module_id',
    `version`     varchar(20) NULL COMMENT '版本',
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_project`
(
    `id`          varchar(32)  NOT NULL,
    `create_time` timestamp    NULL,
    `update_time` timestamp    NULL ON UPDATE CURRENT_TIMESTAMP,
    `name`        varchar(255) NULL COMMENT '项目名',
    `desc`        varchar(255) NULL COMMENT '描述',
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_user`
(
    `id`          varchar(32)  NOT NULL,
    `create_time` timestamp    NULL,
    `update_time` timestamp    NULL ON UPDATE CURRENT_TIMESTAMP,
    `username`    varchar(32)  NOT NULL COMMENT '登录账户名',
    `password`    varchar(64)  NOT NULL COMMENT '密码',
    `icon`        varchar(255) NULL COMMENT '头像',
    `email`       varchar(64)  NULL COMMENT '邮箱',
    `name`        varchar(32)  NULL COMMENT '姓名',
    PRIMARY KEY (`id`)
);

