package com.pm.infrastructure.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author wcy-auto
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName(autoResultMap = true, value = "t_user")
public class UserDO {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 登录账户名
     **/
    private String username;

    /**
     * 密码
     **/
    private String password;

    /**
     * 头像
     **/
    private String icon;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 姓名
     **/
    private String name;

}
