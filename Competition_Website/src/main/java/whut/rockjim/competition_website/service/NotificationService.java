package whut.rockjim.competition_website.service;

import whut.rockjim.competition_website.model.entity.Notification;
import com.baomidou.mybatisplus.extension.service.IService;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;

/**
* @author 25760
* @description 针对表【notification】的数据库操作Service
* @createDate 2022-04-27 16:04:41
*/
public interface NotificationService extends IService<Notification> {

    void getNotificationList(Result result);
    void getNotificationById(Long id,Result result);
    void updateReadingNumberById(Long id,Result result);
    void getNotificationDetailList(Pagination pagination,Result result);
    void updateNotification(Notification notification,Result result);
    void deleteOne(Long id, Result result);
}
