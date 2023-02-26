package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.constant.enums.LoginTypeEnum;
import whut.rockjim.competition_website.model.dto.LoginDTO;
import whut.rockjim.competition_website.model.entity.Users;
import whut.rockjim.competition_website.model.entity.UsersAuths;
import whut.rockjim.competition_website.model.request.LoginRequest;
import whut.rockjim.competition_website.model.request.RegisterRequest;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.LoginService;
import whut.rockjim.competition_website.service.UsersAuthsService;
import whut.rockjim.competition_website.service.UsersService;
import whut.rockjim.competition_website.utils.Token;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class LoginServiceImpl implements LoginService {

    //生成一个UserAuthsService对象
    @Autowired
    private UsersAuthsService usersAuthsService;
    //生成一个UserService对象
    @Autowired
    private UsersService usersService;

    /**
     * 用户登录服务
     * @param loginRequest
     * @return
     */
    @Override
    public void login(LoginRequest loginRequest,Result result) {

        //先从请求对象中获得每个参数
        String identifier=loginRequest.getIdentifier();
        String credential=loginRequest.getCredential();/********** password *************/
        String identityType=loginRequest.getIdentityType();


        //调用userAuthsService
        UsersAuths usersAuths=usersAuthsService.getOne(new QueryWrapper<UsersAuths>()
                .eq("identifier",identifier)
                .eq("credential",credential)
                .eq("identity_type",LoginTypeEnum.valueOf(identityType)));
        //查询失败
        if(usersAuths==null){
            result.setSuccess(false);
            result.setMessage("用户名或密码错误！");
        }else{
            Users loginUser=usersService.getById(usersAuths.getUserId());
            //将用户信息封装到LoginDTO对象中
            result.setSuccess(true);
            result.setMessage("登录成功！");
            Map<String,Object> loginData=new HashMap<>();
            loginData.put("loginInfo",new LoginDTO(loginUser));
            loginData.put("token",new Token("sldflkdjflsjjs6+s5ff8we4f5s46we4r65w466465qw64e@dsfs6"));
            result.setData(loginData);
        }
    }

    @Override
    public void register(RegisterRequest registerRequest, Result result) {

        //从请求体中解析出所有的参数
        String username=registerRequest.getUsername();
        String password=registerRequest.getCredential();
        String email=registerRequest.getEmail();

        //调用UsersAuthsService判断该用户的邮箱是否已经注册
        if(usersAuthsService.getByEmail(email)){
            //邮箱已经注册
            result.setSuccess(false);
            result.setMessage("该邮箱已经注册！");
            return;
        }else if(usersAuthsService.getByUsername(username)){ //判断该用户名是否被使用过
            //该用户名已经注册
            result.setSuccess(false);
            result.setMessage("该用户名已经被使用！");
            return;
        }else {
            //判断该用户的信息没有被注册之后先创建user用户
            Users user = new Users();
            user.setNickname(username);
            //新增该用户
            if (usersService.save(user)) {
                //打印生成的user
                log.info("新增一个用户：" + user.toString());

                //新增一条{用户名、密码}权限数据
                UsersAuths usersAuths_1 = new UsersAuths();
                usersAuths_1.setUserId(user.getId());
                usersAuths_1.setIdentityType(LoginTypeEnum.USERNAME);
                usersAuths_1.setIdentifier(username);
                usersAuths_1.setCredential(password);
                usersAuthsService.save(usersAuths_1);

                //新增一条{邮箱、密码}权限数据
                UsersAuths usersAuths_2 = new UsersAuths();
                usersAuths_2.setUserId(user.getId());
                usersAuths_2.setIdentityType(LoginTypeEnum.EMAIL);
                usersAuths_2.setIdentifier(email);
                usersAuths_2.setCredential(password);
                usersAuthsService.save(usersAuths_2);
                //结果集的返回
                result.setSuccess(true);
                result.setMessage("注册成功！");
                return;
            }else{
                result.setSuccess(false);
                result.setMessage("服务器异常！");
            }
        }
    }

}
