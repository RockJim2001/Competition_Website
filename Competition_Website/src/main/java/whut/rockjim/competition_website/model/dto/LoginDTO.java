package whut.rockjim.competition_website.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.constant.enums.PermissionEnum;
import whut.rockjim.competition_website.model.entity.Users;

/**
 * LoginService ---- LoginUserDTO ----> LoginController
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
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
     * 用户权限（0-student、1-organizer、2-judge、3-admin）
     */
    private PermissionEnum permission;
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
     * 构造函数
     * @param users
     */

    public LoginDTO(Users users){
        this.id=users.getId();
        this.nickname= users.getNickname();
        this.avatar=users.getAvatar();
        this.permission=users.getPermission();
        this.introduction=users.getIntroduction();
        this.school=users.getSchool();
        this.education=users.getEducation();
    }
}
