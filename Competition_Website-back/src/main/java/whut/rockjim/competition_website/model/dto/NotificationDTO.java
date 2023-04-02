package whut.rockjim.competition_website.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.constant.enums.NotificationTypeEnum;

import java.util.Date;

/**
 * 通知返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    /**
     * 通知id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发布人
     */
    private String publishName;

    /**
     * 标题
     */
    private String title;
    /**
     * 通知类型：0-竞赛发布、1-通知公告、2-结果公示、3-系统通知
     */
    private Integer classification;

    /**
     * 阅读量
     */
    private Integer readingNumber;

    /**
     * 发布日期
     */
    private Date publishDate;


    /**
     * 内容
     */
    private String context;
}
