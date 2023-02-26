package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import whut.rockjim.competition_website.model.dto.WorksScoreNewDTO;

import java.util.List;

/**
* @author 25760
* @description 针对表【works_score_1】的数据库操作Mapper
* @createDate 2022-05-10 17:33:32
* @Entity whut.rockjim.competition_website.entity.WorksScore1
*/
@Repository
public interface WorksScoreNewMapper extends BaseMapper<WorksScoreNewDTO>{
    Page<WorksScoreNewDTO> getWorksScore(Page<WorksScoreNewDTO> page);
}




