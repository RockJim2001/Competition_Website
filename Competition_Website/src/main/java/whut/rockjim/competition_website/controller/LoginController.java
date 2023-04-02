package whut.rockjim.competition_website.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.request.LoginRequest;
import whut.rockjim.competition_website.model.request.RegisterRequest;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.LoginService;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

/**
 * 登录、注册
 */
@Log4j2
@RestController
public class LoginController {
    //生成一个LoginService服务对象
    @Autowired
    private LoginService loginService;

    /**
     *处理登录请求
     * @param loginRequest 接受前端传来的LoginRequest对象
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        Result result=new Result();
        log.info("登录请求:" + loginRequest.toString());

        //调用LoginService
        loginService.login(loginRequest,result);
        return result;
    }


    /**
     * 处理注册请求
     * @param registerRequest
     * @return Result
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody RegisterRequest registerRequest) {
        log.info("注册请求：" + registerRequest.toString());
        Result result=new Result();

        //调用LoginService
        loginService.register(registerRequest,result);
        return result;
    }


}
