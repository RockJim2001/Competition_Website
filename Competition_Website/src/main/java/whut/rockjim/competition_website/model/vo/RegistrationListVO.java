package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报名报返回实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationListVO {
    /**
     * 报名编号id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 学生姓名
     */
    private String username;

    /**
     * 竞赛名称
     */
    private String competition;

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

}
