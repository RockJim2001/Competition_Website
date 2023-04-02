package whut.rockjim.competition_website.model.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 作品分配评委的请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorksRequest {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * allocation
     */
    private Integer allocation;
    /**
     * 评委列表
     */
    private List<Long> judges;
}
