package whut.rockjim.competition_website.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 竞赛主体类，作为CompetitionDetailVO的成员变量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionBodyDTO {
    /**
     * 题目
     */
    private String title;
    /**
     * 内容
     */
    private String context;
}
