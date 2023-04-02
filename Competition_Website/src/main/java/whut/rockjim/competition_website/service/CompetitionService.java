package whut.rockjim.competition_website.service;

import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.Competition;
import com.baomidou.mybatisplus.extension.service.IService;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;

/**
* @author 25760
* @description 针对表【competition】的数据库操作Service
* @createDate 2022-04-27 15:13:52
*/
public interface CompetitionService extends IService<Competition> {

    void getList(Result result);
    void getDetail(Long id,Result result);
    void getListOrderByPublishDate(Result result);
    void getDetailList(Pagination pagination,Result result);
    void saveOrUpdateOne(MultipartFile multipartFile,Competition competition,Result result);
    void publishOne(Long id,Result result);
    Boolean addRegistrationNumber(Long competitionId);

    void deleteOne(Long id, Result result);

    void getCompetitionInfoById(Pagination pagination, Result result);
}
