package whut.rockjim.competition_website.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.WorksScore;

import java.util.Date;
import java.util.List;

/**
 * 通过查询works表和works_score以及users表的实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorksScoreNewDTO {
    /**
     * 作品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 作品名称
     */
    private String title;
    /**
     * 竞赛名称
     */
    private String competition;

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

    /**
     *评委列表
     */
    private List<WorksScoreDTO> worksScoreList;
}
