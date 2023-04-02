package whut.rockjim.competition_website.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.vo.UserInfoVO;

import java.io.Serializable;
import java.util.List;

/**
* @author 25760
* @description 针对表【users】的数据库操作Service
* @createDate 2022-04-24 17:19:57
*/
@Service
public interface UsersService extends IService<Users> {
    @Override
    default Users getById(Serializable id) {
        return IService.super.getById(id);
    }

    @Override
    default boolean save(Users entity) {
        return IService.super.save(entity);
    }

    void judges(Long id,Result result);

    List<Long> randomJudgeList(Integer number);

    void getUserInfoById(Long id,Result result);
    void updateUserInfo(MultipartFile avatar, UserInfoVO userInfoVO,Result result);
}
