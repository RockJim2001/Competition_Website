package whut.rockjim.competition_website.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import whut.rockjim.competition_website.constant.enums.PermissionEnum;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 个人简介
     */
    private String introduction;
    /**
     * 就读学校
     */
    private String school;
    /**
     * 学历
     */
    private String education;

    /**
     * 用户权限（0-student、1-organizer、2-judge、3-admin）
     */
    private PermissionEnum permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}