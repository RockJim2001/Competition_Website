package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import whut.rockjim.competition_website.model.dto.WorksScoreNewDTO;

/**
* @author 25760
* @description 针对表【works_score_1】的数据库操作Mapper
* @createDate 2022-05-10 17:33:32
* @Entity whut.rockjim.competition_website.entity.WorksScore1
*/
@Repository
public interface WorksScoreByIdMapper extends BaseMapper<WorksScoreNewDTO>{
    Page<WorksScoreNewDTO> getWorksScoreById(Page<WorksScoreNewDTO> page,Long id);
}




