package whut.rockjim.competition_website.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import whut.rockjim.competition_website.model.entity.Users;

/**
 * 评委简单列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgesVO {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * nickname
     */
    private String nickname;

    public JudgesVO(Users users){
        this.id=users.getId();
        this.nickname=users.getNickname();
    }
}
