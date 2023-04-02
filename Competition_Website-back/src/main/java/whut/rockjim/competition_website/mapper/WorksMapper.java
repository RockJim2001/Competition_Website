package whut.rockjim.competition_website.mapper;

import whut.rockjim.competition_website.model.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
* @author 25760
* @description 针对表【works】的数据库操作Mapper
* @createDate 2022-05-09 23:15:39
* @Entity whut.rockjim.competition_website.model.entity.Works
*/
public interface WorksMapper extends BaseMapper<Works> {

    void summaryScore(Long id);
}




