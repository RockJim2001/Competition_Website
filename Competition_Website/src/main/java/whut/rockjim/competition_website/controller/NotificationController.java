package whut.rockjim.competition_website.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.rockjim.competition_website.model.entity.Notification;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.NotificationService;

/**
 * 通知管理控制
 */
@Log4j2
@RestController
public class NotificationController {

    //生成一个NotificationService服务对象
    @Autowired
    private NotificationService notificationService;

    /**
     * 获取通知列表
     * @param
     * @return
     */
    @GetMapping(value = "/notifications")
    public Result getNotificationList(){
        log.info("获取通知列表请求！");
        //调用NotificationService服务获取列表
        Result result=new Result();

        notificationService.getNotificationList(result);

        return result;
    }

    /**
     * 通过id获取通知详情
     * @param id
     * @return
     */
    @GetMapping(value = "/notification")
    public Result getNotificationDetailById(@RequestParam Long id){
        log.info("获取编号为："+id+"的资讯详细信息");
        Result result=new Result();

        //调用服务
        notificationService.getNotificationById(id,result);
        return result;
    }

    /**
     * 根据id更新readingNumber字段
     * @param id
     * @return
     */
    @PatchMapping(value = "/notification")
    public Result updateNotificationById(@RequestParam Long id){
        Result result=new Result();
        //调用服务
        notificationService.updateReadingNumberById(id,result);
        return result;
    }

    @PostMapping(value = "/admin/notifications")
    public Result getNotificationDetailList(@RequestBody Pagination pagination){
        log.info("后台获取详细竞赛信息的分页列表：");
        Result result=new Result();
        //调用服务
        notificationService.getNotificationDetailList(pagination,result);
        return result;
    }

    @PostMapping(value = "/updateNotification")
    public Result updateNotification(@RequestBody Notification notification){
        log.info("更新通知信息："+notification.toString());
        Result result=new Result();
        //调用服务
        notificationService.updateNotification(notification,result);
        return result;
    }

    @DeleteMapping(value = "/notification")
    public Result deleteOne(@RequestBody JSONObject jsonObject){
        Long id=Long.parseLong(jsonObject.getString("id"));
        log.info("删除编号为："+id+"的通知！");
        Result result=new Result<>();
        //调用服务
        notificationService.deleteOne(id,result);
        return result;
    }
}
