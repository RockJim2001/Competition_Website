package whut.rockjim.competition_website.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 通知类型枚举类：0-竞赛发布、1-通知公告、2-结果公示、3-系统通知
 */
@Getter
public enum NotificationTypeEnum {
    PUBLISH(0,"竞赛发布"),
    NOTIFICATION(1,"通知公告"),
    RESULT(2,"结果公示"),
    NOTICE(3,"系统通知");


    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    @EnumValue
    private Integer type;
    private String name;
}
