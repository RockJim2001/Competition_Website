package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.Notification;

import java.util.Date;


/**
 * 返回通知列表的类：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationListVO {
    /**
     * id--唯一标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *标题
     */
    private String title;

    /**
     * 发布日期
     */
    private Date publishDate;

    /**
     * 通知类型
     */
    private String classification;



    public NotificationListVO(Notification notification){
        this.id=notification.getId();
        this.title=notification.getTitle();
        this.publishDate=notification.getPublishDate();
        this.classification=notification.getClassification().getName();
    }
}
