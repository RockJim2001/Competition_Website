package whut.rockjim.competition_website.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import whut.rockjim.competition_website.mapper.NotificationDetailByIdMapper;
import whut.rockjim.competition_website.mapper.NotificationDetailMapper;
import whut.rockjim.competition_website.model.dto.NotificationDTO;
import whut.rockjim.competition_website.model.entity.Competition;
import whut.rockjim.competition_website.model.entity.Notification;
import whut.rockjim.competition_website.model.entity.Users;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.model.response.ResultObject;
import whut.rockjim.competition_website.model.vo.NotificationDetailVO;
import whut.rockjim.competition_website.model.vo.NotificationListVO;
import whut.rockjim.competition_website.service.CompetitionService;
import whut.rockjim.competition_website.service.NotificationService;
import whut.rockjim.competition_website.mapper.NotificationMapper;
import org.springframework.stereotype.Service;
import whut.rockjim.competition_website.service.UsersService;

import java.util.ArrayList;
import java.util.List;

/**
* @author 25760
* @description 针对表【notification】的数据库操作Service实现
* @createDate 2022-04-27 16:04:41
*/
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
    implements NotificationService{

    //生成一个UsersService服务对象
    @Autowired
    private UsersService usersService;

    //生成一个NotificationDetailMapper对象
    @Autowired
    private NotificationDetailMapper notificationDetailMapper;
    @Autowired
    private NotificationDetailByIdMapper notificationDetailByIdMapper;

    /**
     * 返回资讯列表和通知列表
     * @param result
     */
    @Override
    public void getNotificationList(Result result) {
        System.out.println("哈哈哈");
        try{
            List<NotificationListVO> notificationListVOList=new ArrayList<>();
            List<Notification> notificationList=this.list();
            for (Notification notification:notificationList) {
                notificationListVOList.add(new NotificationListVO(notification));
            }
            result.setSuccess(true);
            result.setMessage("成功获取资讯列表！");
            result.setData(notificationListVOList);
        }catch (Exception e){
            System.out.println("哈哈哈哈"+e);
            result.setSuccess(false);
            result.setMessage("服务器异常");
        }

    }

    @Override
    public void getNotificationById(Long id, Result result) {
        try{
            Notification notification=this.getById(id);
            if(notification!=null){
                Users users=usersService.getById(notification.getPublishId());
                NotificationDetailVO notificationDetailVO=new NotificationDetailVO(notification,users.getNickname());
                result.setSuccess(true);
                result.setMessage("成功获取编号为："+id+"的资讯信息！");
                result.setData(notificationDetailVO);
            }else{
                result.setSuccess(false);
                result.setMessage("编号为："+id+"的资讯不存在！");
            }
        }catch (Exception e){
            System.out.println(e);
            result.setSuccess(false);
            result.setMessage("服务器异常");
        }
    }

    /**
     * @param id
     * @param result
     * @return
     */
    @Override
    public void updateReadingNumberById(Long id, Result result) {
        Notification notification=this.getById(id);
        if(notification==null){
            result.setSuccess(false);
            result.setMessage("编号为："+id+"的资讯不存在！");
        }else{
            notification.setReadingNumber(notification.getReadingNumber()+1);
            if(!this.updateById(notification)) {
                result.setSuccess(false);
                result.setMessage("更新编号为：" + id + "资讯的阅读量字段失败！");
            }else{
                result.setSuccess(true);
                result.setMessage("更新编号为：" + id + "资讯的阅读量字段失败！");
            }
        }

    }

    /**
     * 分页查询获取通知详细列表
     * @param pagination
     * @param result
     */
    @Override
    public void getNotificationDetailList(Pagination pagination, Result result) {
        System.out.println("id:"+pagination.getId());
        Page<NotificationDTO> page=new Page<>();
        page.setSize(pagination.getPageSize());
        page.setCurrent(pagination.getPageNo());
        Long id=pagination.getId();

        Page<NotificationDTO> list=new Page<>();
        if(id==null){
            list=notificationDetailMapper.getNotificationDetailList(page);
        }else{
            list=notificationDetailByIdMapper.getNotificationDetailListById(page,id);
        }
        ResultObject<NotificationDTO> resultObject=new ResultObject<>(list);

        result.setSuccess(true);
        result.setMessage("成功获取分页通知列表！");
        result.setData(resultObject.getResultObject());
    }

    @Override
    public void updateNotification(Notification notification, Result result) {
        Boolean flag=this.saveOrUpdate(notification);
        if(flag){
            result.setSuccess(true);
            result.setMessage("操作成功");
        }else{
            result.setSuccess(false);
            result.setMessage("系统异常");
        }
    }

    @Override
    public void deleteOne(Long id, Result result) {
        Boolean flag= this.removeById(id);
        if(flag){
            result.setSuccess(true);
            result.setMessage("通知删除成功");
        }else{
            result.setSuccess(false);
            result.setMessage("系统异常");
        }
    }
}




