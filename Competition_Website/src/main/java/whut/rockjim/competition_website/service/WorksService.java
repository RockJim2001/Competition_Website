package whut.rockjim.competition_website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.dto.WorksScoreNewDTO;
import whut.rockjim.competition_website.model.entity.Works;
import com.baomidou.mybatisplus.extension.service.IService;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.request.ScoreRequest;
import whut.rockjim.competition_website.model.request.UpdateWorksRequest;
import whut.rockjim.competition_website.model.response.Result;

/**
* @author 25760
* @description 针对表【works】的数据库操作Service
* @createDate 2022-05-09 23:15:40
*/
public interface WorksService extends IService<Works> {
    void submit(MultipartFile multipartFile,Works works, Result result);
    void worksList(Pagination pagination,Result result);
    void updateWorksJudges(UpdateWorksRequest updateWorksRequest, Result result);
    void getWorksScoreById(Pagination page,Result result);
    void getScoreList(Pagination pagination,Result result);
    void updateScore(ScoreRequest scoreRequest,Result result);

    void summaryScore(Long id);

    void inquiryScoreById(Long id, Result result);
}
