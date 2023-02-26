package whut.rockjim.competition_website.model.dto;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorksScoreDTO {
    /**
     * 评审id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 评委名字
     */
    private String judgeName;

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
}
