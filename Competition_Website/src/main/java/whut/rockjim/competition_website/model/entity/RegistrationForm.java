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
import whut.rockjim.competition_website.model.vo.RegistrationListVO;

/**
 * 
 * @TableName registration_form
 */
@TableName(value ="registration_form")
@Data
public class RegistrationForm implements Serializable {
    /**
     * 报名编号id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    /**
     * 学生id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 竞赛id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long competitionId;

    /**
     * 当前的状态：0-未审核、1-已审核
     */
    private Integer status;

    /**
     * 报名表地址
     */
    private String applyForm;

    /**
     * 报名时间
     */
    private Date applyDate;

    /**
     * 批注
     */
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}