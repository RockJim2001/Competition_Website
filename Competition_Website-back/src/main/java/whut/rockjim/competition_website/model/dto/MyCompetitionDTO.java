package whut.rockjim.competition_website.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * 返回用户的竞赛信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCompetitionDTO {
    /**
     * 竞赛id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 竞赛名称
     */
    private String competitionTitle;
    /**
     * 竞赛url
     */
    private String competitionUrl;
    /**
     * 报名表url
     */
    private String registrationFormUrl;
    /**
     * 作品名称
     */
    private String worksTitle;
    /**
     * 作品url
     */
    private String worksUrl;
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
     * 作品得分
     */
    private Double score;
    /**
     * 竞赛级别：0-校赛、1-省赛、2-校赛
     */
    private Integer scale;
}
