-- 增加权限相关表结构

CREATE TABLE `t_resource`
(
    `id`          varchar(32)  NOT NULL,
    `create_time` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `url`         varchar(255) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_role`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `role`        varchar(32) NULL,
    `name`        varchar(64) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_role_resource`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `role_id`     varchar(32) NULL,
    `resource_id` varchar(32) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `t_user_role`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `uid`         varchar(32) NULL,
    `role_id`     varchar(32) NULL,
    PRIMARY KEY (`id`)
);

