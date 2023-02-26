package whut.rockjim.competition_website.service;

import whut.rockjim.competition_website.model.request.LoginRequest;
import whut.rockjim.competition_website.model.request.RegisterRequest;
import whut.rockjim.competition_website.model.response.Result;

/**
 * 实现登录、注册以及忘记密码服务
 */

public interface LoginService {

    void login(LoginRequest loginRequest, Result result);

    void register(RegisterRequest registerRequest, Result result);
}
