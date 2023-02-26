package whut.rockjim.competition_website.model.request;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 竞赛实体的请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionRequest {
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
}
