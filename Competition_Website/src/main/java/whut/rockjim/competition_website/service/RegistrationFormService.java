package whut.rockjim.competition_website.service;

import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.RegistrationForm;
import com.baomidou.mybatisplus.extension.service.IService;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.vo.RegistrationListVO;

/**
* @author 25760
* @description 针对表【registration_form】的数据库操作Service
* @createDate 2022-05-08 21:22:40
*/
public interface RegistrationFormService extends IService<RegistrationForm> {
    void applyOne(MultipartFile multipartFile, Long userId, Long competitionId, Result result);
    void applyList(Pagination pagination,Result result);
    void updateApply(RegistrationListVO registrationListVO,Result result);

    void isRegister(Long user_id, Long competition_id, Result result);
}
