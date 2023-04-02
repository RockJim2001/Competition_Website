package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.constant.enums.PermissionEnum;
import whut.rockjim.competition_website.mapper.UserInfoMapper;
import whut.rockjim.competition_website.model.dto.UserInfoDTO;
import whut.rockjim.competition_website.model.entity.Users;
import whut.rockjim.competition_website.model.entity.UsersAuths;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.vo.JudgesVO;
import whut.rockjim.competition_website.model.vo.UserInfoVO;
import whut.rockjim.competition_website.service.UsersAuthsService;
import whut.rockjim.competition_website.service.UsersService;
import whut.rockjim.competition_website.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.utils.AliyunOSSUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author 25760
* @description 针对表【users】的数据库操作Service实现
* @createDate 2022-04-24 17:19:57
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    //生成一个UserInfoMapper对象
    @Autowired
    private UserInfoMapper userInfoMapper;
    //生成一个AliyunOSSUtil服务对象
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

   //生成一个UsersAuthsService服务对象
    @Autowired
    private UsersAuthsService usersAuthsService;

    /**
     * 获取评委列表
     */
    @Override
    public void judges(Long id, Result result) {
       List<Users> users=this.list(new QueryWrapper<Users>()
               .eq("permission", PermissionEnum.JUDGE.getPermission()));
        List<JudgesVO> judges=new ArrayList<>();
        for (Users item:users) {
            judges.add(new JudgesVO(item));
        }
        result.setSuccess(true);
        result.setMessage("成功获取评委简单列表！");
        result.setData(judges);
    }

    @Override
    public List<Long> randomJudgeList(Integer number) {
        Integer count= Math.toIntExact(this.count(new QueryWrapper<Users>().eq("permission", PermissionEnum.JUDGE.getPermission())));
        List<Long> judges=new ArrayList<>();
        if(number<=0 || number>count){
            return null;
        }else{
            for (Users users:this.list(new QueryWrapper<Users>().eq("permission", PermissionEnum.JUDGE.getPermission()))) {
                judges.add(users.getId());
            }
            for(int i=0;i<count-number;i++){
                Collections.shuffle(judges);
                judges.remove(0);
            }
        }
        return judges;
    }

    @Override
    public void getUserInfoById(Long id, Result result) {
      UserInfoDTO userInfoDTO= userInfoMapper.getUserInfoById(id);
        System.out.println(userInfoDTO.toString());
        result.setMessage("获取用户信息成功");
        result.setSuccess(true);
        result.setData(new UserInfoVO(userInfoDTO));
    }

    @Override
    public void updateUserInfo(MultipartFile avatar, UserInfoVO userInfoVO,Result result) {
        //先删除用户的原始图像
        Long id=userInfoVO.getId();
        String oldUrl=this.getById(id).getAvatar();
        aliyunOSSUtil.deleteFile(oldUrl);
        //再更新图像
        String url=aliyunOSSUtil.upload("avatar",avatar);

        //更新用户的其他信息
        this.updateById(userInfoVO.getUsers(url));
        //更新邮箱以及密码
        usersAuthsService.update(new UpdateWrapper<UsersAuths>()
                .eq("user_id",id)
                        .eq("identity_type",1)
                .set("identifier",userInfoVO.getEmail()));
        usersAuthsService.update(new UpdateWrapper<UsersAuths>()
                .eq("user_id",id)
                .set("credential",userInfoVO.getPassword()));

        result.setSuccess(true);
        result.setMessage("用户信息更新成功！");
    }
}




