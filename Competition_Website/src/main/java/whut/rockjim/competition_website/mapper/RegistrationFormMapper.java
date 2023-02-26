package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import whut.rockjim.competition_website.model.entity.RegistrationForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.vo.RegistrationListVO;

import java.util.List;

/**
* @author 25760
* @description 针对表【registration_form】的数据库操作Mapperregistration_form
* @createDate 2022-05-08 21:22:40
* @Entity whut.rockjim.competition_website.model.entity.RegistrationForm
*/
public interface RegistrationFormMapper extends BaseMapper<RegistrationForm> {

    /**
     * 多表联查的分页查询
     * @param pagination
     * @return
     */
    @Select("select registration_form.id,nickname as 'username',competition.title as 'competition',registration_form.status,apply_form,apply_date,comment from registration_form,users,competition where registration_form.user_id=users.id and registration_form.competition_id=competition.id")
    List<RegistrationListVO> getList(Page<RegistrationListVO> pagination);


    List<RegistrationListVO> getListById(Page<RegistrationListVO> page, Long id);
}




