CREATE TABLE `t_group`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `name`        varchar(32) NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `t_project` ADD COLUMN `group_id` varchar(32) NULL AFTER `update_time`;