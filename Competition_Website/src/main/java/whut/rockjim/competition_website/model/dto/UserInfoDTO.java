package whut.rockjim.competition_website.model.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.UsersAuths;

import java.util.List;

/**
 * 用户详细信息返回实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    /**
     * 用户id
     */
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
    private Integer permission;

    /**
     * 登录方式
     */
    private List<UsersAuthsDTO> authsList;
}
