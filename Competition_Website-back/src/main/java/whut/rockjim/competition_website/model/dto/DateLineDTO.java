package whut.rockjim.competition_website.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 封装时间轴的title和date，作为CompetitionDetailVO的对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateLineDTO {
    /**
     * 名称
     */
    private String name;
    /**
     * 时间点
     */
    private Date datePoint;
}
