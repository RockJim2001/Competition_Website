package whut.rockjim.competition_website.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.vo.UserInfoVO;
import whut.rockjim.competition_website.service.UsersService;

/**
 * 用户控制类
 */
@Log4j2
@RestController
public class UserController {
    //生成一个UsersService服务对象
    @Autowired
    private UsersService usersService;

    /**
     * 通过id获取评委列表
     * @param id
     * @return
     */
    @PostMapping(value = "/judges")
    public Result judges(Long id){
        log.info("获取评委简单列表");
        Result result=new Result();
        //调用服务
        usersService.judges(id,result);
        return result;
    }

    @PostMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestBody JSONObject object){
        String user_id=object.getString("id");
        Long id=Long.parseLong(user_id);
        log.info("获取id为："+id+"的用户的详细信息");
        Result result=new Result();
        //调用服务
        usersService.getUserInfoById(id,result);
        return result;
    }

    @PostMapping(value = "/updateUserInfo")
    public Result updateUserInfo(@RequestPart("avatar")MultipartFile avatar,@RequestPart("userInfo") UserInfoVO userInfoVO){
        log.info("更新用户信息："+userInfoVO.toString());
        log.info("上传的图像为："+avatar);
        Result result=new Result();
        //调用服务
        usersService.updateUserInfo(avatar,userInfoVO,result);
        return result;
    }

}
