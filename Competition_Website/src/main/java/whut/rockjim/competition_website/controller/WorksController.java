package whut.rockjim.competition_website.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whut.rockjim.competition_website.model.entity.Works;
import whut.rockjim.competition_website.model.request.Pagination;
import whut.rockjim.competition_website.model.request.ScoreRequest;
import whut.rockjim.competition_website.model.request.UpdateWorksRequest;
import whut.rockjim.competition_website.model.response.Result;
import whut.rockjim.competition_website.service.WorksService;

/**
 * 作品管理控制类
 */
@Log4j2
@RestController
public class WorksController {
    //生成一个服务对象
    @Autowired
    private WorksService worksService;

    /**
     * 提交作品
     * @param multipartFile
     * @param works
     * @return
     */
    @PostMapping(value = "/submit")
    public Result submit(@RequestPart(value = "file") MultipartFile multipartFile,@RequestPart(value = "works") Works works){
        log.info("提交作品："+works.toString());
        log.info("附件为:"+multipartFile);
        Result result=new Result();
        //先查询给用户是否报名

        //调用服务
        worksService.submit(multipartFile,works,result);
        return result;
    }

    @PostMapping(value = "/worksList")
    public Result worksList(@RequestBody Pagination pagination){
        log.info("通过分页查询作品列表");
        Result result=new Result();
        //调用服务
        worksService.getWorksScoreById(pagination,result);
        return result;
    }

    //评委分配
    @PostMapping(value = "/updateWorksJudges")
    public Result updateWorksJudges(@RequestBody UpdateWorksRequest updateWorksRequest){

        log.info("分配评委："+updateWorksRequest);
        Result result=new Result();
        worksService.updateWorksJudges(updateWorksRequest,result);
        return result;
    }

    @PostMapping(value = "/worksScoreList")
    public Result worksScoreList(@RequestBody Pagination pagination){
        log.info("获取作品列表："+pagination.toString());
        Result result=new Result();
        //调用服务
        worksService.getScoreList(pagination,result);
        return result;
    }

    @PostMapping(value = "/updateScore")
    public Result updateScore(@RequestBody ScoreRequest scoreRequest){
        log.info("打分请求：");
        Result result=new Result();
        //调用服务
        worksService.updateScore(scoreRequest,result);
        return result;
    }

    @PostMapping(value = "/inquiryScore")
    public Result inquiryScore(@RequestBody JSONObject jsonObject){
        log.info("根据id查询作品成绩：");
        Long id=Long.parseLong(jsonObject.getString("id"));
        Result result=new Result();
        worksService.inquiryScoreById(id,result);
        return result;
    }
}
