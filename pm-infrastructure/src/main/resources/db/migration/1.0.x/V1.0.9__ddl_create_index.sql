ALTER TABLE `t_resource` ADD UNIQUE INDEX `uk_url`(`url`) USING BTREE;

ALTER TABLE `t_role` ADD UNIQUE INDEX `uk_role`(`role`) USING BTREE;

ALTER TABLE `t_user` ADD UNIQUE INDEX `uk_username`(`username`) USING BTREE;
ALTER TABLE `t_user` ADD UNIQUE INDEX `uk_email`(`email`) USING BTREE;