package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User extends BasePojo {
    @TableId(type = IdType.AUTO)//标识主键自增
    private Long id;
    private String username;
    private String password; //密码加密处理
    private String phone;   //电话
    private String email;   //邮箱
}
