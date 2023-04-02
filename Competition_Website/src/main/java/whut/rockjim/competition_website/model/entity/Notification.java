package whut.rockjim.competition_website.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import whut.rockjim.competition_website.constant.enums.NotificationTypeEnum;

/**
 * 
 * @TableName notification
 */
@TableName(value ="notification")
@Data
public class Notification implements Serializable {
    /**
     * 通知id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发布人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publishId;

    /**
     * 标题
     */
    private String title;
    /**
     * 通知类型：0-竞赛发布、1-通知公告、2-结果公示、3-系统通知
     */
    private NotificationTypeEnum classification;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}