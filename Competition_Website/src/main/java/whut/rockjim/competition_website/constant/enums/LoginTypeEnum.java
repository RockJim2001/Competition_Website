package whut.rockjim.competition_website.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum LoginTypeEnum {
    USERNAME(0,"用户名"),
    EMAIL(1,"邮箱"),
    PHONE(2,"电话号码"),
    WEIXIN(3,"微信"),
    QQ(4,"QQ"),
    WEIBO(5,"微博");

    LoginTypeEnum(Integer loginType, String loginTypeName) {
        this.loginType = loginType;
        this.loginTypeName = loginTypeName;
    }

    @EnumValue
    private Integer loginType;
    private String loginTypeName;
}

