package whut.rockjim.competition_website.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录时所发送的请求实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    /**
     * 标识（手机号、用户名、邮箱或者第三方应用的唯一标识）
     */
    private String identifier;
    /**
     * 密码凭证（站内的密码，站外不保存或者保存的token）
     */
    private String credential;
    /**
     * 登录方式：0-username、1-email、2-phone、3-weixin、4-qq、5-weibo
     */
    private String identityType;
}
