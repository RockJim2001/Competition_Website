package whut.rockjim.competition_website.mapper;

import org.apache.ibatis.annotations.Mapper;
import whut.rockjim.competition_website.model.entity.UsersAuths;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 25760
* @description 针对表【users_auths】的数据库操作Mapper
* @createDate 2022-04-24 17:20:42
* @Entity whut.rockjim.competition_website.entity.UsersAuths
*/
@Mapper
public interface UsersAuthsMapper extends BaseMapper<UsersAuths> {

}




