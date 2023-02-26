package whut.rockjim.competition_website.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PermissionEnum {
    STUDENT(0,"学生"),
    ORGANIZER(1,"举办方"),
    JUDGE(2,"评委"),
    ADMIN(3,"管理员");

    PermissionEnum(Integer permission, String permissionType) {
        this.permission = permission;
        this.permissionType = permissionType;
    }

    @EnumValue
    private Integer permission;
    private String permissionType;

}
