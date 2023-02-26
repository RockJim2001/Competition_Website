package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.constant.enums.PermissionEnum;
import whut.rockjim.competition_website.model.dto.UserInfoDTO;
import whut.rockjim.competition_website.model.dto.UsersAuthsDTO;
import whut.rockjim.competition_website.model.entity.Users;
import whut.rockjim.competition_website.model.entity.UsersAuths;

/**
 * 用户中心的数据返回
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
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
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    public UserInfoVO(UserInfoDTO userInfoDTO){
        this.id=userInfoDTO.getId();
        this.avatar=userInfoDTO.getAvatar();
        this.school=userInfoDTO.getSchool();
        this.education=userInfoDTO.getEducation();
        this.nickname=userInfoDTO.getNickname();
        this.password=userInfoDTO.getAuthsList().get(0).getCredential();
        this.permission=userInfoDTO.getPermission();
        for (UsersAuthsDTO item : userInfoDTO.getAuthsList()) {
            if(item.getIdentityType()==0)
                this.username=item.getIdentifier();
            if(item.getIdentityType()==1)
                this.email=item.getIdentifier();
        };
    }

    public Users getUsers(String url){
        Users users=new Users();
        users.setId(this.id);
        users.setNickname(this.nickname);
        users.setAvatar(url);
        users.setEducation(this.education);
        users.setIntroduction(this.introduction);
        users.setSchool(this.school);
        return users;
    }

}
