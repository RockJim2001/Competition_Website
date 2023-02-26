package whut.rockjim.competition_website.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName competition
 */
@TableName(value ="competition")
@Data
public class Competition implements Serializable {
    /**
     * 竞赛id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 竞赛举办方id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long organizerId;

    /**
     * 竞赛url地址
     */
    private String url;

    /**
     * 宣传海报（图片）
     */
    private String poster;

    /**
     * 名称
     */
    private String title;

    /**
     * 描述或者摘要
     */
    private String summary;

    /**
     * 竞赛级别：0-校赛、1-省赛、2-校赛
     */
    private Integer scale;

    /**
     * 报名人数
     */
    private Integer registrationNumber;

    /**
     * 竞赛发布时间
     */
    private Date releaseDate;

    /**
     * 报名开始时间
     */
    private Date registrationDate;

    /**
     * 作品提交时间
     */
    private Date submitDate;

    /**
     * 评审开始时间
     */
    private Date reviewDate;

    /**
     * 结果公布时间
     */
    private Date resultDate;

    /**
     * 竞赛信息
     */
    private String detail;

    /**
     * 参赛指南
     */
    private String entryGuidelines;
    /**
     * 结果公布
     */
    private String result;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}