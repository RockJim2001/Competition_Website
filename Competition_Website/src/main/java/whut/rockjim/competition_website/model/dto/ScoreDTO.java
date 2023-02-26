package whut.rockjim.competition_website.model.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 作品的评分返回实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDTO {
    /**
     * 评审id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 竞赛名称
     */
    private String competitionName;

    /**
     * 作品url
     */
    private String worksUrl;

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
