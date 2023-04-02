package whut.rockjim.competition_website.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.Competition;

/**
 * 资讯列表的数据返回类，作为其他类的成员变量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDTO {
    /**
     * 竞赛编号：id---唯一标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  id;
    /**
     * 名称
     */
    private String title;
    /**
     * 海报
     */
    private String poster;

    public NewDTO(Competition competition){
        this.id=competition.getId();
        this.title=competition.getTitle();
        this.poster=competition.getPoster();
    }
}
