ALTER TABLE `t_module_version` ADD COLUMN `description` varchar(255) NULL COMMENT '版本描述' AFTER `version`;

ALTER TABLE `t_module` ADD COLUMN `description` varchar(255) NULL COMMENT '模块说明' AFTER `name`;
ALTER TABLE `t_module` ADD COLUMN `opening_up` int(2) NULL COMMENT '是否对外开发，0 - 不开放，1 - 开放' AFTER `description`;
ALTER TABLE `t_module` ADD COLUMN `latest_version` varchar(20) NULL COMMENT '最新版本' AFTER `opening_up`;

CREATE TABLE `t_dependence`
(
    `id`          varchar(32) NOT NULL,
    `create_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `pid`         varchar(32) NULL COMMENT '所属项目id',
    `mid`         varchar(32) NULL COMMENT '项目模块id',
    `depend_mid`  varchar(32) NULL COMMENT '当前模块依赖其它模块id',
    PRIMARY KEY (`id`)
);