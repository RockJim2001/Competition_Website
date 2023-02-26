package whut.rockjim.competition_website.mapper;

import whut.rockjim.competition_website.model.entity.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 25760
* @description 针对表【competition】的数据库操作Mapper
* @createDate 2022-04-27 15:13:52
* @Entity whut.rockjim.competition_website.entity.Competition
*/
public interface CompetitionMapper extends BaseMapper<Competition> {

    Boolean addRegistrationNumber(Long competitionId);

}




