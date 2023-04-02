package whut.rockjim.competition_website.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.model.request.LoginRequest;
import whut.rockjim.competition_website.model.request.RegisterRequest;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.LoginService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceImplTest {
    @Autowired
    private LoginService loginService;
    /**
     * 登录测试
     */
        @Test
        public void login(){

            for(int i=1;i<=10;i++)
            {
                RegisterRequest request=new RegisterRequest("评委"+i,
                        "2576030"+String.valueOf(48+i)+String.valueOf(48+i+2)+"@qq.com","123456");
                Result result=new Result();
                loginService.register(request,result);
                System.out.println(result.getMessage());
            }

        }
}