package whut.rockjim.competition_website.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户注册时的请求体
 */
@Data
@AllArgsConstructor
public class RegisterRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码凭证（站内的密码，站外不保存或者保存的token）
     */
    private String credential;
}
