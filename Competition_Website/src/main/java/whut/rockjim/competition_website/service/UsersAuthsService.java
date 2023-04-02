package whut.rockjim.competition_website.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.constant.enums.LoginTypeEnum;
import whut.rockjim.competition_website.model.entity.UsersAuths;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 25760
* @description 针对表【users_auths】的数据库操作Service
* @createDate 2022-04-24 17:20:42
*/
@Service
public interface UsersAuthsService extends IService<UsersAuths> {
    @Override
    default UsersAuths getOne(Wrapper<UsersAuths> queryWrapper) {
        return IService.super.getOne(queryWrapper);
    }

    default Boolean getByEmail(String email){
        UsersAuths usersAuths=IService.super.getOne(new QueryWrapper<UsersAuths>()
                .eq("identity_type", LoginTypeEnum.EMAIL)
                .eq("identifier",email));
        if(usersAuths!=null)return true;
        else return false;
    }
    default Boolean getByUsername(String username){
        UsersAuths usersAuths=IService.super.getOne(new QueryWrapper<UsersAuths>()
                .eq("identity_type",LoginTypeEnum.USERNAME)
                .eq("identifier",username));
        if(usersAuths!=null)return true;
        else return false;
    }
}
