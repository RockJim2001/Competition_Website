package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.Notification;

import java.util.Date;

/**
 * 通知详情返回实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDetailVO {
    /**
     * 通知id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发布人id
     */

    /**
     * 发布人名称
     */
    private String publishName;

    /**
     * 标题
     */
    private String title;
    /**
     * 通知类别：0-竞赛发布、1-通知公告、2-结果公示、3-系统通知
     */
    private String classification;

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

    /**
     * @param notification
     * @param publishName
     */
    public NotificationDetailVO(Notification notification,String publishName){
        this.id=notification.getId();
        this.publishName=publishName;
        this.title=notification.getTitle();
        this.context=notification.getContext();
        this.publishDate=notification.getPublishDate();
        this.readingNumber= notification.getReadingNumber();
        this.classification=notification.getClassification().getName();
    }
}
