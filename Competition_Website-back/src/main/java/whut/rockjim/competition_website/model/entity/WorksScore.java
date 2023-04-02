package whut.rockjim.competition_website.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @TableName works_score
 */
@TableName(value ="works_score")
@Data
public class WorksScore implements Serializable {
    /**
     * 评审id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 评委id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 作品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksId;

    /**
     * 得分
     */
    private Double score;

    /**
     * 批注
     */
    private String comment;

    /**
     * 当前状态：0-未打分，1-已打分
     */
    private Integer status;

    /**
     * 打分日期
     */
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}