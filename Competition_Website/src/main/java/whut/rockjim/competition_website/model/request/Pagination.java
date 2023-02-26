package whut.rockjim.competition_website.model.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询请求实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    /**
     * 第一页的页码
     */
    private Integer pageNo;
    /**
     * 每页的大小
     */
    private Integer pageSize;
    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
