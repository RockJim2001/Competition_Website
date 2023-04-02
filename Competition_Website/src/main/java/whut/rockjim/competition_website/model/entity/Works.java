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
 * @TableName works
 */
@TableName(value ="works")
@Data
public class Works implements Serializable {
    /**
     * 作品id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 竞赛id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long competitionId;

    /**
     * 作品名称
     */
    private String title;

    /**
     * 作品描述
     */
    private String introduction;

    /**
     * 作品存储url
     */
    private String worksUrl;

    /**
     * 作品得分
     */
    private Double score;
    /**
     * 作品提交时间
     */
    private Date submitDate;
    /**
     * 分配方式：0-未分配、1-系统分配、2-人工分配
     */
    private Integer allocation;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}