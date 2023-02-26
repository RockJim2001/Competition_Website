package whut.rockjim.competition_website.model.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 打分请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequest {
    /**
     * 作品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 分数
     */
    private Float score;
    /**
     * 点评
     */
    private String comment;
}
