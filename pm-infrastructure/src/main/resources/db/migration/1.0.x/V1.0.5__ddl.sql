ALTER TABLE `t_dependence` DROP COLUMN `mid`;
ALTER TABLE `t_dependence` MODIFY COLUMN `pid` varchar(32) NULL COMMENT '项目id' AFTER `update_time`;
ALTER TABLE `t_dependence` ADD COLUMN `depend_module_info` varchar(255) NULL COMMENT '依赖模块信息' AFTER `depend_mid`;