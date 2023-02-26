package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import whut.rockjim.competition_website.model.dto.ScoreDTO;

@Repository
public interface ScoreMapper {
    Page<ScoreDTO> getScoreList(Page<ScoreDTO> page);

    Page<ScoreDTO> getScoreListById(Page<ScoreDTO> page, Long id);
}
