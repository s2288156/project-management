INSERT INTO `t_user`(`id`, `create_time`, `update_time`, `username`, `password`, `icon`, `email`, `name`)
VALUES ('1', now(), now(), 'admin',
        '$2a$10$Rdii.zO76FN/sOG6nGEUVOrnxG6C9leoMamQAXUcnzcczKr4nNUru',
        'https://wcy-img.oss-cn-beijing.aliyuncs.com/images/avatar/default_avatar.jpg', NULL, '老管理员');


INSERT INTO `t_role`(`id`, `create_time`, `update_time`, `role`, `name`)
VALUES ('1', now(), now(), 'ADMIN', '管理员');
INSERT INTO `t_role`(`id`, `create_time`, `update_time`, `role`, `name`)
VALUES ('2', now(), now(), 'GUEST', '参观者');

INSERT INTO `t_user_role`(`id`, `create_time`, `update_time`, `uid`, `role_id`)
VALUES ('1', now(), now(), '1', '1');