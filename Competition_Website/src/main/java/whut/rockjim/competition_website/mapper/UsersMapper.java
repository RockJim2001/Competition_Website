package whut.rockjim.competition_website.mapper;

import org.apache.ibatis.annotations.Mapper;
import whut.rockjim.competition_website.model.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 25760
* @description 针对表【users】的数据库操作Mapper
* @createDate 2022-04-24 17:19:57
* @Entity whut.rockjim.competition_website.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}




