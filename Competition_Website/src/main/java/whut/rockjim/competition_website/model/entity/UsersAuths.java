package whut.rockjim.competition_website.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import whut.rockjim.competition_website.constant.enums.LoginTypeEnum;

/**
 * 
 * @TableName users_auths
 */
@TableName(value ="users_auths")
@Data
public class UsersAuths implements Serializable {
    /**
     * 权限id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 登录方式：0-username、1-email、2-phone、3-weixin、4-qq、5-weibo
     */
    private LoginTypeEnum identityType;

    /**
     * 标识（手机号、用户名、邮箱或者第三方应用的唯一标识）
     */
    private String identifier;

    /**
     * 密码凭证（站内的密码，站外不保存或者保存的token）
     */
    private String credential;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}