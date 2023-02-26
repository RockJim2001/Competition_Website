package whut.rockjim.competition_website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;
import whut.rockjim.competition_website.model.dto.NotificationDTO;

/**
* @author 25760
* @description 针对表【notification】的数据库操作Mapper
* @createDate 2022-04-27 16:04:41
* @Entity whut.rockjim.competition_website.entity.Notification
*/
@Repository
public interface NotificationDetailByIdMapper extends BaseMapper<NotificationDTO> {
    Page<NotificationDTO> getNotificationDetailListById(Page<NotificationDTO> page, Long id);
}




